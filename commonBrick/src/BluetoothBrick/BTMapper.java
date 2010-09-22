package BluetoothBrick;
import lejos.nxt.*;
import lejos.nxt.comm.*;

import java.io.*;


public class BTMapper {

	

	private static BTConnection btc;
	private static DataInputStream dis;
	private static DataOutputStream dos;

	private static String connected = "Connected";
	private static String waiting = "Waiting for Con...";

	
	public static void connectToPC() {

		LCD.drawString(waiting, 0, 0);
		LCD.refresh();

		btc = Bluetooth.waitForConnection();

		LCD.clear();
		LCD.drawString(connected, 0, 0);
		LCD.refresh();

		dis = btc.openDataInputStream();
		dos = btc.openDataOutputStream();
	}
	
	public static void closeCon(){
		btc.close();
	}

	public static DataInputStream getMapperInput(){
		return dis;
	}
	
	public static DataOutputStream getMapperOutput(){
		return dos;
	}

}