package BluetoothBrick;
import lejos.nxt.*;
import lejos.nxt.comm.*;
import miscBrick.Helper;

import java.io.*;

import Bluetooth.BTSendNodes;
import Graph.*;


public class BTBrick {



	private static BTConnection btc;
	private static DataInputStream dis;
	private static DataOutputStream dos;
	private static Graph graph;

	private static String connected = "Connected";
	private static String waiting = "Waiting for Con...";
	private static String closing = "Closing...";

	
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
	
	public static DataInputStream getInput(){
		return dis;
	}
	
	public static DataOutputStream getOutput(){
		return dos;
	}
	
	public static void receiveTestNumber() throws IOException, InterruptedException{
		LCD.clearDisplay();
		Helper.drawInt((int)dis.read(),0,1);
		Sound.beepSequenceUp();
		dis.close();
		dos.close();
		btc.close();
		Thread.sleep(10000);
	}
	


	public static void closeConnectionAfterOK() throws IOException,
			InterruptedException {
		Helper.drawString("Trying to Close Con", 0, 4);
		if (dis.read() == 1) {
			Sound.beepSequenceUp();
			dis.close();
			Helper.drawString("Input Closed", 0, 5);
			dos.close();
			Helper.drawString("OutPut Closed", 0, 6);
			Thread.sleep(100); // wait for data to drain
			LCD.clear();
			LCD.drawString(closing, 0, 0);
			LCD.refresh();
			btc.close();
			LCD.clear();

		} else {
			BTSendNodes.sendNodes(graph, dos, dis, false);
		}
	}

	

}