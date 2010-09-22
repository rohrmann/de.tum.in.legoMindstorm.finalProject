package Bluetooth;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Enumeration;



import Graph.Graph;
import Graph.Node;
import Graph.Pair;
import Graph.Type;
import Memory.Update;

public class BTSendAnything {
	
	public static void sendMove(Pair move, DataOutputStream dos,
			DataInputStream dis) throws IOException{
		int control = (int) (Math.random() * 255);
		dos.writeInt(move.getX());
		dos.flush();
		dos.writeInt(move.getY());
		dos.flush();
		dos.writeInt(control);
		dos.flush();
		if (dis.read() == control) {
			System.out.println("Control OK");
		} else {
			System.out.println("Control FAIL");
		}
	}
	
	public static void sendNodes(Graph graph, DataOutputStream dos,
			DataInputStream dis) throws IOException,
			InterruptedException {
		dos.flush();
		
		dos.writeInt(graph.size());
		dos.flush();
		Enumeration enumeration = graph.getHashtable().keys();
		int control;
		while (enumeration.hasMoreElements()) {
			Pair pair = (Pair) enumeration.nextElement();
			Node node = graph.getNode(pair);
			int type = Type.typeToInt(node);
			control = (int) (Math.random() * 255);
			dos.writeInt(pair.getX());
			dos.flush();
			dos.writeInt(pair.getY());
			dos.flush();
			dos.writeInt(type);
			dos.flush();
			dos.writeInt(control);
			dos.flush();
			if (dis.read() == control) {
				System.out.println("Control OK");
			} else {
				System.out.println("Control FAIL");
			}
		}
	}
	
<<<<<<< HEAD
	public static void sendUpdate(DataOutputStream dataOut,
			DataInputStream dataIn) throws IOException {
		dos = dataOut;
		dis = dataIn;
		Pair[] x = Update.getBoxPositions();
		dos.writeInt(x.length);
		dos.flush();
		for(int i=0; i<x.length;i++){
			dos.writeInt(x[i].getX());
			dos.flush();
			dos.writeInt(x[i].getX());
			dos.flush();
		}
		dos.writeInt(Update.getPusher().getX());
		dos.flush();
		dos.writeInt(Update.getPusher().getY());
		dos.flush();
		
		dos.writeInt(Update.getPuller().getX());
		dos.flush();
		dos.writeInt(Update.getPuller().getY());
		dos.flush();
	}		
	

	public static void sendSize() throws IOException {
		dos.writeInt(GraphTools.getSize(graph));
		dos.flush();
	}

	public static int typeToInt(Node node) {
		Type a = node.getType();
		switch (a) {
		case EMPTY:
			return 0;
		case UNKNOWN:
			return 1;
		case PUSHSTART:
			return 3;
		case PULLSTART:
			return 2;
		case BOX:
			return 4;
		case DEST:
			return 5;
		case UNDEFINED:
			return 6;
		case PULLER:
			return 7;
		case PUSHER:
			return 8;
		}
		return 6;
	}
=======
>>>>>>> 353c2b03d4e058e93c069f402f265c58f89d3408

	

}
