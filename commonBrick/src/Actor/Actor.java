package Actor;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorSensor;
import lejos.robotics.navigation.TachoPilot;
import misc.RobotType;
import miscBrick.Config;
import miscBrick.Robot;

import Bluetooth.BluetoothConnection;
import Bluetooth.MessageType;
import BluetoothBrick.BTBrickFactory;
import Color.Color;
import ColorBrick.ColorSettings;
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
		
		navi = new RoomNavigator(robot,map); 
	}
	public void start(){
		boolean running = true;
		
		BluetoothConnection conn = BTBrickFactory.createConnection();
		
		DataOutputStream dos = conn.getDataOutputStream();
		DataInputStream dis = conn.getDataInputStream();
		
		int command = 0;
		while(running){
			
			command = dis.readInt();
			dos.writeInt(MessageType.ACK.toInt());
			dos.flush();
			
			switch(command){
			//terminate
			case MessageType.TERMINATE.toInt():
				running = false;
				Bluetooth.done(dos,dis);
				break;
			//move
			case MessageType.MOVE.toInt():
				Pair pos = Bluetooth.receiveMove(conn);
				navi.moveTo(pos);
				Bluetooth.done(dos,dis);
				break;
			//action
			case MessageType.ACTION.toInt():
				Action action = Bluetooth.receiveAction(conn);
				
				navi.turn(findDirection(navi.getPosition(),action.src));
				
				prolog();
				navi.moveStraightForward(abs(action.src.getX()-action.dest.getX())+abs(action.src.getY()-action.dest.getY())-1);
				epilog();
				
				Bluetooth.done(conn);
				
				break;
				
			case MessageType.MAP.toInt():
				Graph graph = Bluetooth.receiveMap(conn);
				navi.setGraph(graph,getType());
				Bluetooth.done(conn);
				break;
			}
		}
	}
	
	public abstract RobotType getType();
	public abstract void prolog();
	public abstract void epilog();
}
