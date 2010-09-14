package Graph;
import java.util.Enumeration;
import java.util.Hashtable;


public class Graph {
	private Hashtable nodes;
	
	public Graph(){
		nodes = new Hashtable();
	}
	
	public int size(){
		int i =0;
		Enumeration enumeration = nodes.keys();
		while(enumeration.hasMoreElements()){
			i++;
			enumeration.nextElement();
		}
		return i;
	}
	
	public void addNode(Node node){
		nodes.put(node.getID(), node);
	}
	
	public Node getNode(Pair id){
		return(Node)nodes.get(id);
	}
	
	public Node getRoom(int x, int y){
		return (Node)nodes.get(new Pair(x,y));
	}
	
	public boolean hasNode(Pair id){
		return nodes.get(id)!= null? true : false;
	}
	
	public boolean hasNode(int x, int y){
		return hasNode(new Pair(x,y));
	}
	
	public Type getType(Pair position){
		Node node = getNode(position);
		
		if(node!= null){
			return node.getType();
		}
		
		return Type.UNDEFINED;
	}
	
	public Hashtable getHashtable(){
		return nodes;
	}
}
