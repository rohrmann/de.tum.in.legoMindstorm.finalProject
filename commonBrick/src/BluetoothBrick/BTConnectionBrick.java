package BluetoothBrick;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.bluetooth.RemoteDevice;

import Bluetooth.BTStreams;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import miscBrick.Helper;

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

	@Override
	public void closeStreams() {
		connection.closeStream();
		dis = null;
		dos = null;
	}

	@Override
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
	
	public void openConnectionTo(RemoteDevice rd){
		System.out.println("Sending BT-Connection");
		connection = Bluetooth.connect(rd);
		System.out.println("Connection established");
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

	@Override
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
