import Actor.Actor;
import Graph.*;
import lejos.nxt.*;
import misc.Direction;
import misc.RobotType;

public class Puller extends Actor
{
	static Pair actualPosition;
	static Pair newPosition;
	static int oldX;
	static int newX;
	static int oldY;
	static int newY;
	
	public static void main(String[] args) {
		Puller puller = new Puller();
		puller.start();
		}

	@Override
	public void init() {
		Motor.C.resetTachoCount();
		liftArm(162);
	}
	
	@Override
	public void prolog() {
		navi.driveForward(6.0f);
		navi.turnOnLine();
		Puller.liftArm(-160);

		robot.getPilot().forward();
		while(!robot.getColor().getColorName().isRoomColor()){};		
	}

	@Override
	public RobotType getType() {
		return RobotType.PULLER;
	}

	@Override
	public void epilog() {		
		navi.driveForward(14.5f);	
		Puller.liftArm(160);

		robot.getPilot().forward();
		while(!robot.getColor().getColorName().isRoomColor()) {}
		robot.getPilot().stop();

		//Aktualisierung der Position
		actualPosition = navi.getPosition();
		oldX = actualPosition.getX(); 
		oldY = actualPosition.getY();
		
		if(Direction.NORTH == navi.getHeading()) {
			newX = oldX;
			newY = oldY + 1;
		}
		else if(Direction.SOUTH == navi.getHeading()) {
			newX = oldX;
			newY = oldY - 1;
		}
		else if(Direction.EAST == navi.getHeading()) {
			newX = oldX + 1;
			newY = oldY;
		}
		else {
			newX = oldX - 1;
			newY = oldY;
		}
		navi.forcePosition(new Pair(newX,newY));
	}
	
	public static void liftArm(int angle){
		Motor.C.setSpeed(60);
		Motor.C.rotate(-angle,false);
		Motor.C.lock(100);
	}		
}