package Bluetooth;

public enum MessageType {
	MOVE(0),ACTION(1),TERMINATE(2),MAP(3),DONE(4),ACK(5);
	
	int id;
	
	MessageType(int id){
		this.id = id;
	}
	
	public int toInt(){
		return id;
	}
}
