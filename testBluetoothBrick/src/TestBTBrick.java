import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.Button;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import misc.GraphExample;
import Bluetooth.BTCommunicator;
import BluetoothBrick.BTConnectionBrick;
import Graph.Graph;


public class TestBTBrick {
	public static void main(String[] args) {
		boolean cont = true;
		int connections =0;
		
		while(cont){
			System.out.println("waiting for connection");
			
			BTConnection conn = Bluetooth.waitForConnection();
			
			DataOutputStream dos = conn.openDataOutputStream();
			DataInputStream dis = conn.openDataInputStream();
			
			System.out.println("Connection established");
			
			try {
				dos.writeInt(connections++);
				dos.flush();
			} catch (IOException e1) {
			}
			
			
			System.out.println("close connection");
			
			try {
				dos.close();
				dis.close();
			} catch (IOException e) {
			}
			
			conn.close();
		}
		
	}
	

}
