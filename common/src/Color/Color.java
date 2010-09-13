package Color;

public enum Color {
	BLACK("black"),
	WHITE("white"),
	//Empty
	RED("red"),
	//dest
	GREEN("green"),
	//pull
	YELLOW("yellow"),
	//box
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
