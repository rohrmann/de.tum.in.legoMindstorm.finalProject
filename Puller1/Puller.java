import Color.Color;
import Color.ColorSettings;
import Graph.BuildGraph;
import Graph.Graph;

import Light.LightSettings;
import Navigation.RoomNavigator;
import lejos.nxt.*;
import lejos.nxt.addon.ColorSensor;
import lejos.robotics.navigation.TachoPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import misc.Direction;
import misc.Robot;

public class Puller
{

	public static boolean input = false;
	static Robot robot;
	static UltrasonicSensor usensor;
	static ColorSettings color;
	static ColorSensor colorSensor;
	static LightSensor leftLightSensor;
	static LightSettings leftLightSettings;
	static LightSensor rightLightSensor;
	static LightSettings rightLightSettings;
	static TachoPilot pilot;
	static int height = 255;
	
	public static void main(String[] args) {
	
		int x = 2;
		int times = 5;
		int pollingInterval = 25;
		int toleranceLeft = 5;
		int toleranceRight = 5;
		Color[] colors = {Color.WHITE,Color.BLACK,Color.BLUE,Color.RED,Color.GREEN,Color.YELLOW};
		
		pilot = new TachoPilot(5.6f,10.5f,Motor.A,Motor.B);
		pilot.setSpeed(80);
			
		usensor = new UltrasonicSensor(SensorPort.S3);
		colorSensor = new ColorSensor(SensorPort.S4);
		leftLightSensor = new LightSensor(SensorPort.S2);
		rightLightSensor = new LightSensor(SensorPort.S1);
		
		color = new ColorSettings(colorSensor);
		color.init(colors,times,pollingInterval);
		leftLightSettings = new LightSettings(leftLightSensor);
		rightLightSettings = new LightSettings(rightLightSensor);
		rightLightSettings.init(toleranceRight);
		
		leftLightSettings.init(toleranceLeft);
		
		robot = new Robot(pilot,color,leftLightSettings, rightLightSettings);
		
		RoomNavigator navi = new RoomNavigator(robot, GraphExample.getGraph());
		//navi.updateHeading(0);
		//navi.move(Direction.NORTH);
		liftArm(160);
		
		navi.move(Direction.EAST);
		navi.move(Direction.EAST);
		navi.move(Direction.EAST);
		
		while(height == 255) {
		height = usensor.getDistance();
		}
		
		Behavior getAndMoveBox = new GetAndMoveBox(Direction.EAST, 2, robot, usensor, navi);
		Behavior waitForInput = new WaitForInput();
		//Behavior getBox = new GetBox(robot, usensor);
		//Behavior moveBox = new MoveBox(x, usensor, robot);
		Behavior[] bArray = {getAndMoveBox, waitForInput};
		Arbitrator arby = new Arbitrator(bArray, true);
		arby.start();
		
	}
	
	//Hebt den Arm hoch
	public static void liftArm(int angle){
		Motor.C.setSpeed(60);
		Motor.C.rotateTo(angle, false);
		Motor.C.lock(100);
	}
}