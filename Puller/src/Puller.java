
import java.io.IOException;

import BluetoothBrick.BTBrick;
import BluetoothBrick.BTReceiveCommand;
import Bluetooth.BTReceiveNodes;
import Color.Color;
import ColorBrick.ColorSettings;
import Graph.Graph;
import Graph.Pair;
import LightBrick.LightSettings;
import NavigationBrick.RoomNavigator;
import lejos.nxt.*;
import lejos.nxt.addon.ColorSensor;
import lejos.robotics.navigation.TachoPilot;
import misc.Direction;
import misc.GraphExample;
import miscBrick.Robot;

public class Puller
{
	public static int fields;
	public static Direction turnDirection;
	static Robot robot;
	static RoomNavigator navi;
	static ColorSettings color;
	static ColorSensor colorSensor;
	static LightSensor leftLightSensor;
	static LightSettings leftLightSettings;
	static LightSensor rightLightSensor;
	static LightSettings rightLightSettings;
	static TachoPilot pilot;
	static int height = 255;
	static Pair actualPosition;
	static Pair newPosition;
	static int oldX;
	static int newX;
	static int oldY;
	static int newY;

	public static void main(String[] args) {

		BTBrick.connectToPC();
		System.out.println("press button to start test");
		Button.waitForPress();
		//System.out.println("RUNNING...");
		
		int times = 5;
		int pollingInterval = 25;
		int toleranceLeft = 4;
		int toleranceRight = 4;
		Color[] colors = {Color.WHITE,Color.BLACK,Color.BLUE,Color.RED,Color.GREEN,Color.YELLOW};

		pilot = new TachoPilot(5.6f,10.5f,Motor.A,Motor.B);
		pilot.setSpeed(100);

		colorSensor = new ColorSensor(SensorPort.S3);
		leftLightSensor = new LightSensor(SensorPort.S1);
		rightLightSensor = new LightSensor(SensorPort.S2);

		color = new ColorSettings(colorSensor);
		color.init(colors,times,pollingInterval);
		leftLightSettings = new LightSettings(leftLightSensor);
		rightLightSettings = new LightSettings(rightLightSensor);
		rightLightSettings.init(toleranceRight);	
		leftLightSettings.init(toleranceLeft);

		robot = new Robot(pilot,color,leftLightSettings, rightLightSettings);


		System.out.println("press button to start test");
		Button.waitForPress();
		System.out.println("RUNNING...");

		
		
		liftArm(160);

		int[] a = {0,0,0,0,0};
		Graph g = GraphExample.getGraph();
		navi = new RoomNavigator(robot, g);

		while(true){
			try {
				g = BTReceiveNodes.receiveNodes(BTBrick.getOutput(), BTBrick.getInput(), true);
			} catch (IOException e) {
			}
			navi.setGraph(g);
			try {
				a = BTReceiveCommand.receiveCommand(BTBrick.getOutput(),BTBrick.getInput());
			} catch (Exception e) {
			}


			//navi.moveTo(navi.getPosition(),new Pair(a[1],a[2]));
			if (a[0] == 0)
				getAndMoveBox(getMoveDirectionPuller(a),getFieldsToMove(a));

		}
	}

	//Hebt den Arm hoch
	public static void liftArm(int angle){
		Motor.C.setSpeed(60);
		Motor.C.rotateTo(angle, false);
		Motor.C.lock(100);
	}

	//Bestimmt aus dem fuenfstelligen Array Richtungsangaben fuer die Movebewegung
	public static Direction getMoveDirectionPuller(int[] a) {
		if(a[1] == a[3]) {
			if(a[2] -a[4] < 0) return Direction.SOUTH;
			else return Direction.NORTH;
		}
		else {
			if(a[1] - a[3] < 0) return Direction.WEST;
			else return Direction.EAST;
		}
	}

	//Bestimmt aus dem fuenfstelligen Array die Anzahl der Felder, die der Stein bewegt werden muss
	public static int getFieldsToMove(int[] a) {
		return Math.abs(a[1]+a[2]-a[3]-a[4]);
	}

	//Faehrt den letzten Abschnitt zur Kiste und bewegt diese an die angegebene Position
	public static void getAndMoveBox(Direction turnDirection, int fields) {

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

		robot.getPilot().rotate(2,false);

		while (robot.getRightLight().getLightValue() <= robot.getRightLight().getGroundValue() - robot.getRightLight().getTolerance() ||robot.getLeftLight().getLightValue() <= robot.getLeftLight().getGroundValue() - robot.getLeftLight().getTolerance()){
			if (robot.getRightLight().getLightValue() <= robot.getRightLight().getGroundValue() - robot.getRightLight().getTolerance()) {
				robot.getPilot().rotate(-3, false);
				robot.getPilot().forward();
			}
			else if (robot.getLeftLight().getLightValue() <= robot.getLeftLight().getGroundValue() - robot.getLeftLight().getTolerance()) {
				robot.getPilot().rotate(3, false);
				robot.getPilot().forward();
			}
		}

		//Roboter faehrt an die Kiste hin
		robot.getPilot().reset();
		robot.getPilot().forward();

		while(4.0f > robot.getPilot().getTravelDistance()) {
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

		//Roboter dreht sich um 180 Grad
		robot.getPilot().rotate(190,false);
		while(robot.getLeftLight().groundChange()) {
			LCD.drawString("Ich korrigiere die Drehung",0,6);
			robot.getPilot().rotate(-1,false);
		}
		robot.getPilot().rotate(3,false);

		robot.getPilot().stop();
		navi.updateHeading(turnDirection.opposite());

		//Nimmt die Kiste auf
		Puller.liftArm(2);

		//Roboter faehrt zum naechsten Raum
		robot.getPilot().forward();
		while(!robot.getColor().getColorName().isRoomColor()){};

		for(int i = 0; i < fields; i++) {
			navi.move(turnDirection.opposite());
		}

		//Roboter faehrt ein Stueck nach vorne und legt die Kiste ab
		robot.getPilot().reset();
		robot.getPilot().forward();
		while (15.f > robot.getPilot().getTravelDistance()){
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

		//Roboter faehrt bis zum naechsten Raum
		robot.getPilot().forward();
		while(!robot.getColor().getColorName().isRoomColor()) {}
		robot.getPilot().stop();

		//Aktualisierung der Position
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
	}

}