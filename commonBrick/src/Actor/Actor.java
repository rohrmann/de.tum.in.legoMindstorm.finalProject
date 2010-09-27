package Actor;


import java.util.ArrayList;
import java.util.List;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.addon.ColorSensor;
import lejos.robotics.navigation.TachoPilot;
import misc.Action;
import misc.RobotType;
import miscBrick.Config;
import miscBrick.Robot;

import Bluetooth.BTStreams;
import Bluetooth.MessageType;
import BluetoothBrick.BTConnectionBrick;
import Graph.Graph;
import Graph.Pair;
import LightBrick.LightSettings;
import NavigationBrick.RoomNavigator;
import Bluetooth.BTCommunicator;
import Color.Color;
import ColorBrick.ColorSettings;

abstract public class Actor {	
	protected RoomNavigator navi;
	protected Robot robot;
	
	protected boolean hasToken;
	
	public Actor(){
		TachoPilot pilot = new TachoPilot(Config.wheelHeight,getWheelToWheel(),Motor.A,Motor.B);
		pilot.setMoveSpeed(Config.defaultMoveSpeed);
		pilot.setTurnSpeed(Config.defaultTurnSpeed);
		ColorSensor colorSensor = new ColorSensor(SensorPort.S3);
		LightSensor leftLightSensor = new LightSensor(SensorPort.S1);
		LightSensor rightLightSensor = new LightSensor(SensorPort.S2);		
		Color[] colors = {Color.WHITE,Color.BLACK,Color.BLUE,Color.RED,Color.GREEN,Color.YELLOW};
		ColorSettings color = new ColorSettings(colorSensor);
		color.init(colors,Config.colorScanTimes,Config.mapperPollingInterval);
		LightSettings leftLightSettings = new LightSettings(leftLightSensor);
		LightSettings rightLightSettings = new LightSettings(rightLightSensor);
		leftLightSettings.init(getLightTolerance());
		rightLightSettings.init(getLightTolerance());
		
		robot = new Robot(pilot,color,leftLightSettings, rightLightSettings);
		Graph map = new Graph();
		
		navi = new RoomNavigator(robot,map); 
		
		hasToken = false;
	}
	
	public void start(){
		
		init();
		
		BTConnectionBrick conn = new BTConnectionBrick();
		
		List<Command> commands = recvCommands(conn);
		
		conn.close();
		
		System.out.println("Get Brick Connection");
		BTStreams brick = getBrickConnection();
		
		System.out.println("Execute Commands");
		for(Command command : commands){
			System.out.println(command);
			if(command.getType() == getType()){
				
				if(!hasToken){
					acquireToken(brick);
					hasToken = true;
				}
				command.execute(this);
			}else if(hasToken){
				releaseToken(brick);
				hasToken = false;
			}
			
			command.update(this);
		}
		
		playSound();
		
	}
	
	public void playSound(){
		short h = 494;
		short c = 523;
		short d = 587;
		short e = 659;
		short f = 698;

		short [] note = {c,100, 0,35,d,50, 0,5, e,50, 0,35, d,50, 0,35,f,100,0,35,e,35,0,35,d,5,0,35,h,35,0,5,c,100};
		while(true){
			for(int i=0;i <note.length; i+=2) {
				short w = note[i+1];
				int n = note[i];
				if (n != 0) Sound.playNote(Sound.PIANO,n,w*10);
				else{
					try {
						Thread.sleep(w*10);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
					}
				}
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
			}
		}
	}
	
	public void acquireToken(BTStreams conn){
		while(BTCommunicator.recvInt(conn) == -1){
		}
		
		Sound.beep();
	}
	
	public void releaseToken(BTStreams conn){
		while(!BTCommunicator.sendInt(0, conn)){
			
		}
		
		Sound.buzz();
	}
	
	public List<Command> recvCommands(BTStreams connection){
		List<Command> result = new ArrayList<Command>();
		
		boolean running = true;
		
		while(running){
			MessageType message = BTCommunicator.recvMessageType(connection);
			
			switch(message){
			case FINISH:
				System.out.println("Finish");
				running = false;
				BTCommunicator.done(connection);
				break;
			case MAP:
				System.out.println("Received map");
				Graph graph = BTCommunicator.recvGraph(connection);
				navi.setGraph(graph, getType());
				BTCommunicator.done(connection);
				break;
			case ACTION:
				RobotType type = BTCommunicator.recvRobotType(connection);
				Action action = BTCommunicator.recvAction(connection);
				Command command = new ActionCommand(action.getSrc(),action.getDest(),type);
				result.add(command);
				System.out.println(command);
				BTCommunicator.done(connection);
				break;
			case MOVE:
				type = BTCommunicator.recvRobotType(connection);
				Pair pos = BTCommunicator.recvPair(connection);
				command = new MoveCommand(pos,type);
				result.add(command);
				System.out.println(command);
				BTCommunicator.done(connection);
				break;
			}
		}
		
		
		return result;
	}
	
	public abstract RobotType getType();
	public abstract float getWheelToWheel();
	public abstract int getLightTolerance();
	public abstract void prolog();
	public abstract void epilog();
	public abstract void init();
	public abstract BTStreams getBrickConnection();
}
