package Actor;

import java.util.ArrayList;
import java.util.List;

import misc.Action;
import misc.RobotType;
import misc.Update;
import miscBrick.CommandByMax;

import Bluetooth.MessageType;
import BluetoothBrick.BTConnectionBrick;
import Graph.Graph;
import Graph.Pair;
import Bluetooth.BTCommunicator;


abstract public class PusherReceiveCommands {	

	
	public static List<CommandByMax> getAllCommands(){
		
		boolean running = true;
		
		BTConnectionBrick conn = new BTConnectionBrick();
		
		MessageType message;
		RobotType robotType;
		List<CommandByMax> commands = new ArrayList<CommandByMax>();
		while(running){
			robotType = BTCommunicator.receiveRobotType(conn);
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
				commands.add(new CommandByMax(message, robotType, pos, null, null, null));
				BTCommunicator.done(conn);
				break;
			//action
			case ACTION:
				Action action = BTCommunicator.receiveAction(conn);
				commands.add(new CommandByMax(message, robotType, null, action, null, null));
				BTCommunicator.done(conn);
				break;
			//map
			case MAP:
				Graph graph = BTCommunicator.receiveGraph(conn);
				commands.add(new CommandByMax(message, robotType, null, null, null, graph));
				BTCommunicator.done(conn);
				break;
			//update	
			case UPDATE:
				Update update = BTCommunicator.receiveUpdate(conn);
				commands.add(new CommandByMax(message, robotType, null, null, update, null));
				BTCommunicator.done(conn);
			}
		}
		
		conn.close();
		return commands;
	}
	
	public abstract RobotType getType();
	public abstract void prolog();
	public abstract void epilog();
	public abstract void init();
}
