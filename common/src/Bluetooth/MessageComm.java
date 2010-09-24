package Bluetooth;

import misc.Action;
import misc.RobotType;
import Graph.Graph;
import Graph.Pair;

public class MessageComm {
	
	public static boolean sendMove(RobotType robotType,Pair pair, BTStreams streams){
		boolean result = BTCommunicator.sendMessageType(MessageType.MOVE,streams);

		result = result && BTCommunicator.sendRobotType(robotType,streams);
		
		result = result && BTCommunicator.sendMove(pair, streams);
		
		result = result && BTCommunicator.recvDone(streams);
		
		return result;
	}
	
	public static boolean sendAction(RobotType robotType, Action action, BTStreams streams){
		boolean result = BTCommunicator.sendMessageType(MessageType.ACTION,streams);
		
		result = result && BTCommunicator.sendRobotType(robotType,streams);
		
		result = result && BTCommunicator.sendAction(action, streams);
		
		result = result && BTCommunicator.recvDone( streams);
			
		return result;
	}
	
	public static boolean sendMap(Graph graph, BTStreams streams){
		boolean result = BTCommunicator.sendMessageType(MessageType.MAP,streams);
				
		result = result && BTCommunicator.sendGraph(graph, streams);
		
		result = result &&	BTCommunicator.recvDone(streams);
		
		return result;
	}
	
	public static boolean sendFinish(BTStreams streams){
		boolean result = BTCommunicator.sendMessageType(MessageType.FINISH, streams);
		result = result && BTCommunicator.recvDone(streams);
		
		return result;
	}

}
