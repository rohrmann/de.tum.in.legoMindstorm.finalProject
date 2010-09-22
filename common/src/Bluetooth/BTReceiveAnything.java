package Bluetooth;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import Graph.Graph;
import Graph.GraphTools;
import Graph.Node;
import Graph.Pair;
import Graph.Type;


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
		}
		
	}
	
	public static void receiveSize() throws IOException{
		numberOfNodes = dis.readInt();
	}

	public static Graph receiveNodes(DataOutputStream dataOut,
			DataInputStream dataIn) throws IOException {
		
		Graph graph = new Graph();
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
		
		return graph;
		
	}


	public static void receiveUpdate(DataOutputStream dataOut,
			DataInputStream dataIn) throws IOException {
		Graph graph = Memory.Map.getMap();
		GraphTools.resetNodes(graph);
		dis = dataIn;
		dos = dataOut;
		int numberOfBoxes;
		numberOfBoxes = dis.readInt();
		for(int i=0; i<numberOfBoxes; i++){
			graph.getNode(new Pair(dis.readInt(),dis.readInt())).setType(Type.BOX);
		}
		
		graph.getNode(new Pair(dis.readInt(),dis.readInt())).setType(Type.PUSHER);
		graph.getNode(new Pair(dis.readInt(),dis.readInt())).setType(Type.PULLER);
		Memory.Map.setMap(graph);
	}
	
}
