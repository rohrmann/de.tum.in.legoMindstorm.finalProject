import java.io.IOException;

import Graph.*;
//import Text.TxtToGraph;


public class TestTxt {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Graph graph = TxtToGraph.readtxt("C:/Users/Public/Documents/test2.txt");
		
		//Graph graph = BuildGraph.getGraph1();
		System.out.println(graph.hasNode(new Pair(1,2)));
		System.out.println(graph.getNode(new Pair(1,2)).hasWest());
		System.out.println(graph.getNode(new Pair(0,5)).hasNorth());
	   //GraphToTxt.writeTxt(graph,"C:/Users/Public/Documents/test2.txt");
	}
}
