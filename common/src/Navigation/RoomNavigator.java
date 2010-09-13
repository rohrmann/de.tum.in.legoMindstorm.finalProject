package Navigation;

import Color.Color;
import Graph.Graph;
import Graph.Node;
import Graph.Pair;
import Light.LightSettings;
import misc.Direction;
import misc.Helper;
import misc.Robot;

public class RoomNavigator {
	private RoomPilot pilot;
	private Direction heading;
	private Graph map;
	private Pair<Integer,Integer> currentPosition;
	private LightSettings leftLightSettings;
	
	public RoomNavigator(Robot robot){
		leftLightSettings = robot.getLeftLight();
		this.pilot = new RoomPilot(robot);
		this.heading = Direction.NORTH;
		this.map = new Graph();
		currentPosition = new Pair<Integer,Integer>(0,0);
	}
	
	public Color goToNextRoom(){
		advanceRoom();
		return pilot.goToNextRoom();
	}
	
	private void advanceRoom(){
		switch(heading){
		case EAST:
			currentPosition = new Pair<Integer,Integer>(currentPosition.getX()+1,currentPosition.getY());
			break;
		case SOUTH:
			currentPosition = new Pair<Integer,Integer>(currentPosition.getX(),currentPosition.getY()-1);
			break;
		case WEST:
			currentPosition = new Pair<Integer,Integer>(currentPosition.getX()-1,currentPosition.getY());
			break;
		case NORTH:
			currentPosition = new Pair<Integer,Integer>(currentPosition.getX(),currentPosition.getY()+1);
		}
	}
	
	/**
	 * 
	 * @param left turn left if the parameter is true, otherwise turn right
	 */
	public void turn(boolean left,int intersections){
		boolean leftFree =true;
		int curIntersections = 0;
		
		pilot.rotate(left?360:-360,true);
		if(!leftLightSettings.groundChange()){
			pilot.stop();
		}
		
		pilot.reset();
		
		while(pilot.isMoving()){
			
			if(!leftLightSettings.groundChange()){
				leftFree = true;
			}
			
			if(leftFree && leftLightSettings.groundChange()){
				curIntersections++;
				leftFree = false;
			}
			
			if(curIntersections==intersections && leftFree){
				pilot.stop();
			}
		}
		
		updateHeading(pilot.getAngle());
	}
	
	public void turnLeft(){
		turn(true,1);
	}
	
	public void updateHeading(float angle){
		if(heading == Direction.WEST){
			angle += 90;
		}
		else if(heading == Direction.SOUTH){
			angle += 180;
		}
		else if(heading == Direction.EAST){
			angle += 270;
		}
		
		while(angle < 0){
			angle += 360;
		}
		
		if(angle >=360){
			angle %= 360;
		}
		
		if(angle <= 45 || angle > 315){
			heading = Direction.NORTH;
		}
		else if(angle <= 135){
			heading = Direction.WEST;
		}
		else if(angle <= 225){
			heading = Direction.SOUTH;
		}
		else
			heading = Direction.EAST;
	}
	
	public void turnRight(){
		turn(false,1);
	}
	
	public void turnNorth(){
		Node room = map.getNode(currentPosition);
		
		if(!room.hasNorth()){
			Helper.error("RoomNavigator.turnNorth: map has no north neighbor");
		}
		
		if(heading==Direction.EAST){
			turn(true,1);
		}
		else if(heading == Direction.WEST){
			turn(false,1);
		}
		else if(heading == Direction.SOUTH){
			turn(true,room.hasEast()?2:1);
		}
	}
	
	public void turnEast(){
		Node room = map.getNode(currentPosition);
		
		if(!room.hasEast()){
			Helper.error("RoomNavigator.turnEast: map has no east neighbor");
		}
		
		if(heading==Direction.SOUTH){
			turn(true,1);
		}
		else if(heading == Direction.NORTH){
			turn(false,1);
		}
		else if(heading == Direction.WEST){
			turn(true,room.hasSouth()?2:1);
		}
	}
	
	public void turnSouth(){
		Node room = map.getNode(currentPosition);
		
		if(!room.hasSouth()){
			Helper.error("RoomNavigator.turnSouth: map has no south neighbor");
		}
		
		if(heading == Direction.EAST){
			turn(false,1);
		}
		else if(heading == Direction.WEST){
			turn(true,1);
		}
		else if(heading == Direction.NORTH){
			turn(true,room.hasWest()?2:1);
		}
	}
	
	public void turnWest(){
		Node room = map.getNode(currentPosition);
		
		if(!room.hasWest()){
			Helper.error("RoomNavigator.turnWest: map has no west neighbor");
		}
		
		if(heading == Direction.NORTH){
			turn(true,1);
		}
		else if(heading == Direction.SOUTH){
			turn(false,1);
		}
		else if(heading == Direction.EAST){
			turn(true,room.hasNorth()?2:1);
		}
	}
	
}