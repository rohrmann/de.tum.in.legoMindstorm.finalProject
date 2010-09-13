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
		
		Node node1 = new Node(TYPE.DEST,new Pair<Integer,Integer>(3,0));
		Node node2 = new Node(TYPE.EMPTY,new Pair<Integer,Integer>(2,0));
		Node node3 = new Node(TYPE.EMPTY,new Pair<Integer,Integer>(1,0));
		Node node4 = new Node(TYPE.DEST,new Pair<Integer,Integer>(0,0));
		Node node5 = new Node(TYPE.EMPTY,new Pair<Integer,Integer>(1,1));
		Node node6 = new Node(TYPE.EMPTY,new Pair<Integer,Integer>(0,1));
		Node node7 = new Node(TYPE.BOX,new Pair<Integer,Integer>(1,2));
		Node node8 = new Node(TYPE.PULLSTART,new Pair<Integer,Integer>(0,2));
		Node node9 = new Node(TYPE.EMPTY,new Pair<Integer,Integer>(0,3));
		Node node10 = new Node(TYPE.BOX,new Pair<Integer,Integer>(0,4));
		Node node11 = new Node(TYPE.PUSHSTART,new Pair<Integer,Integer>(0,5));
		
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
		
		return result;
		
		
	}

}
