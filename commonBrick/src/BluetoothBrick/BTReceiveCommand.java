package BluetoothBrick;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.Sound;
import miscBrick.Helper;

public class BTReceiveCommand {
	
	private static int[] arrayFromPC = new int[5];
	
	public static int[] receiveCommand(DataOutputStream dataOut,
			DataInputStream dataIn) throws IOException, InterruptedException{
		arrayFromPC[0] = dataIn.read();
		for(int i=1;i<5;i++){
			arrayFromPC[i] = dataIn.read();
			if(dataIn.read() == 1){
				arrayFromPC[i] = -arrayFromPC[i];
			}
		}
		int control = dataIn.read();
		byte[]send = new byte[1];
		send[0] = (byte) control;
		dataOut.write(send);
		dataOut.flush();
		Sound.beepSequenceUp();
		for(int i=0;i<5;i++){
			Helper.drawInt(arrayFromPC[i], 0, i);
		}
		Thread.sleep(5000);
		return arrayFromPC;
	}
}
