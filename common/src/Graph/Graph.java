package Graph;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;


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
	
	public Pair find(Type field){
		Enumeration keys = nodes.keys();
		
		while(keys.hasMoreElements()){
			Pair key = (Pair)keys.nextElement();
			
			Node node = (Node)nodes.get(key);
			
			if(node.getType()==field){
				return key;
			}
		}
		
		return null;
	}
	
	public List<Node> getNodes(){
		List<Node> result = new ArrayList<Node>();
		
		Enumeration keys = nodes.keys();
		
		while(keys.hasMoreElements()){
			Pair key = (Pair)keys.nextElement();
			
			Node node = (Node)nodes.get(key);
			
			result.add(node);
		}
		
		return result;
	}
}
