package Graph;

import misc.Direction;

public class Pair {
	private final int x;
	private final int y;
	
	public Pair(int x,int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	@Override
	public String toString(){
		return "("+x+","+y+")";
	}
	
	@Override
	public int hashCode(){
		return x*10 + y;
	}
	
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof Pair)){
			return false;
		}
		
		Pair p = (Pair)(obj);
		
		return p.getX() == getX() && p.getY() == getY();
	}
	
	public Pair getNeighbour(Direction dir){
		switch(dir){
		case NORTH:
			return new Pair(x,y+1);
		case SOUTH:
			return new Pair(x,y-1);
		case WEST:
			return new Pair(x-1,y);
		default:
			return new Pair(x+1,y);
		}
	}
	
	public static Pair parsePair(String str){
		String temp = str.substring(1, str.length()-1);
		int comma = temp.indexOf(',');
		
		if(comma == -1)
			return null;
		
		String n1 = temp.substring(0, comma);
		String n2 = temp.substring(comma+1,temp.length());
		
		return new Pair(Integer.parseInt(n1),Integer.parseInt(n2));
	}
	
}
