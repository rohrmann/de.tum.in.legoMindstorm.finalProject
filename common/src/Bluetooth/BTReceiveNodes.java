package Bluetooth;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import Graph.Graph;
import Graph.GraphTools;
import Graph.Node;
import Graph.Pair;
import Graph.Type;


public class BTReceiveNodes {
	
	private static int numberOfNodes;
	
	private static boolean toBrick;
	
	private static DataInputStream dis;
	private static DataOutputStream dos;
	
	private static Graph graph;
	
	public static Graph getGraph(){
		return graph;
	}
	
	public static Graph receiveNodes(DataOutputStream dataOut,
			DataInputStream dataIn, boolean toBrick1) throws IOException{
		dis = dataIn;
		dos = dataOut;
		toBrick = toBrick1;
		receiveSize();
		int[] arrayFromBrick = new int[4];
		while (numberOfNodes>0) {
			if(!toBrick){
				dis.read();
				dis.read();
			}
			arrayFromBrick[0] = dis.read();
			if (dis.read() == 1) {
				arrayFromBrick[0] = -1 * arrayFromBrick[0];
			}
			arrayFromBrick[1] = dis.read();
			if (dis.read() == 1) {
				arrayFromBrick[1] = -1 * arrayFromBrick[1];
			}
			arrayFromBrick[2] = dis.read();
			arrayFromBrick[3] = dis.read();

//			System.out.println("ende while durchlauf");
//			System.out.println("Daten empfangen: " + "    X: "
//					+ arrayFromBrick[0] + "    Y: " + arrayFromBrick[1]
//					+ "    Enum: " + arrayFromBrick[2]);

			byte[] check = new byte[1];
			check[0] = (byte) arrayFromBrick[3];
			dos.write(check);
			dos.flush();
			System.out.println("geflusht");
			makeNode(arrayFromBrick[2], arrayFromBrick[0], arrayFromBrick[1]);
			numberOfNodes--;
			System.out.println("Num: " + numberOfNodes);
		}
		System.out.println("Checking Nodes");
		GraphTools.addConnections(graph);
		if(!toBrick){
		//GraphToTxt.writeTxt(graph, PCConfig.getTextFile());
		//closeConnection();
		}
		return graph;
	}
	
public static void makeNode(int a, int x, int y) {
		
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
		if(!toBrick){
			dis.read();
			dis.read();
		}
		numberOfNodes = dis.read();
		}
	
}
