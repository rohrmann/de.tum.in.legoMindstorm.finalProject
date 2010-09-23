package Bluetooth;

import misc.Action;
import Graph.Graph;
import Graph.Pair;

public class MessageComm {
	
	public static boolean sendUpdate(Graph graph, BTStreams streams){
		
		boolean result = BTCommunicator.sendMessageType(MessageType.UPDATE,streams) &&
		BTCommunicator.receiveAck(streams) && BTCommunicator.sendUpdate(graph, streams) &&
		BTCommunicator.receiveDone(streams);
		
		streams.closeStreams();
		
		return result;
				
	}
	
	public static boolean sendMove(Pair pair, BTStreams streams){
		
		boolean result = BTCommunicator.sendMessageType(MessageType.MOVE,streams) && 
		BTCommunicator.receiveAck(streams) && BTCommunicator.sendMove(pair, streams) &&
		BTCommunicator.receiveDone(streams);
		
		streams.closeStreams();
		
		return result;
	}
	
	public static boolean sendAction(Action action, BTStreams streams){
		boolean result = BTCommunicator.sendMessageType(MessageType.ACTION,streams) &&
		BTCommunicator.receiveAck(streams) && BTCommunicator.sendAction(action, streams) &&
		BTCommunicator.sendAction(action, streams);
		
		streams.closeStreams();
		
		return result;
	}
	
	public static boolean sendMap(Graph graph, BTStreams streams){
		boolean result = BTCommunicator.sendMessageType(MessageType.MAP,streams) &&
		BTCommunicator.receiveAck(streams) && BTCommunicator.sendGraph(graph, streams) &&
		BTCommunicator.receiveDone(streams);
	
		streams.closeStreams();
		
		return result;
	}

}
