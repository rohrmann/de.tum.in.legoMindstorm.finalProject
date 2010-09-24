package NavigationBrick;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Stack;
import Color.Color;
import ErrorHandlingBrick.ErrorHandling;
import Graph.Graph;
import Graph.Node;
import Graph.Pair;
import Graph.Type;
import LightBrick.LightSettings;
//import misc.AStar;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import miscBrick.Config;
import misc.Direction;
import miscBrick.Helper;
import miscBrick.Robot;
import miscBrick.RoomInformation;
import misc.RobotType;

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
	private Robot robot;
	private boolean roomMissedActive;
	
	public RoomNavigator(Robot robot,Graph map){
		this.robot = robot;
		leftLightSettings = robot.getLeftLight();
		rightLightSettings = robot.getRightLight();
		this.pilot = new RoomPilot(robot, roomMissedActive);
		this.heading = Direction.NORTH;
		this.map = new Graph();
		currentPosition = new Pair(0,0);
		this.map = map;
		roomMissedActive = false;
	}
	
	public Color goToNextRoom(){
		advanceRoom();
		Color color = pilot.goToNextRoom();
		if(color == null){
			ErrorHandling.resolvebyHand(robot, this);
			color = goToNextRoom();
		}
		return color;
	}
	
	public void setRoomMissedActive(boolean roomMissedActive){
		pilot.setRoomMissedActive(roomMissedActive);
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
	
	public void turnOnLine()
	{
		pilot.rotate(180, false); // guckt nach Linie, nach der er sich, nach seiner theoretischen 180 Grad Drehung dann richten kann
//		while(leftLightSettings.groundChange())
//		{ pilot.rotate(1, false); }
//		while(rightLightSettings.groundChange())
//		{ pilot.rotate(-1, false); }
		pilot.stop();
		
		updateHeading(180);
	}
	
	public void driveForward(float distance)
	{
		DriveDistanceForward driveDistanceForward = new DriveDistanceForward(robot, distance);
		FollowLine followLine = new FollowLine(robot, new RoomInformation());

		Behavior[] driveForwardAndFollow = new Behavior[]{driveDistanceForward, followLine};
		Arbitrator a = new Arbitrator(driveForwardAndFollow, true);
		a.start();
	}
	
	public void driveBackward(float distance)
	{
		robot.getPilot().travel(-distance);
	
	}
	
	public void findRoom(float distanceToRoom)
	{
		RoomInformation roomInfo = new RoomInformation();
		
		DriveForward driveForward = new DriveForward(robot, roomInfo);
		FollowLine followLine = new FollowLine(robot, roomInfo);
		CheckRoom checkRoom = new CheckRoom(robot, (int)(distanceToRoom-Config.checkRoomTolerance), Config.checkRoomTolerance, roomInfo, roomMissedActive);
		
		Behavior[] returnToRoom = new Behavior[]{driveForward, followLine, checkRoom};
		Arbitrator a = new Arbitrator(returnToRoom, true);
		a.start();
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
		
		//System.out.println("Start bfs");
		//Button.waitForPress();
		Hashtable visited = new Hashtable();
		Hashtable prev = new Hashtable();
		
		Direction[] dirs= {Direction.NORTH,Direction.WEST,Direction.SOUTH,Direction.EAST};
		
		Queue queue = new Queue();
		Pair pos = getPosition();
		queue.push(pos);
		visited.put(pos, true);
		
		while(!queue.empty()){
			pos =(Pair)queue.pop();
			
		//	System.out.println("CN:" + pos + " TO:" + to);
		//	Button.waitForPress();
			
			if(pos.equals(to)){
				break;
			}
			
			for(Direction dir : dirs){
				/*System.out.println("Check:" + pos.getNeighbour(dir));
				if(map.hasNode(pos.getNeighbour(dir))){
					System.out.println("Node:" + map.getNode(pos.getNeighbour(dir)));
				}
				Button.waitForPress();*/
				if(map.hasNode(pos.getNeighbour(dir)) && visited.get(pos.getNeighbour(dir)) == null && map.getType(pos.getNeighbour(dir)).isAccessible()){
					//System.out.println("RN:" + pos.getNeighbour(dir));
					//Button.waitForPress();
					queue.push(pos.getNeighbour(dir));
					visited.put(pos.getNeighbour(dir), true);
					prev.put(pos.getNeighbour(dir),pos);
				}
				
			/*	if(!map.getType(pos.getNeighbour(dir)).isAccessible())
				{
					System.out.println("NA:" + pos.getNeighbour(dir));
				}*/
			}
		}
		
		if(!pos.equals(to))
			return null;
		else{
			Stack stack = new Stack();
			
			while(!pos.equals(getPosition())){
				stack.push(pos);
				pos = (Pair)prev.get(pos);
			}
			
			List<Pair> result = new ArrayList<Pair>();
			
			while(!stack.empty()){
				pos = (Pair)stack.pop();
				
				result.add(pos);
			}
			
			return result;
		}
	}
	
	
	public void moveToAStar( Pair to)
	{
		ArrayList<Pair> path = misc.AStar.findPath(map, getPosition(), heading, to);
		
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
	
}
