package Memory;

import Graph.Graph;

public class Map {
	
	private static Graph map;
	
	public static Graph getMap(){
		return map;
	}
	
	public static void setMap(Graph graph){
		map = graph;
	}
	
}
