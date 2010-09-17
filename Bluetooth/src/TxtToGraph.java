

import java.io.*;
import java.util.Enumeration;

import Graph.Node;
import Graph.Pair;
import Graph.Type;
import Graph.Graph;

public class TxtToGraph {

	/**
	 * @param args
	 * @throws IOException
	 */
	private static Graph graph = new Graph();


	public static Graph readtxt(String source) throws IOException {
		FileReader datenstrom;
		datenstrom = new FileReader(source);
		BufferedReader eingabe = new BufferedReader(datenstrom);
		String a = eingabe.readLine();
		int maxX = 0;
		int maxY = 0;
		while (a != null) {
			maxY++;
			if (a.length() > maxX) {
				maxX = a.length();
			}
			a = eingabe.readLine();
		}
		eingabe.close();
		FileReader datenstrom2;
		datenstrom2 = new FileReader(source);
		BufferedReader eingabe2 = new BufferedReader(datenstrom2);
		System.out.println("maxLength: " + maxX);
		System.out.println("maxHeigth: " + maxY);
		for (int y = maxY - 1; y > 0; y--) {
			a = eingabe2.readLine();
			for (int x = 0; x < a.length(); x++) {
				System.out.print(a.charAt(x));
				newNode(a.charAt(x), x - 1, y - 1);
			}
			System.out.println();
		}
		addConnections(graph);
		return graph;
	}
	
	public static void addConnections(Graph graph){
		Enumeration enumer = graph.getHashtable().keys();
		while(enumer.hasMoreElements()){
			Pair nextElement = (Pair) enumer.nextElement();
			int x = nextElement.getX();
			int y = nextElement.getY();
			if(graph.hasNode(new Pair(x+1,y))){
				graph.getNode(new Pair(x,y)).setEast(graph.getNode(new Pair(x+1,y)));
			}
			if(graph.hasNode(new Pair(x,y+1))){
				graph.getNode(new Pair(x,y)).setNorth(graph.getNode(new Pair(x,y+1)));
			}
		}
	}

	public static void newNode(char a, int x, int y) {
		switch (a) {
		case'#': break;
		case ' ':
			graph.addNode(new Node(Type.EMPTY, new Pair(x, y)));
			break;
		case '@':
			graph.addNode(new Node(Type.PUSHSTART, new Pair(x, y)));
			break;
		case '!':
			graph.addNode(new Node(Type.PULLSTART, new Pair(x, y)));
			break;
		case '$':
			graph.addNode(new Node(Type.BOX, new Pair(x, y)));
			break;
		case '.':
			graph.addNode(new Node(Type.DEST, new Pair(x, y)));
			break;
		}
	}

}
