import lejos.pc.comm.*;
import java.io.*;

import Graph.Graph;
import Graph.Node;
import Graph.Pair;
import Graph.Type;



public class TestBTPC4 {
	private static Graph graph = new Graph();
	public static void main(String[] args) throws IOException {
		NXTConnector conn = new NXTConnector();
		boolean connected = conn.connectTo("s_brick");
		
		if (!connected) {
			System.err.println("Failed to connect to any NXT");
			System.exit(1);
		}

		DataOutputStream dos = conn.getDataOut();
		DataInputStream dis = conn.getDataIn();

		int[] arrayFromBrick = new int[4];
		while (dis.read() > -1) {
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
		}
		try {
			dis.close();
			System.out.println("InputStream geschlossen");
			dos.close();
			System.out.println("OutputStream geschlossen");
			conn.close();
			System.out.println("Connection geschlossen");
		} catch (IOException ioe) {
			System.out.println("IOException closing connection:");
			System.out.println(ioe.getMessage());
		}
		TxtToGraph.addConnections(graph);
		GraphToTxt.writeTxt(graph, "C:/Users/Public/Documents/blub.txt");
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