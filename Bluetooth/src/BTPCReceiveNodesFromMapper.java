import lejos.pc.comm.*;
import java.io.*;

import Graph.Graph;
import Graph.Node;
import Graph.Pair;
import Graph.Type;



public class BTPCReceiveNodesFromMapper {
	
	private static Graph graph = new Graph();
	
	private static DataInputStream dis;
	private static DataOutputStream dos;
	
	private static int numberOfNodes;
	

	
	
	
	public static void receiveNodesFromBrick() throws IOException{
		dis = BTConnections.getMapperIn();
		dos = BTConnections.getMapperOut();
		receiveSize();
		int[] arrayFromBrick = new int[4];
		while (numberOfNodes>0) {
			dis.read();
			System.out.println("muell: " + dis.read());
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

			System.out.println("ende while durchlauf");
			System.out.println("Daten empfangen: " + "    X: "
					+ arrayFromBrick[0] + "    Y: " + arrayFromBrick[1]
					+ "    Enum: " + arrayFromBrick[2]);

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
		TxtToGraph.addConnections(graph);
		GraphToTxt.writeTxt(graph, PCConfig.getTextFile());
		closeConnection();
	}
	
	public static void closeConnection() throws IOException{
		System.out.println("Closing Con ...");
		if(numberOfNodes!=0){
			System.out.println("Wrong Number");
			byte[] ok = new byte[1];
			ok[0]=0;
			dos.write(ok);
			dos.flush();
			receiveNodesFromBrick();
		}
		else{
			System.out.println("Right Number");
			byte[] ok = new byte[1];
			ok[0]=1;
			dos.write(ok);
			dos.flush();
			try {
				dis.close();
				System.out.println("InputStream geschlossen");
				dos.close();
				System.out.println("OutputStream geschlossen");
				//conn.close();
				System.out.println("Connection geschlossen");
			} catch (IOException ioe) {
				System.out.println("IOException closing connection:");
				System.out.println(ioe.getMessage());
			}
		}
	}
	
	public static void receiveSize() throws IOException{
		System.out.println(dis.read());
		System.out.println(dis.read());
		numberOfNodes = dis.read();
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
		}
		
	}
}