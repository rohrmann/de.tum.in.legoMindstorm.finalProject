package Actor;


import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.bluetooth.RemoteDevice;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorSensor;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import lejos.robotics.navigation.TachoPilot;
import misc.Action;
import misc.RobotType;
import misc.Update;
import misc.Direction;
import miscBrick.CommandByMax;
import miscBrick.Config;
import miscBrick.Robot;

import Bluetooth.BTStreams;
import Bluetooth.MessageComm;
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

abstract public class ActorByMax {	
	protected RoomNavigator navi;
	protected Robot robot;
	
	private BTStreams pullerStreams = null;
	private BTStreams pusherStreams = null;
	
	public ActorByMax(){
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
		
		if(getType()==RobotType.PULLER){
			while(!connectToPusher()){
				
			}
			waitForCommand();
		} else if(getType()==RobotType.PUSHER){
			List<CommandByMax> commands = PusherReceiveCommands.getAllCommands();
			while(!connectToPuller()){
				
			}
			distributeCommands(commands);
		}
	}
	
	public boolean connectToPusher(){
		BTConnectionBrick btcb = new BTConnectionBrick();
		btcb.openConnection();
		return true;
	}
	
	public boolean connectToPuller(){
		RemoteDevice rd = Bluetooth.getKnownDevice(RobotType.PULLER.name());
		BTConnectionBrick btcb = new BTConnectionBrick();
		btcb.openConnectionTo(rd);
		return true;
	}
	
	public void distributeCommands(List<CommandByMax> commands){
		
		for(CommandByMax command: commands){
			boolean done = false;
			while(!done){
				if(command.getRobotType()==RobotType.PULLER){
					done = sendCommandToPuller(command, pullerStreams);
				}else if (command.getRobotType()==RobotType.PUSHER){
					done = doCommand(command);
				}else{
					done = false;
				}
			}
		}
		
	}
	
	public boolean sendCommandToPuller(CommandByMax command, BTStreams pullerStreams){
		MessageType message = command.getMessageType();
		switch(message){
		case TERMINATE:
			return true;
		case MOVE:
			return MessageComm.sendMove(command.getPair(), pullerStreams);
		case ACTION:
			return MessageComm.sendAction(RobotType.PULLER, command.getAction(), pullerStreams);
		case MAP:
			return MessageComm.sendMap(command.getGraph(), pullerStreams);
		case UPDATE:
			return MessageComm.sendUpdate(RobotType.PULLER, command.getGraph(), pullerStreams);
		}
		return false;
	}
	
	public boolean doCommand(CommandByMax command){
		boolean running = true;
		
		MessageType message = command.getMessageType();
		
		switch(message){
		//terminate
		case TERMINATE:
			running = false;
			BTCommunicator.done(conn);
			return true;
		//move
		case MOVE:
			Pair pos = command.getPair();
			System.out.println("Move to " + pos);
			navi.moveTo(pos);
			BTCommunicator.done(conn);
			System.out.println("Moved");
			return true;
		//action
		case ACTION:
			Action action = command.getAction();
			System.out.println("Action " + action);
			navi.turn(Direction.findDirection(navi.getPosition(),action.getSrc()));
			prolog();
			navi.moveStraightForward(Math.abs(action.getSrc().getX()-action.getDest().getX())+Math.abs(action.getSrc().getY()-action.getDest().getY())-1);
			epilog();
			BTCommunicator.done(conn);
			System.out.println("Action done");
			return true;
		case MAP:
			Graph graph = command.getGraph();
		
			navi.setGraph(graph,getType());
			BTCommunicator.done(conn);
			System.out.println("Map received");
			return true;

		case UPDATE:
			Update update = command.getUpdate();
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
			return true;
		}
		return false;
	}
	
	
	public abstract RobotType getType();
	public abstract void prolog();
	public abstract void epilog();
	public abstract void init();
}
