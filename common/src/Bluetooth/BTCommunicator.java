package Bluetooth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import misc.Action;
import misc.Update;

import Graph.Graph;
import Graph.GraphTools;
import Graph.Node;
import Graph.Pair;
import Graph.Type;

public class BTCommunicator {
	
	public static boolean sendInt(int i, BTStreams connection){
		try{
			connection.getDataOutputStream().writeInt(i);
			connection.getDataOutputStream().flush();
			return true;
		}catch(IOException e){
		}
		
		return false;
	}
	
	public static int receiveInt(BTStreams connection){
		try{
			int result = connection.getDataInputStream().readInt();
			return result;
		}catch(IOException e){
		}
		
		return 0;
	}
	
	public static boolean done(BTStreams connection){
		try {
			connection.getDataOutputStream().writeInt(MessageType.DONE.toInt());
			connection.getDataOutputStream().flush();
			return true;
		} catch (IOException e) {
		}
		
		return false;
	}
	
	public static boolean receiveDone(BTStreams connection){
		try{
			int result = connection.getDataInputStream().readInt();
			
			if(MessageType.int2M(result) != MessageType.DONE){
				return false;
			}
			
			return true;
		}catch(IOException e){
			
		}
		
		return false;
	}
	
	public static boolean ack(BTStreams connection){
		try{
			connection.getDataOutputStream().writeInt(MessageType.ACK.toInt());
			connection.getDataOutputStream().flush();
			return true;
		}catch(IOException e){
		}
		
		return false;
	}
	
	public static boolean receiveAck(BTStreams connection){
		try{
			int result = connection.getDataInputStream().readInt();
			
			if(MessageType.int2M(result) == MessageType.ACK){
				return true;
			}
			
			return false;
		}catch(IOException e){
		}
		
		return false;
	}
	
	public static boolean sendMove(Pair pos,BTStreams connection){
		return sendPair(pos,connection);
		
	}
	
	public static Pair receiveMove(BTStreams connection){
		return receivePair(connection);
	}
	
	public static boolean sendPair(Pair pair, BTStreams connection){
		try{
			connection.getDataOutputStream().writeInt(pair.getX());
			connection.getDataOutputStream().writeInt(pair.getY());
			connection.getDataOutputStream().flush();
			
			return true;
		}catch(IOException e){
			
		}
		
		return false;
	}
	
	public static Pair receivePair(BTStreams connection){
		try{
			int x = connection.getDataInputStream().readInt();
			int y = connection.getDataInputStream().readInt();
			
			return new Pair(x,y);
		}catch(IOException e){
		}

		return null;
	}
	
	public static boolean sendAction(Action action, BTStreams connection){
		return sendPair(action.getSrc(),connection) && sendPair(action.getDest(),connection);
	}
	
	public static Action receiveAction(BTStreams connection){
		Pair src = receivePair(connection);
		Pair dest = receivePair(connection);
		
		if(src == null || dest == null){
			return null;
		}
		else
			return new Action(src,dest);
	}
	
	public static MessageType receiveMessageType(BTStreams connection){
		try{
			int message = connection.getDataInputStream().readInt();
			
			BTCommunicator.ack(connection);
			
			return MessageType.int2M(message);
			
		}catch(IOException ex){
		}
		
		return MessageType.UNDEFINED;
	}
	
	public static boolean sendMessageType(MessageType type, BTStreams connection){
		try{
			connection.getDataOutputStream().writeInt(type.toInt());
			connection.getDataOutputStream().flush();
			
			return receiveAck(connection);
		}catch(IOException e){
		}
		
		return false;
	}
	
	public static Node receiveNode(BTStreams connection){
		Pair id = receivePair(connection);
		
		if(id == null){
			return null;
		}
		
		Type type = Type.toType(receiveInt(connection));
		
		return new Node(type,id);
	}
	
	public static boolean sendNode(Node node,BTStreams connection){
		return sendPair(node.getID(), connection) && sendInt(node.getType().toInt(),connection);
	}
	
	public static boolean sendGraph(Graph graph,BTStreams connection){
		
		sendInt(graph.size(),connection);
		
		for(Node node: graph.getNodes()){
			if(!sendNode(node,connection))
				return false;
		}
		
		return true;
	}
	
	public static Graph receiveGraph(BTStreams connection){
		int numNodes = receiveInt(connection);
		
		Graph result = new Graph();
		
		for(int i=0;i< numNodes;i++){
			Node node = receiveNode(connection);
			
						
			if(node == null){
				return null;
			}
			
			result.addNode(node);
		}
		
		GraphTools.addConnections(result);
		
		return result;
	}
	
	public static boolean sendUpdate(Graph graph, BTStreams connection){
		List<Pair> boxes = graph.getBoxes();
		
		if(!sendInt(boxes.size(),connection)){
			return false;
		}
		
		for(Pair box:boxes){
			if(!sendPair(box,connection))
				return false;
		}
		
		if(!sendPair(graph.getPusher(),connection)){
			return false;
		}
		
		if(!sendPair(graph.getPuller(),connection)){
			return false;
		}
		
		return true;
	}
	
	public static Update receiveUpdate(BTStreams connection){
		List<Pair> boxes = new ArrayList<Pair>();
		
		int numBoxes = receiveInt(connection);
		
		for(int i =0;i <numBoxes;i++){
			Pair box = receivePair(connection);
			
			if(box == null){
				return null;
			}
			
			boxes.add(box);
		}
		
		Pair pusher = receivePair(connection);
		Pair puller = receivePair(connection);
		
		if(pusher == null || puller == null){
			return null;
		}
		
		return new Update(boxes,pusher,puller);
	}
	

}
