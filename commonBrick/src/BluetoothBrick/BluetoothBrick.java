package BluetoothBrick;
import lejos.nxt.*;
import lejos.nxt.comm.*;
import miscBrick.Helper;

import java.io.*;
import java.util.Enumeration;

import Graph.*;

/**
 * 
 * @author rohrmann
 *
 */
public class BluetoothBrick {

	private static int arrayLength = 6;

	public static int getArrayLength() {
		return arrayLength;
	}

	private static BTConnection btc;
	private static DataInputStream dis;
	private static DataOutputStream dos;
	private static Graph graph;

	private static String connected = "Connected";
	private static String waiting = "Waiting for Con...";
	private static String closing = "Closing...";

	
	
	public static void sendGraph(Graph graph1) throws IOException, InterruptedException{
		graph = graph1;
		//connectToPC();
		sendNodesToPC(graph);
	}
	
	public static void sendSize() throws IOException{
		byte[] numberOfNodes = new byte[1];
		numberOfNodes[0]= (byte) GraphTools.getSize(graph);
		dos.write(numberOfNodes);
		dos.flush();
	}

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
			sendNodesToPC(graph);
		}
	}

	public static void sendNodesToPC(Graph graph) throws IOException,
			InterruptedException {
		sendSize();
		Enumeration enumeration = graph.getHashtable().keys();
		byte[] send = new byte[arrayLength];
		int control;
		while (enumeration.hasMoreElements()) {
			Pair pair = (Pair) enumeration.nextElement();
			Node node = graph.getNode(pair);
			int type = typeToInt(node);
			control = (int) (Math.random() * 255);
			send[4] = (byte) type;
			if (pair.getX() < 0) {
				send[1] = (byte) 1;
				send[0] = (byte) -pair.getX();
			} else {
				send[1] = (byte) 0;
				send[0] = (byte) pair.getX();
			}
			if (pair.getY() < 0) {
				send[3] = (byte) 1;
				send[2] = (byte) -pair.getY();
			} else {
				send[3] = (byte) 0;
				send[2] = (byte) pair.getY();

			}
			send[5] = (byte) control;
			dos.write(send);
			dos.flush();
			Helper.drawString("Data send", 0, 1);
			Helper.drawString("Waiting for Data", 0, 2);
			Helper.drawString("Data received", 0, 3);
			if (dis.read() == control) {
				Sound.beep();
			} else {
				Sound.buzz();
			}
			//Thread.sleep(200);
			LCD.clearDisplay();
		}
		closeConnectionAfterOK();
	}

	public static int typeToInt(Node node) {
		Type a = node.getType();
		switch (a) {
		case EMPTY:
			return 0;
		case UNKNOWN:
			return 1;
		case PUSHSTART:
			return 3;
		case PULLSTART:
			return 2;
		case BOX:
			return 4;
		case DEST:
			return 5;
		case UNDEFINED:
			return 6;
		}
		return 6;
	}

}