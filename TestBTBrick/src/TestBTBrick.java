import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTCommConnector;
import lejos.nxt.comm.NXTConnection;


public class TestBTBrick {
	public static void main(String[] args) {
		
		NXTCommConnector  conn = Bluetooth.getConnector();
		NXTConnection connection = null;
		System.out.println("waiting for connection");
		
		while((connection = conn.waitForConnection(0, NXTConnection.PACKET))==null){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
		
		DataInputStream input = connection.openDataInputStream();
		DataOutputStream output = connection.openDataOutputStream();
		
		try {
			System.out.println(input.readInt());
		} catch (IOException e) {
		}
		String line = "";
		
		try {
			while(( line = input.readLine()) != null){
				System.out.println(line);
			}
		} catch (IOException e) {
			System.out.println("IOException");
		}
		
		try{
			output.writeInt(0);
			output.flush();
		}catch(IOException e){
			
		}
		
		connection.close();
	}
	

}
