import miscBrick.Config;
import misc.RobotType;
import Actor.Actor;
import Bluetooth.BTStreams;
import BluetoothBrick.BTCConnector;
import NavigationBrick.RoomNavigator;


public class Pusher extends Actor {
	
	public static void main(String[] args)
	{
		Pusher pusher = new Pusher();
		pusher.start();
	}
	
	public Pusher()
	{
		super();
		robot.getPilot().setMoveSpeed(Config.pusherMoveSpeed);
		robot.getPilot().setTurnSpeed(Config.pusherTurnSpeed);
	}
	
	public RoomNavigator getNavigator()
	{
		return super.navi;
	}

	public void push(int fields)
	{
		for(int i=0; i<fields-1; i++)
			getNavigator().goToNextRoom();
		push();
	}
	
	public void push()
	{
		float travelDistance = Config.lineLength + Config.squareSize - Config.boxSize/2 - Config.wheelToBucket;		//Distanz, die noch zurueckgelegen werden soll	

		getNavigator().driveForward(travelDistance);
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		getNavigator().driveBackward(Config.pusherBackOffDistance);
		
		getNavigator().turnOnLine();
		
		getNavigator().findRoom(travelDistance - Config.pusherBackOffDistance);
	}

	@Override
	public void epilog() {
		push();	
	}

	@Override
	public RobotType getType() {
		return RobotType.PUSHER;
	}

	@Override
	public void prolog() {
		getNavigator().goToNextRoom();
	}

	@Override
	public void init() {
		hasToken = true;
	}

	@Override
	public BTStreams getBrickConnection() {
		return new BTCConnector(Config.rightoo);
	}

	@Override
	public float getWheelToWheel() {
		return Config.pusherWheelToWheel;
	}

	
}
