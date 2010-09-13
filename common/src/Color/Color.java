package Color;

public enum Color {
	BLACK("black"),
	WHITE("white"),
	RED("red"),
	GREEN("green"),
	YELLOW("yellow"),
	BLUE("blue"),
	UNKNOWN("unknown");
	
	private String name;
	
	Color(String name){
		this.name = name;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	public boolean isRoomColor(){
		if(this != BLACK && this != WHITE && this != UNKNOWN){
			return true;
		}
		
		return false;
	}
}
