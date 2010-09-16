import Graph.*;
import Navigation.RoomNavigator;
import lejos.nxt.LCD;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;
import misc.Direction;
import misc.Robot;


public class GetAndMoveBox implements Behavior {

	private Direction turnDirection;
	private int fields;
	private Robot robot;
	private UltrasonicSensor usensor;
	private RoomNavigator navi;
	private Pair actualPosition;
	private Pair newPosition;
	private int oldX;
	private int newX;
	private int oldY;
	private int newY;

	public GetAndMoveBox(Direction turnDirection, int fields, Robot robot, UltrasonicSensor usensor, RoomNavigator navi)
	{
		this.fields = fields;
		this.turnDirection = turnDirection;
		this.robot = robot;
		this.usensor = usensor;
		this.navi = navi;
	}

	@Override
	public void action() {

		//dreht den Roboter in Richtung Kiste
		if(Direction.NORTH.equals(turnDirection)) {
			navi.turnNorth();
		}
		else if(Direction.SOUTH.equals(turnDirection)) {
			navi.turnSouth();
		}
		else if(Direction.EAST.equals(turnDirection)) {
			navi.turnEast();
		}
		else {
			navi.turnWest();
		}

		//Roboter faehrt an die Kiste hin
		robot.getPilot().forward();
		while(usensor.getDistance() >= 12) {
		}
		robot.getPilot().travel(3.0f,false);
		robot.getPilot().stop();

		//ggf noch ein Stueck zurueck fahren

		//Roboter dreht sich um 180 Grad
		robot.getPilot().rotate(180,false);
		while (robot.getRightLight().getLightValue() <= robot.getRightLight().getGroundValue() - robot.getRightLight().getTolerance()){
			robot.getPilot().rotate(3,false);	
		}
		navi.updateHeading(turnDirection.opposite());

		//Nimmt die Kiste auf
		Puller.liftArm(2);

		//Roboter faehrt zum naechsten Raum
		robot.getPilot().forward();

		//Linienkorrektur
		while(!robot.getColor().getColorName().isRoomColor()) {
			if (robot.getRightLight().getLightValue() <= robot.getRightLight().getGroundValue() - robot.getRightLight().getTolerance()) {
				robot.getPilot().rotate(-3, false);
				robot.getPilot().forward();
			}
			else if (robot.getLeftLight().getLightValue() <= robot.getLeftLight().getGroundValue() - robot.getLeftLight().getTolerance()) {
				robot.getPilot().rotate(3, false);
				robot.getPilot().forward();
			}
		}

		for(int i = 1; i < fields; i++) {
			navi.move(turnDirection.opposite());
		}
		
		//robot.getPilot().travel(15f, true);
		robot.getPilot().reset();
		robot.getPilot().forward();
		while (15.f >robot.getPilot().getTravelDistance()){
			if (robot.getRightLight().getLightValue() <= robot.getRightLight().getGroundValue() - robot.getRightLight().getTolerance()) {
				robot.getPilot().rotate(-3, false);
				robot.getPilot().forward();
			}
			else if (robot.getLeftLight().getLightValue() <= robot.getLeftLight().getGroundValue() - robot.getLeftLight().getTolerance()) {
				robot.getPilot().rotate(3, false);
				robot.getPilot().forward();
			}
		}
		
		robot.getPilot().stop();
		Puller.liftArm(160);

		robot.getPilot().forward();
		while(!robot.getColor().getColorName().isRoomColor()) {
			
		}
		robot.getPilot().stop();
		Puller.input = true;
		actualPosition = navi.getPosition();
		oldX = actualPosition.getX(); 
		oldY = actualPosition.getY();
		
		if(Direction.NORTH.equals(turnDirection)) {
			newX = oldX;
			newY = oldY - 1;
		}
		else if(Direction.SOUTH.equals(turnDirection)) {
			newX = oldX;
			newY = oldY + 1;
		}
		else if(Direction.EAST.equals(turnDirection)) {
			newX = oldX - 1;
			newY = oldY;
		}
		else {
			newX = oldX + 1;
			newY = oldY;
		}
		newPosition = new Pair(newX,newY);
		navi.forcePosition(newPosition);

		while(true){
			LCD.drawString("X: " + navi.getX() + "             ",0,0);
			LCD.drawString("Y: " + navi.getY() + "             ",0,1);
			LCD.drawString("Direction: " + navi.getHeading() + "             ",0,2); 
		}
	}


	@Override
	public void suppress() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean takeControl() {
		return !Puller.input;
	}


}
