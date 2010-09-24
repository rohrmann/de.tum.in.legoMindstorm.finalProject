package BluetoothBrick;

import java.io.DataInputStream;
import java.io.DataOutputStream;


import Bluetooth.BTStreams;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class BTConnectionBrick implements BTStreams {

	private BTConnection connection;
	private DataOutputStream dos;
	private DataInputStream dis;
	
	
	public BTConnectionBrick(){
		connection = null;
		dos = null;
		dis = null;
		
		openConnection();
	}
	
	public void close(){
		closeStreams();
		closeConnection();
	}

	public void closeStreams() {
		connection.closeStream();
		dis = null;
		dos = null;
	}

	public DataInputStream getDataInputStream() {
		if(connection == null){
			openConnection();
		}
		
		
		if(dis == null){
			dis = connection.openDataInputStream();
			
			while(dis == null){
				closeConnection();
				openConnection();
				dis = connection.openDataInputStream();
			}
		}
		
		return dis;
		
	}
	
	public void openConnection(){
		System.out.println("Waiting for BT-Connection");
		connection = Bluetooth.waitForConnection();
		System.out.println("Connection established");
	}
	
	public void closeConnection(){
		
		if(connection != null)
			connection.close();
		
		connection = null;
	}

	public DataOutputStream getDataOutputStream() {
		if(connection == null){
			openConnection();
		}
		
		if(dos == null){
			dos = connection.openDataOutputStream();
			
			while(dos == null){
				closeConnection();
				openConnection();
				
				dos = connection.openDataOutputStream();
			}
		}
		
		return dos;
	}
}
