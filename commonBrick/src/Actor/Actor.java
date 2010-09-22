package Actor;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorSensor;
import lejos.robotics.navigation.TachoPilot;
import misc.Action;
import misc.RobotType;
import miscBrick.Config;
import miscBrick.Helper;
import miscBrick.Robot;

import Bluetooth.BluetoothCommunicator;
import Bluetooth.BluetoothConnection;
import Bluetooth.MessageType;
import BluetoothBrick.BTBrickFactory;
import Color.Color;
import ColorBrick.ColorSettings;
import ErrorHandlingBrick.ErrorInformation;
import Graph.Graph;
import Graph.Pair;
import LightBrick.LightSettings;
import NavigationBrick.RoomNavigator;

abstract public class Actor {	
	protected RoomNavigator navi;
	protected Robot robot;
	
	public Actor(){
		TachoPilot pilot = new TachoPilot(Config.wheelHeight,Config.wheelToWheel,Motor.A,Motor.B);
		pilot.setMoveSpeed(Config.mapperMoveSpeed);
		ColorSensor colorSensor = new ColorSensor(SensorPort.S3);
		LightSensor leftLightSensor = new LightSensor(SensorPort.S1);
		LightSensor rightLightSensor = new LightSensor(SensorPort.S2);		
		Color[] colors = {Color.WHITE,Color.BLACK,Color.BLUE,Color.RED,Color.GREEN,Color.YELLOW};
		ColorSettings color = new ColorSettings(colorSensor);
		color.init(colors,Config.colorScanTimes,Config.mapperPollingInterval);
		LightSettings leftLightSettings = new LightSettings(leftLightSensor);
		LightSettings rightLightSettings = new LightSettings(rightLightSensor);
		int tolerance =5;
		leftLightSettings.init(tolerance);
		rightLightSettings.init(tolerance);
		
		robot = new Robot(pilot,color,leftLightSettings, rightLightSettings);
		Graph map = new Graph();
		
		ErrorInformation error = new ErrorInformation();
		error.setError(false);
		navi = new RoomNavigator(robot,map,error); 
	}
	public void start(){
		
		init();
		
		boolean running = true;
		
		BluetoothConnection conn = BTBrickFactory.createConnection();
		
		MessageType message;

		while(running){
			message = BluetoothCommunicator.receiveMessageType(conn);
			
			
			switch(message){
			//terminate
			case TERMINATE:
				running = false;
				BluetoothCommunicator.done(conn);
				break;
			//move
			case MOVE:
				Pair pos = BluetoothCommunicator.receiveMove(conn);
				navi.moveTo(pos);
				BluetoothCommunicator.done(conn);
				break;
			//action
			case ACTION:
				Action action = BluetoothCommunicator.receiveAction(conn);
				
				navi.turn(Helper.findDirection(navi.getPosition(),action.getSrc()));
				
				prolog();
				navi.moveStraightForward(Math.abs(action.getSrc().getX()-action.getDest().getX())+Math.abs(action.getSrc().getY()-action.getDest().getY())-1);
				epilog();
				
				BluetoothCommunicator.done(conn);
				
				break;
				
			case MAP:
				Graph graph = BluetoothCommunicator.receiveMap(conn);
				navi.setGraph(graph,getType());
				BluetoothCommunicator.done(conn);
				break;
			}
			
		}
	}
	
	public abstract RobotType getType();
	public abstract void prolog();
	public abstract void epilog();
	public abstract void init();
}
