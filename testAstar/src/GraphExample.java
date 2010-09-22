import Graph.Graph;
import Graph.Node;
import Graph.Pair;
import Graph.Type;


public class GraphExample {							// Karte bzw Koordinaten

	public static Graph getGraph(){
		/**
		 * 	D
		 * 	E
		 * 	E	E	B
		 * 	D	E	P	E	B	C
		 */
		
		Graph result = new Graph();
		
		Node node1 = new Node(Type.EMPTY,new Pair(0,0));
		Node node2 = new Node(Type.EMPTY,new Pair(0,1));
		Node node3 = new Node(Type.EMPTY,new Pair(0,2));
		Node node4 = new Node(Type.EMPTY,new Pair(0,3));
		Node node5 = new Node(Type.EMPTY,new Pair(0,4));
		Node node6 = new Node(Type.EMPTY,new Pair(1,0));
		Node node7 = new Node(Type.EMPTY,new Pair(1,2));
		Node node8 = new Node(Type.EMPTY,new Pair(2,0));
		Node node9 = new Node(Type.EMPTY,new Pair(2,2));
		Node node10 = new Node(Type.EMPTY,new Pair(2,3));
		Node node11= new Node(Type.EMPTY,new Pair(2,4));
		Node node12 = new Node(Type.EMPTY,new Pair(3,0));
		Node node13 = new Node(Type.BOX,new Pair(3,2));
		Node node14 = new Node(Type.EMPTY,new Pair(3,3));
		Node node15 = new Node(Type.BOX,new Pair(4,0));
		Node node16 = new Node(Type.EMPTY,new Pair(4,1));
		Node node17 = new Node(Type.EMPTY,new Pair(4,2));
		Node node18 = new Node(Type.EMPTY,new Pair(4,3));
		Node node19 = new Node(Type.EMPTY,new Pair(5,0));
		Node node20 = new Node(Type.EMPTY,new Pair(5,1));
		Node node21 = new Node(Type.BOX,new Pair(5,2));
		Node node22 = new Node(Type.EMPTY,new Pair(6,0));
		Node node23 = new Node(Type.EMPTY,new Pair(6,1));
		Node node24 = new Node(Type.EMPTY,new Pair(6,2));
		Node node25 = new Node(Type.EMPTY,new Pair(6,3));
		
		node1.setEast(node6);
		node1.setNorth(node2);
		node2.setNorth(node3);
		node3.setNorth(node4);
		node3.setEast(node7);
		node4.setNorth(node5);
		node6.setEast(node8);
		node7.setEast(node9);
		node8.setEast(node12);
		node9.setNorth(node10);
		node9.setEast(node13);
		node10.setEast(node14);
		node10.setNorth(node11);
		node12.setEast(node15);
		node13.setNorth(node14);
		node13.setEast(node17);
		node14.setEast(node18);
		node15.setNorth(node16);
		node15.setEast(node19);
		node16.setEast(node20);
		node16.setNorth(node17);
		node17.setEast(node21);
		node17.setNorth(node18);
		node19.setEast(node22);
		node19.setNorth(node20);
		node20.setEast(node23);
		node20.setNorth(node21);
		node21.setEast(node24);
		node22.setNorth(node23);
		node23.setNorth(node24);
		node24.setNorth(node25);
		

		
		result.addNode(node1);
		result.addNode(node2);
		result.addNode(node3);
		result.addNode(node4);
		result.addNode(node5);
		result.addNode(node6);
		result.addNode(node7);
		result.addNode(node8);	
		result.addNode(node9);
		result.addNode(node10);
		result.addNode(node11);
		result.addNode(node12);
		result.addNode(node13);
		result.addNode(node14);
		result.addNode(node15);
		result.addNode(node16);
		result.addNode(node17);
		result.addNode(node18);
		result.addNode(node19);
		result.addNode(node20);
		result.addNode(node21);
		result.addNode(node22);
		result.addNode(node23);
		result.addNode(node24);
		result.addNode(node25);
		return result;
		
		
	}
	
}
