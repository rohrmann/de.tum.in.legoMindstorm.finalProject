package misc;

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
	
}
