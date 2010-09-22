package Bluetooth;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import Graph.Graph;
import Graph.GraphTools;
import Graph.Node;
import Graph.Pair;
import Graph.Type;
import Memory.Action;
import Memory.Map;
import Memory.Move;


public class BTReceiveAnything {
	
	private static int numberOfNodes;
	
	private static DataInputStream dis;
	private static DataOutputStream dos;
	
	private static Graph graph;
	
	public static Graph getGraph(){
		return graph;
	}
	
	
public static void makeNode(int x, int y, int a) {
		
		switch (a) {
		case 0:
			graph.addNode(new Node(Type.EMPTY, new Pair(x, y)));
			break;
		case 1:
			graph.addNode(new Node(Type.UNKNOWN, new Pair(x, y)));
			break;
		case 2:
			graph.addNode(new Node(Type.PULLSTART, new Pair(x, y)));
			break;
		case 3:
			graph.addNode(new Node(Type.PUSHSTART, new Pair(x, y)));
			break;
		case 4:
			graph.addNode(new Node(Type.BOX, new Pair(x, y)));
			break;
		case 5:
			graph.addNode(new Node(Type.DEST, new Pair(x, y)));
			break;
		case 6:
			graph.addNode(new Node(Type.UNDEFINED, new Pair(x, y)));
			break;
		case 7:
			graph.addNode(new Node(Type.PULLER, new Pair(x, y)));
			break;
		case 8:
			graph.addNode(new Node(Type.PUSHER, new Pair(x, y)));
			break;
		}
		
	}
	
	public static void receiveSize() throws IOException{
		numberOfNodes = dis.readInt();
	}

	public static void receiveMove(DataOutputStream dataOut,
			DataInputStream dataIn) throws IOException {
		Move.setMove(dis.readInt(), dis.readInt());
		dos.write(dis.readInt());
		dos.flush();
		
	}

	public static void receiveAction(DataOutputStream dataOut,
			DataInputStream dataIn) throws IOException {
		Action.setAction(new Pair(dis.readInt(), dis.readInt()), new Pair(dis.readInt(), dis.readInt()));
		dos.write(dis.readInt());
		dos.flush();
	}

	public static void receiveNodes(DataOutputStream dataOut,
			DataInputStream dataIn) throws IOException {
		dis = dataIn;
		dos = dataOut;
		receiveSize();
		while (numberOfNodes>0) {
			makeNode(dis.readInt(), dis.readInt(), dis.readInt());
		
			dos.write(dis.readInt());
			dos.flush();
			
			numberOfNodes--;
		}
		GraphTools.addConnections(graph);
		
		Map.setMap(graph);
		
	}
	
}
