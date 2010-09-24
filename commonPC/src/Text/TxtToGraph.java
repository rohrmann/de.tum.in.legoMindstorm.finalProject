package Text;


import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

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


	public static Graph readGraph(BufferedReader input) throws IOException {
		
		String line = "";
		List<String> map = new ArrayList<String>();
		int maxX = 0;
		int maxY = 0;
		while (true){
			line = input.readLine();
			
			if(line == null || !line.startsWith("#"))
				break;
			
			maxY++;
			if (line.length() > maxX) {
				maxX = line.length();
			}
			map.add(line);
		}

		
		for (int y = maxY-1; y >= 0; y--) {
			line = map.get(maxY-1-y);
			for (int x = 0; x < line.length(); x++) {
				newNode(line.charAt(x), x, y);
			}
		}
		addConnections(graph);
				
		return graph;
	}
	
	public static void parseCommands(BufferedReader eingabe) throws IOException{
		String a = eingabe.readLine();
		while(a != null){
			if (a.startsWith("puller")){
				String b = (String) a.subSequence(8, a.length()-1);
				String[] c = b.split(",");
				int x = Integer.parseInt(c[0]);
				int y = Integer.parseInt(c[1]);
				
			}else if (a.startsWith("pusher")){
				String b = (String) a.subSequence(8, a.length()-1);
				String[] c = b.split(",");
				int x = Integer.parseInt(c[0]);
				int y = Integer.parseInt(c[1]);
				
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
