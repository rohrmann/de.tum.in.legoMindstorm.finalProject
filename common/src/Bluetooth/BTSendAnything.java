package Bluetooth;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Enumeration;



import Graph.Graph;
import Graph.Node;
import Graph.Pair;
import Graph.Type;

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
	

}
