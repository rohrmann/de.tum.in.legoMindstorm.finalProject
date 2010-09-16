package Graph;

import Color.Color;
import misc.Direction;

public class Node {
	private Type type;
	private final Pair id;
	private Node north;
	private Node west;
	private Node south;
	private Node east;
	
	public Node(Type type, Pair id){
		this.type = type;
		this.id = id;
		north =null;
		west = null;
		south = null;
		east = null;
	}
	
	public Type getType(){
		return type;
	}
	
	public void setType(Type type){
		this.type = type;
	}
	
	public Pair getID(){
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
			this.north.setSimplexSouth(null);
		}
		
		this.north = north;
		
		if(north != null)
			north.setSimplexSouth(this);
	}
	
	private void setSimplexNorth(Node north){
		this.north = north;
	}
	
	public void setWest(Node west){
		if(this.west != null){
			this.west.setSimplexEast(null);
		}
		
		this.west = west;
		if(west != null){
			west.setSimplexEast(this);
		}
	}
	
	private void setSimplexWest(Node west){
		this.west = west;
	}
	
	public void setSouth(Node south){
		if(this.south != null){
			this.south.setSimplexNorth(null);
		}
		
		this.south = south;
		if(south!= null)
		south.setSimplexNorth(this);
	}
	
	private void setSimplexSouth(Node south){
		this.south = south;
	}
	
	public void setEast(Node east){
		if(this.east != null){
			this.east.setSimplexWest(null);
		}
		
		this.east =east;
		
		if(east != null){
			east.setSimplexWest(this);
		}
	}
	
	private void setSimplexEast(Node east){
		this.east = east;
	}
	
	public boolean hasNorth(){
		return north != null;
	}
	
	public boolean hasWest(){
		return west != null;
	}
	
	public boolean hasSouth(){
		return south != null;
	}
	
	public boolean hasEast(){
		return east != null;
	}
	
	public boolean has(Direction dir){
		switch(dir){
		case NORTH:
			return hasNorth();
		case SOUTH:
			return hasSouth();
		case WEST:
			return hasWest();
		default:
			return hasEast();
		}
	}
	
	public void set(Node node, Direction dir){
		switch(dir){
		case NORTH:
			setNorth(node);
			break;
		case SOUTH:
			setSouth(node);
			break;
		case WEST:
			setWest(node);
			break;
		case EAST:
			setEast(node);
			break;
		}
	}
	
	public Node get(Direction dir){
		switch(dir){
		case NORTH:
			return getNorth();
		case SOUTH:
			return getSouth();
		case WEST:
			return getWest();
		default:
			return getEast();
			
		}
	}

	public void setType(Color color){
		switch(color){
		case RED:
			type = Type.EMPTY;
			break;
		case YELLOW:
			type = Type.PULLSTART;
			break;
		case GREEN:
			type = Type.DEST;
			break;
		case BLUE:
			type = Type.BOX;
			break;
		}
	}
	
	@Override
	public String toString(){
		return "Node:"+id + " Type:"+type + (hasNorth()?"N":"") + (hasWest()?"W":"") + (hasSouth()?"S":"") + (hasEast()?"E":"");
	}
	
}
