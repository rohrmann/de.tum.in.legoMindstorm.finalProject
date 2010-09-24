package BluetoothBrick;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.bluetooth.RemoteDevice;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

import Bluetooth.BTStreams;

public class BTCConnector implements BTStreams {
	
	private String brick;
	private BTConnection connection;
	private DataOutputStream dos;
	private DataInputStream dis;
	private Random rand;
	
	public BTCConnector(String brick){
		this.brick = brick;
		this.connection = null;
		dos = null;
		dis = null;
		
		openConnection();
	}
	
	public void openConnection(){
		System.out.println("Look for known device:" + brick);
		
		RemoteDevice rd = null;
		while((rd = Bluetooth.getKnownDevice(brick)) == null){
			try {
				Thread.sleep(100 + rand.nextInt(150));
			} catch (InterruptedException e) {
			}
		}
		
		System.out.println("Try to connect");
		while((connection = Bluetooth.connect(rd))==null){
			try {
				Thread.sleep(100 + rand.nextInt(150));
			} catch (InterruptedException e) {
			}
		}
		
		System.out.println("Connection established");
		
		dos = connection.openDataOutputStream();
		dis = connection.openDataInputStream();
	}

	@Override
	public void close() {
		connection.close();
		
	}

	@Override
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

	@Override
	public DataInputStream getDataInputStream() {
		return dis;
	}

	@Override
	public DataOutputStream getDataOutputStream() {
		return dos;
	}

}
