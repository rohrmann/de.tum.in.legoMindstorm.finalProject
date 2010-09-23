package BluetoothBrick;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import Bluetooth.BTStreams;

import lejos.nxt.comm.BTConnection;

public class BTConnectionBrick extends BTStreams{

	protected BTConnection connection;
	
	public BTConnectionBrick(DataOutputStream dos, DataInputStream dis, BTConnection connection){
		super(dos,dis);
		this.connection = connection;
	}
	
	public void close(){
		super.close();
		
		connection.close();
	}
}
