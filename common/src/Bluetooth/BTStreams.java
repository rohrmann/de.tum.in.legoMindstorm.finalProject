package Bluetooth;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BTStreams {
	
	protected DataInputStream dis;
	protected DataOutputStream dos;
	
	public BTStreams(DataOutputStream dos, DataInputStream dis){
		this.dis = dis;
		this.dos = dos;
	}
	
	public DataInputStream getDataInputStream(){
		return dis;
	}
	
	public DataOutputStream getDataOutputStream(){
		return dos;
	}
	
	public void close(){
		try {
			dis.close();
		} catch (IOException e) {
		}
		
		try {
			dos.close();
		} catch (IOException e) {
		}
	}

}
