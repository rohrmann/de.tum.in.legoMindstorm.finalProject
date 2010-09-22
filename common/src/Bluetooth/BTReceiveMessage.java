package Bluetooth;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;



public class BTReceiveMessage {
	public static MessageType receiveType(int type){
		switch(type){
		case 1:
			return MessageType.MOVE;
		case 2:
			return MessageType.ACTION;
		case 3:
			return MessageType.MAP;
		}
		return null;
	}
	
	public static void receiveMessage(DataOutputStream dataOut,
			DataInputStream dataIn) throws IOException, InterruptedException{
			MessageType type = BTReceiveMessage.receiveType(dataIn.read());
			switch(type){
			case MOVE:
				BTReceiveAnything.receiveMove(dataOut, dataIn);
			case ACTION:
				BTReceiveAnything.receiveAction(dataOut, dataIn);
			case MAP:
				BTReceiveAnything.receiveNodes(dataOut, dataIn);
			}
		}
}
