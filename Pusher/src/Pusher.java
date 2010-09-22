import misc.Config;
import misc.Direction;
import misc.Robot;
import misc.RoomInformation;
import Color.Color;
import Color.ColorSettings;
import Graph.Graph;
import Graph.Pair;
import Light.LightSettings;
import Navigation.CheckRoom;
import Navigation.DriveDistanceForward;
import Navigation.DriveForward;
import Navigation.FollowLine;
import Navigation.RoomNavigator;
import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorSensor;
import lejos.robotics.navigation.TachoPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.Arbitrator;

public class Pusher {

	public static void main(String[] args) {	
		
		TachoPilot pilot = new TachoPilot(Config.wheelHeight,Config.wheelToWheel,Motor.A,Motor.B);
		ColorSensor colorSensor = new ColorSensor(SensorPort.S3);
		LightSensor leftLightSensor = new LightSensor(SensorPort.S1);
		LightSensor rightLightSensor = new LightSensor(SensorPort.S2);
		int times = Config.colorScanTimes;
		int pollingInterval = Config.mapperPollingInterval;
		Color[] colors = {Color.WHITE,Color.BLACK,Color.BLUE,Color.RED,Color.GREEN,Color.YELLOW};
		ColorSettings color = new ColorSettings(colorSensor);
		color.init(colors,times,pollingInterval);
		LightSettings leftLightSettings = new LightSettings(leftLightSensor);
		LightSettings rightLightSettings = new LightSettings(rightLightSensor);
		int tolerance = Config.lightTolerance;
		leftLightSettings.init(tolerance);
		rightLightSettings.init(tolerance);

		Robot robot = new Robot(pilot,color,leftLightSettings, rightLightSettings);
		
		robot.getPilot().setMoveSpeed(Config.pusherMoveSpeed);
		robot.getPilot().setTurnSpeed(Config.pusherTurnSpeed);

		Pusher pusher = new Pusher(robot, GraphExample.getGraph());

		System.out.println("press button to start test!");
		Button.waitForPress();
		System.out.println("RUNNING...");

		//TODO waitForConnection()

		int[] a = {0,2,2,6,2};

		pusher.getNavigator().moveTo(pusher.getNavigator().getPosition(), new Pair(a[1],a[2]));

		if(a[0] == 0) {
			pusher.getNavigator().turn(getMoveDirectionPusher(a));
			pusher.push(getFieldsToPush(a));
		}

		//closeConnection()
	}
	
	//Bestimmt aus dem fuenfstelligen Array Richtungsangaben fuer die Movebewegung
	public static Direction getMoveDirectionPusher(int[] a) {
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
	public static int getFieldsToPush(int[] a) {
		return Math.abs(a[1]+a[2]-a[3]-a[4]);
	}
	
	
	
	Robot robot;
	RoomNavigator navigator;
	
	public Pusher(Robot robot, Graph map)			//Konstruktor
	{
		this.robot = robot;
		navigator = new RoomNavigator(robot, map);
	}

	public RoomNavigator getNavigator()
	{
		return navigator;
	}

	public void push(int fields)
	{
		for(int i=0; i<fields-1; i++)
			navigator.goToNextRoom();
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

	
}
