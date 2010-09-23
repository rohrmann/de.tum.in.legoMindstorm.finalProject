package Bluetooth;

import misc.Action;
import Graph.Graph;
import Graph.Pair;

public class MessageComm {
	
	public static boolean sendUpdate(Graph graph, BTStreams streams){
		if(!BTCommunicator.sendMessageType(MessageType.UPDATE,streams))
			return false;
		
		if(!BTCommunicator.receiveAck(streams))
			return false;
		
		if(!BTCommunicator.sendUpdate(graph, streams))
			return false;
		
		if(!BTCommunicator.receiveDone(streams))
			return false;
		
		return true;
				
	}
	
	public static boolean sendMove(Pair pair, BTStreams streams){
		if(!BTCommunicator.sendMessageType(MessageType.MOVE,streams))
			return false;
		
		if(!BTCommunicator.receiveAck(streams))
			return false;
		
		if(!BTCommunicator.sendMove(pair, streams))
			return false;
		
		if(!BTCommunicator.receiveDone(streams))
			return false;
		
		return true;
	}
	
	public static boolean sendAction(Action action, BTStreams streams){
		if(!BTCommunicator.sendMessageType(MessageType.ACTION,streams))
			return false;
		
		if(!BTCommunicator.receiveAck(streams))
			return false;
		
		if(!BTCommunicator.sendAction(action, streams))
			return false;
		
		if(!BTCommunicator.receiveDone(streams))
			return false;
		
		return true;
	}
	
	public static boolean sendMap(Graph graph, BTStreams streams){
		if(!BTCommunicator.sendMessageType(MessageType.MAP,streams))
			return false;
		
		if(!BTCommunicator.receiveAck(streams))
			return false;
		
		if(!BTCommunicator.sendGraph(graph, streams))
			return false;
		
		if(!BTCommunicator.receiveDone(streams))
			return false;
		
		return true;
	}

}
