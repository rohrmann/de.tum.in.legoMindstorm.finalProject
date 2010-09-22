package NavigationBrick;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Stack;

import Color.Color;
import ErrorHandlingBrick.ErrorInformation;
import Graph.Graph;
import Graph.Node;
import Graph.Pair;
import Graph.Type;
import LightBrick.LightSettings;
//import misc.AStar;
import misc.Direction;
import misc.RobotType;
import miscBrick.Helper;
import miscBrick.Robot;

/**
 * 
 * @author rohrmann
 *
 */
public class RoomNavigator {
	private RoomPilot pilot;
	private Direction heading;
	private Graph map;
	private Pair currentPosition;
	private LightSettings leftLightSettings;
	private LightSettings rightLightSettings;
	private ErrorInformation errinfo;
	
	public RoomNavigator(Robot robot,Graph map, ErrorInformation errorinfo){
		leftLightSettings = robot.getLeftLight();
		rightLightSettings = robot.getRightLight();
		this.pilot = new RoomPilot(robot, this);
		this.heading = Direction.NORTH;
		this.map = new Graph();
		currentPosition = new Pair(0,0);
		this.map = map;
		this.errinfo = errorinfo;
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
	
	public void turn(Direction direction)
	{
		if(Direction.NORTH == direction) {
			turnNorth();
		}
		else if(Direction.SOUTH == direction) {
			turnSouth();
		}
		else if(Direction.EAST == direction) {
			turnEast();
		}
		else {
			turnWest();
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
	
	public void setGraph(Graph map,RobotType robot) {
		this.map = map;
		
		switch(robot){
		case PUSHER:
			currentPosition = map.find(Type.PUSHSTART);
			break;
		case PULLER:
			currentPosition = map.find(Type.PULLSTART);
			break;
		case MAPPER:
			break;
		}
	}
	
	public void moveStraightForward(int rooms){
		
		boolean nodesFound = true;
		Pair pos = getPosition();
		for(int i =0; i< rooms;i++){
			pos = pos.getNeighbour(getHeading());
			
			if(!map.hasNode(pos)){
				nodesFound =false;
				break;
			}
		}
		
		if(!nodesFound){
			Helper.error("RoomNavigator.movesStraigthForward: room not existing");
		}
		
		for(int i =0; i< rooms;i++){
			goToNextRoom();
		}
	}
	
	public void moveTo(Pair to){
		List<Pair> path = bfs(to);
		
		if(path == null){
			Helper.error("RoomNavigator.moveTo: could not find a path from:" + getPosition() + " to:" + to);
		}
		
		for(int i =0; i< path.size(); i++){
			Direction dir = findDir(path.get(i));
			
			if(dir == Direction.UNDEFINED){
				Helper.error("RoomNavigator.moveTo: could not find the direction");
			}
			
			turn(dir);
			goToNextRoom();
		}
	}
	
	public Direction findDir(Pair to){
		int x = to.getX() - getPosition().getX();
		int y = to.getY() - getPosition().getY();
		
		if(x == 0 && y == 1)
			return Direction.NORTH;
		else if(x == 0 && y == -1){
			return Direction.SOUTH;
		}
		else if(y ==0 && x ==1 ){
			return Direction.EAST;
		}
		else if(y==0 && x == -1){
			return Direction.WEST;
		}
		else
			return Direction.UNDEFINED;
	}
	
	public List<Pair> bfs(Pair to){
		Hashtable visited = new Hashtable();
		Hashtable prev = new Hashtable();
		
		Direction[] dirs= {Direction.NORTH,Direction.WEST,Direction.SOUTH,Direction.EAST};
		
		Queue queue = new Queue();
		Node node = map.getNode(to);
		queue.push(node);
		visited.put(node, true);
		
		while(!queue.empty()){
			node =(Node)queue.pop();
			
			if(node.getID() == to){
				break;
			}
			
			for(Direction dir : dirs){
				if(node.has(dir) && visited.get(node.get(dir)) == null ){
					queue.push(node.get(dir));
					visited.put(node.get(dir), true);
					prev.put(node.get(dir),node);
				}
			}
		}
		
		if(node.getID() != to)
			return null;
		else{
			Stack stack = new Stack();
			
			while(node.getID() != getPosition()){
				stack.push(node.getID());
				node = (Node)prev.get(node);
			}
			
			List<Pair> result = new ArrayList<Pair>();
			
			while(!stack.empty()){
				node = (Node)stack.pop();
				
				result.add(node.getID());
			}
			
			return result;
		}
	}
	
	
	public void moveTo(Pair from, Pair to)
	{
		ArrayList<Pair> path = misc.AStar.findPath(map, from, to);
		
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
			
			pos = nextPos;
		}
		
	}
	
	public Graph getGraph(){
		return map;
	}
	
	public ErrorInformation getErrorInformation(){
		return errinfo;
	}
}
