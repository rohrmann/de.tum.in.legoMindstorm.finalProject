package Bluetooth;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class BTSendCommand {
	public static void sendCommand(int[] commandArray1,DataOutputStream dataOut,
			DataInputStream dataIn) throws IOException{
		DataOutputStream dos = dataOut;
		DataInputStream dis = dataIn;
		int[]commandArray = commandArray1;
		byte[] send = new byte[10];
		send[0]= (byte) commandArray[0];
		for(int i=0;i<commandArray.length-1;i++)
		{
			if(commandArray[i+1]>=0){
				send[2*i+1]=(byte)commandArray[i+1];
				send[2*i+2]=(byte) 0;
			}
			else{
				send[2*i+1]=(byte)-commandArray[i+1];
				send[2*i+2]=(byte) 1;
			}
		}
		int control = (int) (Math.random() * 255);
		send[9] = (byte) control;
		dos.write(send);
		dos.flush();
		dis.read();
		dis.read();
		if (dis.read() == control) {
			System.out.println("Command sent");
		} else {
			System.err.println("Command sent FAIL");
		}
	}

}
 