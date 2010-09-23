package BluetoothBrick;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
	}
	
	public void close(){
		closeStreams();
		closeConnection();
	}

	@Override
	public void closeStreams() {
		if(dos != null){
			try {
				dos.close();
				dos = null;
			} catch (IOException e) {
			}
		}
		
		if(dis != null){
			try{
				dis.close();
				dis = null;
			}catch(IOException e){
			}
		}
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
	
	public void openConnection(){
		connection = Bluetooth.waitForConnection();
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
