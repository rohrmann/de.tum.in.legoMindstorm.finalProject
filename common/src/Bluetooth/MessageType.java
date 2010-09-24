package Bluetooth;

public enum MessageType {

	MOVE(0),ACTION(1),TERMINATE(2),MAP(3),DONE(4),FINISH(5),UPDATE(6),UNDEFINED(7);
	
	int id;
	
	MessageType(int id){
		this.id = id;
	}
	
	public int toInt(){
		return id;
	}
	
	public static MessageType int2M(int i){
		switch(i){
		case 0:
			return MOVE;
		case 1:
			return ACTION;
		case 2:
			return TERMINATE;
		case 3:
			return MAP;
		case 4:
			return DONE;
		case 5:
			return FINISH;
		case 6:
			return UPDATE;
		default:
			return UNDEFINED;
		}
	}
}
