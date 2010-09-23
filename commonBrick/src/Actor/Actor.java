package Actor;


import java.util.List;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorSensor;
import lejos.robotics.navigation.TachoPilot;
import misc.Action;
import misc.RobotType;
import misc.Update;
import misc.Direction;
import miscBrick.Config;
import miscBrick.Robot;

import Bluetooth.MessageType;
import BluetoothBrick.BTConnectionBrick;
import Graph.Graph;
import Graph.Pair;
import Graph.Type;
import LightBrick.LightSettings;
import NavigationBrick.RoomNavigator;
import Bluetooth.BTCommunicator;
import Color.Color;
import ColorBrick.ColorSettings;

abstract public class Actor {	
	protected RoomNavigator navi;
	protected Robot robot;
	
	public Actor(){
		TachoPilot pilot = new TachoPilot(Config.wheelHeight,Config.wheelToWheel,Motor.A,Motor.B);
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
		leftLightSettings.init(Config.lightTolerance);
		rightLightSettings.init(Config.lightTolerance);
		
		robot = new Robot(pilot,color,leftLightSettings, rightLightSettings);
		Graph map = new Graph();
		
		navi = new RoomNavigator(robot,map); 
	}
	
	public void start(){
		
		init();
		
		boolean running = true;
		
		BTConnectionBrick conn = new BTConnectionBrick();
		
		MessageType message;

		while(running){
			message = BTCommunicator.receiveMessageType(conn);			
			
			switch(message){
			//terminate
			case TERMINATE:
				running = false;
				BTCommunicator.done(conn);
			//	conn.close();
				break;
			//move
			case MOVE:
		
				Pair pos = BTCommunicator.receiveMove(conn);
				System.out.println("Move to " + pos);
				navi.moveTo(pos);
				BTCommunicator.done(conn);
			//	conn.close();
				System.out.println("Moved");
				break;
			//action
			case ACTION:
				Action action = BTCommunicator.receiveAction(conn);
				System.out.println("Action " + action);
				navi.turn(Direction.findDirection(navi.getPosition(),action.getSrc()));
				prolog();
				navi.moveStraightForward(Math.abs(action.getSrc().getX()-action.getDest().getX())+Math.abs(action.getSrc().getY()-action.getDest().getY())-1);
				epilog();
				BTCommunicator.done(conn);
			//	conn.close();
				
				System.out.println("Action done");
				break;
			case MAP:
				Graph graph = BTCommunicator.receiveGraph(conn);
			
				navi.setGraph(graph,getType());
				//System.out.println("Pos:" + navi.getPosition());
				//Button.waitForPress();
				BTCommunicator.done(conn);
				//conn.closeStreams();
				System.out.println("Map received");
				break;
				
			case UPDATE:
				Update update = BTCommunicator.receiveUpdate(conn);
				
				List<Pair> boxes = navi.getGraph().getBoxes();
			
				Graph map = navi.getGraph();
				
				for(Pair box: boxes){
					map.setNode(box, Type.EMPTY);
				}
				
				for(Pair box: update.boxes){
					map.setNode(box, Type.BOX);
				}
				
				if(getType() == RobotType.PULLER){
					Pair rPos = map.getPusher();
					
					if(rPos != null){
						map.setNode(rPos, Type.EMPTY);
					}
					
					map.setNode(update.pusher,Type.PUSHSTART);
				}
				else if(getType() == RobotType.PUSHER){
					Pair rPos = map.getPuller();
					
					if(rPos != null){
						map.setNode(rPos, Type.EMPTY);
					}
					
					map.setNode(update.puller,Type.PULLSTART);
				}
				
				BTCommunicator.done(conn);
				//conn.close();
				
				System.out.println("Update received");
			}
		}
		
		conn.close();
	}
	
	public abstract RobotType getType();
	public abstract void prolog();
	public abstract void epilog();
	public abstract void init();
}
