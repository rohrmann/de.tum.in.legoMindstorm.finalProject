package Graph;

public enum Type {
EMPTY,UNKNOWN,PUSHSTART,PULLSTART,BOX,DEST,UNDEFINED;

	public boolean isAccessible(){
		if(this == BOX || this==UNDEFINED || this==PULLSTART || this ==PUSHSTART)
			return false;
		
		return true;
	}
	
	public int toInt() {
		switch(this){
		case EMPTY:
			return 0;
		case UNKNOWN:
			return 1;
		case PUSHSTART:
			return 2;
		case PULLSTART:
			return 3;
		case BOX:
			return 4;
		case DEST:
			return 5;
		default:
			return 6;
		}
	}
	
	
	public static Type toType(int i){
		switch(i){
		case 0:
			return EMPTY;
		case 1:
			return UNKNOWN;
		case 2:
			return PUSHSTART;
		case 3:
			return PULLSTART;
		case 4:
			return BOX;
		case 5:
			return DEST;
		default:
			return UNDEFINED;
		}
	}

}
