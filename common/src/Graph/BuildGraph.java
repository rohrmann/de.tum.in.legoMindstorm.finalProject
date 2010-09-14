package Graph;

public class BuildGraph {
	
	public static Graph getGraph1(){
		/**
		 * 	D
		 * 	E
		 * 	E	E	B
		 * 	D	E	P	E	B	C
		 */
		
		Graph result = new Graph();
		
		Node node1 = new Node(Type.DEST,new Pair(3,0));
		Node node2 = new Node(Type.EMPTY,new Pair(2,0));
		Node node3 = new Node(Type.EMPTY,new Pair(1,0));
		Node node4 = new Node(Type.DEST,new Pair(0,0));
		Node node5 = new Node(Type.EMPTY,new Pair(1,1));
		Node node6 = new Node(Type.EMPTY,new Pair(0,1));
		Node node7 = new Node(Type.BOX,new Pair(1,2));
		Node node8 = new Node(Type.PULLSTART,new Pair(0,2));
		Node node9 = new Node(Type.EMPTY,new Pair(0,3));
		Node node10 = new Node(Type.BOX,new Pair(0,4));
		Node node11 = new Node(Type.PUSHSTART,new Pair(0,5));
		
		node1.setSouth(node2);
		node2.setSouth(node3);
		node3.setSouth(node4);
		node3.setEast(node5);
		node4.setEast(node6);
		node5.setSouth(node6);
		node5.setEast(node7);
		node6.setEast(node8);
		node7.setSouth(node8);
		node8.setEast(node9);
		node9.setEast(node10);
		node11.setEast(node11);
		
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
		return result;
		
		
	}

}
