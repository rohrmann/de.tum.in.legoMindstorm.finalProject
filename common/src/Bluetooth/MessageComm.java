package Bluetooth;

import misc.Action;
import Graph.Graph;
import Graph.Pair;

public class MessageComm {
	
	public static boolean sendUpdate(Graph graph, BTStreams streams){
		
		boolean result = BTCommunicator.sendMessageType(MessageType.UPDATE,streams);
				
		result = result && BTCommunicator.sendUpdate(graph, streams);
		
		result = result &&	BTCommunicator.receiveDone(streams);
		
		//streams.close();
		
		return result;
				
	}
	
	public static boolean sendMove(Pair pair, BTStreams streams){
		
		boolean result = BTCommunicator.sendMessageType(MessageType.MOVE,streams);

		result = result && BTCommunicator.sendMove(pair, streams);
		
		result = result && BTCommunicator.receiveDone(streams);
		
		//streams.close();
		
		return result;
	}
	
	public static boolean sendAction(Action action, BTStreams streams){
		boolean result = BTCommunicator.sendMessageType(MessageType.ACTION,streams);
		
		result = result && BTCommunicator.sendAction(action, streams);
		
		result = result && BTCommunicator.receiveDone( streams);
		
		//streams.close();
		
		return result;
	}
	
	public static boolean sendMap(Graph graph, BTStreams streams){
		boolean result = BTCommunicator.sendMessageType(MessageType.MAP,streams);
		
		result = result && BTCommunicator.sendGraph(graph, streams);
		
		result = result &&	BTCommunicator.receiveDone(streams);
	
		//streams.close();
		
		return result;
	}

}
