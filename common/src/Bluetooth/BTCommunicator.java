package Bluetooth;

import java.io.IOException;

import misc.Action;
import misc.RobotType;

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
			System.out.println("BTCommunicator.sendInt IOException");
		}
		
		return false;
	}
	
	public static int recvInt(BTStreams connection){
		try{
			int result = connection.getDataInputStream().readInt();
			return result;
		}catch(IOException e){
			System.out.println("BTCommunicator.receiveInt IOException");
		}
		
		return -1;
	}
	
	public static boolean done(BTStreams connection){
		try {
			connection.getDataOutputStream().writeInt(MessageType.DONE.toInt());
			connection.getDataOutputStream().flush();
			return true;
		} catch (IOException e) {
			System.out.println("BTCommunicator.done IOException");
		}
		
		return false;
	}
	
	public static boolean recvDone(BTStreams connection){
		try{
			int result = connection.getDataInputStream().readInt();
			
			if(MessageType.int2M(result) != MessageType.DONE){
				return false;
			}
			
			return true;
		}catch(IOException e){
			System.out.println("BTCommunicator.recvDone IOException");
		}
		
		return false;
	}
	
	public static boolean sendMove(Pair pos,BTStreams connection){
		return sendPair(pos,connection);
		
	}
	
	public static Pair recvMove(BTStreams connection){
		return recvPair(connection);
	}
	
	public static boolean sendPair(Pair pair, BTStreams connection){
		try{
			connection.getDataOutputStream().writeInt(pair.getX());
			connection.getDataOutputStream().writeInt(pair.getY());
			connection.getDataOutputStream().flush();
			
			return true;
		}catch(IOException e){
			System.out.println("BTCommunicator.sendPair IOException");
		}
		
		return false;
	}
	
	public static Pair recvPair(BTStreams connection){
		try{
			int x = connection.getDataInputStream().readInt();
			int y = connection.getDataInputStream().readInt();
			
			return new Pair(x,y);
		}catch(IOException e){
			System.out.println("BTCommunicator.recvPair IOException");
		}

		return null;
	}
	
	public static boolean sendAction(Action action, BTStreams connection){
		return sendPair(action.getSrc(),connection) && sendPair(action.getDest(),connection);
	}
	
	public static Action recvAction(BTStreams connection){
		Pair src = recvPair(connection);
		Pair dest = recvPair(connection);
		
		if(src == null || dest == null){
			return null;
		}
		else
			return new Action(src,dest);
	}
	
	public static RobotType recvRobotType(BTStreams connection){
		try{
			int robotType = connection.getDataInputStream().readInt();
			
			return RobotType.int2RobotType(robotType);
			
		}catch(IOException ex){
			System.out.println("BTCommunicator.recvRobotType IOException");
		}
		
		return RobotType.UNDEFINED;
	}
	
	public static boolean sendRobotType(RobotType type, BTStreams connection){
		try{
			connection.getDataOutputStream().writeInt(type.toInt());
			connection.getDataOutputStream().flush();
			
			return true;
		}catch(IOException e){
			System.out.println("BTCommunicator.sendRobotType IOException");
		}
		
		return false;
	}
	
	public static MessageType recvMessageType(BTStreams connection){
		try{
			int message = connection.getDataInputStream().readInt();
					
			return MessageType.int2M(message);
			
		}catch(IOException ex){
			System.out.println("BTCommunicator.recvMessageType");
		}
		
		return MessageType.UNDEFINED;
	}
	
	public static boolean sendMessageType(MessageType type, BTStreams connection){
		try{
			connection.getDataOutputStream().writeInt(type.toInt());
			connection.getDataOutputStream().flush();
						
			return true;
		}catch(IOException e){
			System.out.println("BTCommunicator.sendMessageType IOException");
		}
		
		return false;
	}
	
	public static Node receiveNode(BTStreams connection){
		Pair id = recvPair(connection);
		
		if(id == null){
			return null;
		}
		
		Type type = Type.toType(recvInt(connection));
		
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
	
	public static Graph recvGraph(BTStreams connection){
		int numNodes = recvInt(connection);
		
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
}
