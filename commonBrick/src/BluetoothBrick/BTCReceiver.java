package BluetoothBrick;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

import Bluetooth.BTStreams;

public class BTCReceiver implements BTStreams {
	
	private BTConnection connection;
	private DataOutputStream dos;
	private DataInputStream dis;
	private Random rand;
	
	public BTCReceiver(){
		connection = null;
		dos = null;
		dis = null;
		
		openConnection();
	}
	
	public void openConnection(){
		System.out.println("Waiting for connection");
		while((connection=Bluetooth.waitForConnection())==null){
			try {
				Thread.sleep(100 + rand.nextInt(250));
			} catch (InterruptedException e) {
			}
		}
		
		System.out.println("Connection established");
		
		dos = connection.openDataOutputStream();
		dis = connection.openDataInputStream();
	}

	public void close() {
		connection.close();
	}

	public void closeStreams() {
		if(dos != null){
			try {
				dos.close();
				dos = null;
			} catch (IOException e) {
				System.out.println("BTCConnector.closeStreams IOException");
			}
		}
		
		if(dis != null){
			try {
				dis.close();
				dis = null;
			} catch (IOException e) {
				System.out.println("BTCConnector.closeStreams IOException");
			}
		}
		
	}

	public DataInputStream getDataInputStream() {
		return dis;
	}

	public DataOutputStream getDataOutputStream() {
		return dos;
	}

}
