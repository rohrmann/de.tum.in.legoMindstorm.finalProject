package misc;

import Graph.Pair;

public enum Direction {
	WEST,NORTH,EAST,SOUTH,UNDEFINED;
	
	
	public Direction opposite(){
		switch(this){
		case NORTH:
			return SOUTH;
		case SOUTH:
			return NORTH;
		case EAST:
			return WEST;
		case WEST:
			return EAST;
		default:
			return UNDEFINED;
		}
	}
	
	public String toString(){
		switch(this){
		case NORTH:
			return "NORTH";
		case SOUTH:
			return "SOUTH";
		case EAST:
			return "EAST";
		case WEST:
			return "WEST";
		default:
			return "UNDEFINED";
		}
	}
	
	public static Direction findDirection(Pair from, Pair to){
		int x = to.getX() - from.getX();
		int y = to.getY() - from.getY();
		
		if(x == 0 && y >0){
			return Direction.NORTH;
		}
		else if(x ==0 && y < 0){
			return Direction.SOUTH;
		}
		else if(x >0 && y ==0){
			return Direction.EAST;
		}
		else if(x < 0  && y == 0){
			return Direction.WEST;
		}
		else
			return Direction.UNDEFINED;
	}
	
}
