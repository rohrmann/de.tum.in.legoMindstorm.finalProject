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
	
	public List<Pair> getBoxes(){
		List<Node> nodes = getNodes();
		
		List<Pair> result = new ArrayList<Pair>();
		
		for(Node node : nodes){
			result.add(node.getID());
		}
		
		return result;
	}
	
	public Pair getPuller(){
		return find(Type.PULLSTART);
	}
	
	public Pair getPusher(){
		return find(Type.PUSHSTART);
	}
	
	public void setNode(Pair id, Type value){
		if(hasNode(id)){
			((Node)nodes.get(id)).setType(value);
		}
	}
	
	public void updateBox(Pair src, Pair dest){
		((Node)nodes.get(src)).setType(Type.EMPTY);
		((Node)nodes.get(dest)).setType(Type.BOX);
	}
	
	public void setPuller(Pair dest){
		if(find(Type.PULLSTART) != null){
			((Node)nodes.get(find(Type.PULLSTART))).setType(Type.EMPTY);
		}
		
		((Node)nodes.get(dest)).setType(Type.PULLSTART);
	}
	
	public void setPusher(Pair dest){
		if(find(Type.PUSHSTART) != null){
			((Node)nodes.get(find(Type.PUSHSTART))).setType(Type.EMPTY);
		}
		
		((Node)nodes.get(dest)).setType(Type.PUSHSTART);
	}
	
	public  String toString(){
		String result = "";
		
		for(Node node: getNodes()){
			result += node + "\n";
		}
		
		return result;
	}
}
