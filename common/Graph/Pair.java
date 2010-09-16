package Graph;

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
	
}
