import miscBrick.Config;
import misc.Direction;
import miscBrick.Robot;
import miscBrick.RoomInformation;
import ErrorHandlingBrick.ErrorInformation;
import Graph.Graph;
import NavigationBrick.CheckRoom;
import NavigationBrick.DriveForward;
import NavigationBrick.FollowLine;
import NavigationBrick.RoomNavigator;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.Arbitrator;

public class PusherFunctions {

	Robot robot;
	RoomNavigator navigator;
	
	public PusherFunctions(Robot robot, Graph map, ErrorInformation errorinfo)			//Konstruktor
	{
		this.robot = robot;
		navigator = new RoomNavigator(robot, map, errorinfo);
	}
	
	//Bestimmt aus dem fuenfstelligen Array Richtungsangaben fuer die Movebewegung
	public Direction getMoveDirectionPusher(int[] a) {
		if(a[1] == a[3]) {
			if(a[2] -a[4] > 0) return Direction.SOUTH;
			else return Direction.NORTH;
		}
		else {
			if(a[1] - a[3] > 0) return Direction.WEST;
			else return Direction.EAST;
		}
	}

	//Bestimmt aus dem fuenfstelligen Array die Anzahl der Felder, die der Stein bewegt werden muss
	public int getFieldsToMove(int[] a) {
		return Math.abs(a[1]+a[2]-a[3]-a[4])-1;
	}

	public RoomNavigator getNavigator()
	{
		return navigator;
	}

	public void push()
	{

		float travelDistance = Config.lineLength + Config.squareSize - Config.boxSize/2 - Config.wheelToBucket;		//Distanz, die noch zurueckgelegen werden soll	

		RoomInformation roomInfo = new RoomInformation();

		DriveDistanceForward driveDistanceForward = new DriveDistanceForward(robot, travelDistance);
		FollowLine followLine = new FollowLine(robot, roomInfo);
		DriveForward driveForward = new DriveForward(robot, roomInfo);
		CheckRoom checkRoom = new CheckRoom(robot, (int)(travelDistance - Config.pusherBackOffDistance )-Config.pusherCheckRoomTolerance, Config.pusherCheckRoomTolerance, roomInfo, navigator);

		// Vorwaerts fahren bis die Kiste auf der naechsten Kreuzung liegt
		System.out.println("Follow line for "+travelDistance+"cm ...");
		Behavior[] driveForwardAndFollow = new Behavior[]{driveDistanceForward, followLine};
		Arbitrator a = new Arbitrator(driveForwardAndFollow, true);
		a.start();

		// rueckwaerts
		System.out.println("Travel back for "+Integer.toString((int)Config.pusherBackOffDistance)+"cm ...");
		robot.getPilot().travel(-Config.pusherBackOffDistance);

		// Drehung mit Linienkontrolle
		System.out.println("turn 90...");
		robot.getPilot().rotate(90);
		System.out.println("looking for line...");
		robot.getPilot().rotate(180, true);					// guckt nach Linie, nach der er sich, nach seiner theoretischen 180 Grad Drehung dann richten kann
		while(!robot.getLeftLight().groundChange())
		{ Thread.yield(); }
		while(robot.getLeftLight().groundChange())
		{ Thread.yield(); }
		robot.getPilot().stop();

		// zur Kreuzung zurueckkehren
		System.out.println("return to room...");
		Behavior[] returnToRoom = new Behavior[]{driveForward, followLine, checkRoom};
		a = new Arbitrator(returnToRoom, true);
		a.start();

		navigator.updateHeading(180);
	}

	
}
