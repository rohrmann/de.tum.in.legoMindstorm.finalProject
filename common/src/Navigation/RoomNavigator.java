package Navigation;

import java.util.ArrayList;
import java.util.ListIterator;

import Color.Color;
import Graph.Graph;
import Graph.Node;
import Graph.Pair;
import Light.LightSettings;
//import misc.AStar;
import misc.Direction;
import misc.Helper;
import misc.Robot;

public class RoomNavigator {
	private RoomPilot pilot;
	private Direction heading;
	private Graph map;
	private Pair currentPosition;
	private LightSettings leftLightSettings;
	private LightSettings rightLightSettings;
	
	public RoomNavigator(Robot robot,Graph map){
		leftLightSettings = robot.getLeftLight();
		rightLightSettings = robot.getRightLight();
		this.pilot = new RoomPilot(robot);
		this.heading = Direction.NORTH;
		this.map = new Graph();
		currentPosition = new Pair(0,0);
		this.map = map;
	}
	
	public Color goToNextRoom(){
		advanceRoom();
		return pilot.goToNextRoom();
	}
	
	private void advanceRoom(){
		switch(heading){
		case EAST:
			currentPosition = new Pair(currentPosition.getX()+1,currentPosition.getY());
			break;
		case SOUTH:
			currentPosition = new Pair(currentPosition.getX(),currentPosition.getY()-1);
			break;
		case WEST:
			currentPosition = new Pair(currentPosition.getX()-1,currentPosition.getY());
			break;
		case NORTH:
			currentPosition = new Pair(currentPosition.getX(),currentPosition.getY()+1);
		}
	}
	
	public void forcePosition(Pair pair) {
		currentPosition = pair;
	}
	
	/**
	 * 
	 * @param left turn left if the parameter is true, otherwise turn right
	 */
	public void turn(boolean left,int intersections){
		boolean leftFree =true;
		int curIntersections = 0;
		LightSettings light = left?leftLightSettings:rightLightSettings;
		
		pilot.rotate(left?360:-360,true);
		while(light.groundChange()){
			Thread.yield();
		}
		pilot.stop();
		
		pilot.reset();
		
		pilot.rotate(left?360:-360,true);
		
		while(pilot.isMoving()){
			
			if(!light.groundChange()){
				leftFree = true;
			}
			
			if(leftFree && light.groundChange()){
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
	

	public void updateHeading(Direction direction){
		heading = direction;
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
	
	public Pair getPosition(){
		return currentPosition;
	}
	
	public int getX(){
		return currentPosition.getX();
	}
	
	public int getY(){
		return currentPosition.getY();
	}
	
	public Direction getHeading(){
		return heading;
	}
	
	public Color move(Direction dir){
		Color result = Color.UNKNOWN;
		switch(dir){
		case NORTH:
			turnNorth();
			result = goToNextRoom();
			break;
		case SOUTH:
			turnSouth();
			result =goToNextRoom();
			break;
		case WEST:
			turnWest();
			result = goToNextRoom();
			break;
		case EAST:
			turnEast();
			result =goToNextRoom();
			break;
		}
		
		return result;
	}
	
	public void setGraph(Graph map) {
		this.map = map;
	}
	
	
	/*public void moveTo(Pair from, Pair to)
	{
		ArrayList<Pair> path = AStar.findPath(map, from, to);
		
		ListIterator<Pair> iterator = path.listIterator();
		
		Pair pos = iterator.next();
		Pair nextPos;
		
		while(iterator.hasNext())
		{
			nextPos = iterator.next();
			
			int x = nextPos.getX() - pos.getX();
			int y = nextPos.getY() - pos.getY();
			
			if(x == 1 && y == 0)
				move(Direction.EAST);
			else if(x == -1 && y == 0)
				move(Direction.WEST);
			else if(x == 0 && y == 1)
				move(Direction.NORTH);
			else if(x == 0 && y == -1)
				move(Direction.SOUTH);
		}
		
	}*/
}
