package misc;

import Color.Color;

public class RoomInformation {
	private Color color;
	private boolean roomFound;
	
	public RoomInformation(){
		clear();
	}
	
	public synchronized void clear(){
		color = Color.UNKNOWN;
		roomFound = false;
	}
	
	public synchronized Color getRoomColor(){
		return color;
	}
	
	public synchronized void setRoomColor(Color color){
		this.color = color;
		roomFound = true;
	}
	
	public synchronized void setRoomFound(){
		roomFound = true;
	}
	
	public synchronized boolean roomFound(){
		return roomFound;
	}
	
	

}
