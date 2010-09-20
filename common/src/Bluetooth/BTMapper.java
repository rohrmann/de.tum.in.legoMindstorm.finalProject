package Bluetooth;
import lejos.nxt.*;
import lejos.nxt.comm.*;
import misc.Helper;

import java.io.*;
import java.util.Enumeration;

import Graph.*;

public class BTMapper {

	

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