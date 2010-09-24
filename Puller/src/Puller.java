import Actor.Actor;
import Bluetooth.BTStreams;
import BluetoothBrick.BTCReceiver;
import Graph.*;
import lejos.nxt.*;
import misc.Direction;
import misc.RobotType;
import miscBrick.Config;

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
	
	public Puller(){
		super();
		robot.getPilot().setMoveSpeed(Config.pullerMoveSpeed);
		robot.getPilot().setTurnSpeed(Config.pullerTurnSpeed);
	}

	@Override
	public void init() {
		hasToken = false;
	}
	
	@Override
	public void prolog() {
		navi.driveForward(6.0f);
		navi.turnOnLine();
		Puller.liftArm(-Config.pullerArmAngle);

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
		Puller.liftArm(Config.pullerArmAngle);

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
		Motor.C.setSpeed(Config.pullerArmSpeed);
		Motor.C.rotate(-angle,false);
		Motor.C.lock(100);
	}

	@Override
	public BTStreams getBrickConnection() {
		return new BTCReceiver();
	}		
	
	@Override
	public float getWheelToWheel() {
		return Config.pullerWheelToWheel;
	}

	@Override
	public int getLightTolerance() {
		return Config.pullerLightTolerance;
	}
}