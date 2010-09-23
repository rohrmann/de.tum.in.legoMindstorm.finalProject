package Text;


import java.io.*;
import java.util.Enumeration;

import Graph.Node;
import Graph.Pair;
import Graph.Type;
import Graph.Graph;
import Memory.Move;

public class TxtToGraph {

	/**
	 * @param args
	 * @throws IOException
	 */
	private static Graph graph = new Graph();


	public static void readtxt(String source) throws IOException {
		FileReader datenstrom;
		datenstrom = new FileReader(source);
		BufferedReader eingabe = new BufferedReader(datenstrom);
		String a = eingabe.readLine();
		int maxX = 0;
		int maxY = 0;
		while (a != null && a.charAt(0) == '#'){
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
		for (int y = maxY; y >= 0; y--) {
			a = eingabe2.readLine();
			for (int x = 0; x < a.length(); x++) {
				System.out.print(a.charAt(x));
				newNode(a.charAt(x), x, y);
			}
			System.out.println();
		}
		addConnections(graph);
		Memory.Map.setMap(graph);
		parseCommands(eingabe);
	}
	
	public static void parseCommands(BufferedReader eingabe) throws IOException{
		String a = eingabe.readLine();
		while(a != null){
			if (a.startsWith("puller")){
				String b = (String) a.subSequence(8, a.length()-1);
				String[] c = b.split(",");
				int x = Integer.parseInt(c[0]);
				int y = Integer.parseInt(c[1]);
				Move.setMove(x, y);
				System.out.println(x);
				System.out.println(y);
			}else if (a.startsWith("pusher")){
				String b = (String) a.subSequence(8, a.length()-1);
				String[] c = b.split(",");
				int x = Integer.parseInt(c[0]);
				int y = Integer.parseInt(c[1]);
				System.out.println(x);
				System.out.println(y);
			}else if (a.startsWith("push")){
				String b = (String) a.subSequence(6, a.length()-1);
				String[] c = b.split(" ");
				String dest = c[0].substring(0, c[0].length()-1);
				String[] destArray = dest.split(",");
				int x = Integer.parseInt(destArray[0]);
				int y = Integer.parseInt(destArray[1]);
				
				
				String destbox = c[1].substring(1, c[1].length());
				String[] destboxArray = destbox.split(",");
				int xdest = Integer.parseInt(destboxArray[0]);
				int ydest = Integer.parseInt(destboxArray[1]);
				System.out.println(x);
				System.out.println(y);
				System.out.println(xdest);
				System.out.println(ydest);
			}else if (a.startsWith("pull")){
				String b = (String) a.subSequence(6, a.length()-1);
				String[] c = b.split(" ");
				String dest = c[0].substring(0, c[0].length()-1);
				String[] destArray = dest.split(",");
				int x = Integer.parseInt(destArray[0]);
				int y = Integer.parseInt(destArray[1]);
				
				
				String destbox = c[1].substring(1, c[1].length());
				String[] destboxArray = destbox.split(",");
				int xdest = Integer.parseInt(destboxArray[0]);
				int ydest = Integer.parseInt(destboxArray[1]);
				System.out.println(x);
				System.out.println(y);
				System.out.println(xdest);
				System.out.println(ydest);
			}
			else {
				
			}
			
			a = eingabe.readLine();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void addConnections(Graph graph){
		Enumeration<Pair> enumer = (Enumeration<Pair>)graph.getHashtable().keys();
		while(enumer.hasMoreElements()){
			Pair nextElement = enumer.nextElement();
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
