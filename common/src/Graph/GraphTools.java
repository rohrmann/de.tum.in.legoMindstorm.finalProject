package Graph;

import java.util.Enumeration;

public class GraphTools {

	public static int getSize(Graph graph) {
		Enumeration enumeration = graph.getHashtable().keys();
		int size = 0;
		while (enumeration.hasMoreElements()) {
			enumeration.nextElement();
			size++;
		}
		return size;
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

	public static void resetNodes(Graph graph) {
		Enumeration enumer = graph.getHashtable().keys();
		while(enumer.hasMoreElements()){
			Pair nextElement = (Pair) enumer.nextElement();
			graph.getNode(nextElement).setType(Type.EMPTY);
		}
	}
	
}