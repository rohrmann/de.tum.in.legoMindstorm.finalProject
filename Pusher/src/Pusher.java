import miscBrick.Config;
import misc.RobotType;
import Actor.Actor;
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
		// TODO Auto-generated method stub
		
	}

	
}
