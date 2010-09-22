package Graph;

public enum Type {
EMPTY, UNKNOWN,PUSHSTART,PULLSTART,BOX,DEST,UNDEFINED;

	public boolean isAccessible(){
		if(this == BOX || this==UNDEFINED || this==PULLSTART || this ==PUSHSTART)
			return false;
		
		return true;
	}
	
	public static int typeToInt(Node node) {
		Type a = node.getType();
		switch (a) {
		case EMPTY:
			return 0;
		case UNKNOWN:
			return 1;
		case PUSHSTART:
			return 3;
		case PULLSTART:
			return 2;
		case BOX:
			return 4;
		case DEST:
			return 5;
		case UNDEFINED:
			return 6;
		}
		return 6;
	}

}
