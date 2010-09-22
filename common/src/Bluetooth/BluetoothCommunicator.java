package Bluetooth;

import java.io.IOException;

import misc.Action;

import Graph.Graph;
import Graph.Pair;

public class BluetoothCommunicator {
	
	public static boolean done(BluetoothConnection connection){
		try {
			connection.getDataOutputStream().writeInt(MessageType.DONE.toInt());
			connection.getDataOutputStream().flush();
			return true;
		} catch (IOException e) {
		}
		
		return false;
	}
	
	public static boolean ack(BluetoothConnection connection){
		try{
			connection.getDataOutputStream().writeInt(MessageType.ACK.toInt());
			connection.getDataOutputStream().flush();
			return true;
		}catch(IOException e){
		}
		
		return false;
	}
	
	public static boolean sendMove(Pair pos,BluetoothConnection connection){
		return sendPair(pos,connection);
		
	}
	
	public static Pair receiveMove(BluetoothConnection connection){
		return receivePair(connection);
	}
	
	public static boolean sendPair(Pair pair, BluetoothConnection connection){
		try{
			connection.getDataOutputStream().writeInt(pair.getX());
			connection.getDataOutputStream().writeInt(pair.getY());
			connection.getDataOutputStream().flush();
			
			return true;
		}catch(IOException e){
			
		}
		
		return false;
	}
	
	public static Pair receivePair(BluetoothConnection connection){
		try{
			int x = connection.getDataInputStream().readInt();
			int y = connection.getDataInputStream().readInt();
			
			return new Pair(x,y);
		}catch(IOException e){
		}

		return null;
	}
	
	public static boolean sendAction(Action action, BluetoothConnection connection){
		return sendPair(action.getSrc(),connection) && sendPair(action.getDest(),connection);
	}
	
	public static Action receiveAction(BluetoothConnection connection){
		Pair src = receivePair(connection);
		Pair dest = receivePair(connection);
		
		if(src == null || dest == null){
			return null;
		}
		else
			return new Action(src,dest);
	}
	
	public static MessageType receiveMessageType(BluetoothConnection connection){
		try{
			int message = connection.getDataInputStream().readInt();
			
			BluetoothCommunicator.ack(connection);
			
			return MessageType.int2M(message);
			
		}catch(IOException ex){
		}
		
		return MessageType.UNDEFINED;
	}
	
	public static Graph receiveMap(BluetoothConnection connection){
		try{
			return BTReceiveAnything.receiveNodes(connection.getDataOutputStream(), connection.getDataInputStream());
		}catch(IOException e){	
		}
		
		return null;
	}
	

}
