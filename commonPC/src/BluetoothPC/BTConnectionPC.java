package BluetoothPC;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import Bluetooth.BTStreams;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTConnector;

public class BTConnectionPC implements BTStreams {
	
	private NXTConnector connector;
	private String name;
	private String addr;
	private DataOutputStream dos;
	private DataInputStream dis;
	private boolean open;
	
	
	public BTConnectionPC(String name, String addr){
		this.name = name;
		this.addr = addr;
		connector = null;
		dos = null;
		dis = null;
		open = false;
		
		openConnection();
	}
	
	public void openConnection(){
		connector = new NXTConnector();
		while(!connector.connectTo(name, addr, NXTCommFactory.BLUETOOTH, NXTComm.PACKET)){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		open = true;
	}
	
	public void closeConnection(){
		open = false;
		try {
			connector.close();
		} catch (IOException e) {
		}
	}
	
	public void close(){
		closeStreams();
		closeConnection();
	}
	
	public void closeStreams(){
		try{
			dos.close();
			dos = null;
		}catch(IOException e){
			
		}
		
		try{
			dis.close();
			dis = null;
		}catch(IOException e){
			
		}
	}
	
	public DataInputStream getDataInputStream(){
		if(!open){
			openConnection();
		}
		
		if(dis == null){
			dis = connector.getDataIn();
			
			while(dis == null){
				closeConnection();
				openConnection();
				dis = connector.getDataIn();
			}
		}
		
		return dis;
	}
	
	public DataOutputStream getDataOutputStream(){
		if(!open){
			openConnection();
		}
		
		if(dos == null){
			dos = connector.getDataOut();
			
			while(dos == null){
				closeConnection();
				openConnection();
				
				dos = connector.getDataOut();
			}
		}
		
		return dos;
	}
}
