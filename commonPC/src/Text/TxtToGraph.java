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
				String b = (String) a.subSequence(7, a.length()-1);
				System.out.println(b);
			}else if (a.startsWith("pusher")){
				String b = (String) a.subSequence(7, a.length()-1);
				System.out.println(b);
			}else if (a.startsWith("push")){
				String b = (String) a.subSequence(5, a.length()-1);
				System.out.println(b);
			}else if (a.startsWith("pull")){
				
			}else {
				
			}
			
			String[] b = a.split("(");
			
			if(b[0].equalsIgnoreCase("puller")){
				String[] c = b[1].split(",");
				for(int i=0; i<c.length;i++){
					System.out.println(c[i]);
				}
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
