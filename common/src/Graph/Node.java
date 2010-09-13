package Graph;

public class Node {
	private TYPE type;
	private final Pair<Integer,Integer> id;
	private Node north;
	private Node west;
	private Node south;
	private Node east;
	
	public Node(TYPE type, Pair<Integer,Integer> id){
		this.type = type;
		this.id = id;
		north =null;
		west = null;
		south = null;
		east = null;
	}
	
	public TYPE getType(){
		return type;
	}
	
	public void setType(TYPE type){
		this.type = type;
	}
	
	public Pair<Integer,Integer> getID(){
		return id;
	}
	
	public Node getNorth(){
		return north;
	}
	
	public Node getWest(){
		return west;
	}
	
	public Node getSouth(){
		return south;
	}
	
	public Node getEast(){
		return east;
	}
	
	public void setNorth(Node north){
		/**
		 * Check whether one has to delete an existing connection
		 */
		if(this.north != null){
			Node temp = this.north;
			this.north = null;
			temp.setSouth(null);
		}
		
		this.north = north;
		
		if(north != null)
			north.setSouth(this);
	}
	
	public void setWest(Node west){
		if(this.west != null){
			Node temp = this.west;
			this.west = null;
			temp.setEast(null);
		}
		
		this.west = west;
		if(west != null){
			west.setEast(this);
		}
	}
	
	public void setSouth(Node south){
		if(this.south != null){
			Node temp = this.south;
			this.south = null;
			temp.setNorth(null);
		}
		
		this.south = south;
		if(south!= null)
			south.setNorth(this);
	}
	
	public void setEast(Node east){
		if(this.east != null){
			Node temp = this.east;
			this.east = null;
			temp.setWest(null);
		}
		
		this.east =east;
		
		if(east != null){
			east.setWest(this);
		}
	}

}
