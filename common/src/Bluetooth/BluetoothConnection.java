package Bluetooth;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class BluetoothConnection {
	
	private DataInputStream dis;
	private DataOutputStream dos;
	
	public BluetoothConnection(DataOutputStream dos, DataInputStream dis){
		this.dis = dis;
		this.dos = dos;
	}
	
	public DataInputStream getDataInputStream(){
		return dis;
	}
	
	public DataOutputStream getDataOutputStream(){
		return dos;
	}

}
