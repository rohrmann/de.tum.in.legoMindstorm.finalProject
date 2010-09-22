import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;


public class TestBTBrick {
	static Random rand;
	public static void main(String[] args) {
		
		LCD.drawString("Waiting for BT", 0, 0);
		BTConnection btc = Bluetooth.waitForConnection();
		
		DataInputStream dis = btc.openDataInputStream();
		DataOutputStream dos = btc.openDataOutputStream();
		
		while(true){
			try {
				LCD.drawInt(dis.readInt(), 0, 0);
			} catch (IOException e) {
			}
			
			try {
				dos.writeInt(rand.nextInt());
			} catch (IOException e) {
			}
			
			Button.waitForPress();
			
			try {
				dos.flush();
			} catch (IOException e) {
			}
		}
	}
	

}
