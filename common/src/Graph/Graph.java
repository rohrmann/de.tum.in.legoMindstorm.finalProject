package Graph;
import java.util.Hashtable;


public class Graph {
	private Hashtable nodes;
	
	public Graph(){
		nodes = new Hashtable();
	}
	
	public void addNode(Node node){
		nodes.put(node.getID(), node);
	}
	
	public Node getNode(Pair<Integer,Integer> id){
		return(Node)nodes.get(id);
	}
	
	public Node getRoom(int x, int y){
		return (Node)nodes.get(new Pair<Integer,Integer>(x,y));
	}
}
