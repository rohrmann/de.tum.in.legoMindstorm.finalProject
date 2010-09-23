package BluetoothPC;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import Bluetooth.BTStreams;

import lejos.pc.comm.NXTConnector;

public class BTConnectionPC extends BTStreams {
	
	protected DataOutputStream dos;
	protected DataInputStream dis;
	protected NXTConnector connector;
	protected String name;
	
	public BTConnectionPC(DataOutputStream dos, DataInputStream dis, NXTConnector connector,String name){
		super(dos,dis);
		this.connector = connector;
		this.name = name;
	}
	
	public void close(){
		super.close();
		try{
			connector.close();
		}catch(IOException e){
			
		}
	}
	
	

}
