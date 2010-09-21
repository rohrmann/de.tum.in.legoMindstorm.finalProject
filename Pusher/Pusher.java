import Color.Color;
import Color.ColorSettings;
import Graph.Graph;
import Light.LightSettings;
import Navigation.*;
import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorSensor;
import lejos.robotics.navigation.TachoPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import misc.Direction;
import misc.Robot;
import misc.RoomInformation;
import misc.Config;

public class Pusher {

	public static void main(String[] args) {	
	
		TachoPilot pilot = new TachoPilot(5.6f,10.35f,Motor.A,Motor.B);
		ColorSensor colorSensor = new ColorSensor(SensorPort.S3);
		LightSensor leftLightSensor = new LightSensor(SensorPort.S1);
		LightSensor rightLightSensor = new LightSensor(SensorPort.S2);
		int times = 5;
		int pollingInterval = 25;
		Color[] colors = {Color.WHITE,Color.BLACK,Color.BLUE,Color.RED,Color.GREEN,Color.YELLOW};
		ColorSettings color = new ColorSettings(colorSensor);
		color.init(colors,times,pollingInterval);
		LightSettings leftLightSettings = new LightSettings(leftLightSensor);
		LightSettings rightLightSettings = new LightSettings(rightLightSensor);
		int tolerance = 5;
		leftLightSettings.init(tolerance);
		rightLightSettings.init(tolerance);

		Robot robot = new Robot(pilot,color,leftLightSettings, rightLightSettings);
		
		robot.getPilot().setMoveSpeed(Config.pusherMoveSpeed);
		robot.getPilot().setTurnSpeed(Config.pusherTurnSpeed);

		PusherFunctions pusher = new PusherFunctions(robot, GraphExample.getGraph());

		System.out.println("press button to start test!");
		Button.waitForPress();
		System.out.println("RUNNING...");

		//TODO waitForConnection()

		int[] a = {0,0,0,3,0};

		Direction moveDirection;

		//moveTo(pusher.getNavigator().getX(),pusher.getNavigator().getY(),a[1],a[2]);

		if(a[0] == 0) {
			if(Direction.NORTH.equals(pusher.getMoveDirectionPusher(a))) {
				pusher.getNavigator().turnNorth();
			}
			else if(Direction.SOUTH.equals(pusher.getMoveDirectionPusher(a))) {
				pusher.getNavigator().turnSouth();
			}
			else if(Direction.EAST.equals(pusher.getMoveDirectionPusher(a))) {
				pusher.getNavigator().turnEast();
			}
			else {
				pusher.getNavigator().turnWest();
			}

			for(int i = 0; i < pusher.getFieldsToMove(a);i++){
				pusher.getNavigator().move(pusher.getNavigator().getHeading());
			}
			pusher.push();
		}

		//closeConnection()
	}

}
