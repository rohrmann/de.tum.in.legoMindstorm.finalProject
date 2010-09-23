
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import PCConfig.PCConfig;
import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTConnector;

public class TestBTPC {	
	public static void main(String[] args) {
		
		NXTConnector connector = null;
		
		int i =0;
		while(true){
			System.out.println("try to connect");
			connector = new NXTConnector();
			boolean connected = connector.connectTo(PCConfig.getPusher(), PCConfig.getPusherAddr(), NXTCommFactory.BLUETOOTH, NXTComm.PACKET);
			
				if(connected){
					System.out.println("connection established");
					
					DataInputStream dis = connector.getDataIn();
				
					try {
						System.out.println(dis.readInt());
					} catch (IOException e2) {
						e2.printStackTrace();
					}
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				/*	try {
						dos.close();
						dis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}*/
					
					NXTComm comm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
					
					comm.open(comm.search(name, protocol))
					
					System.out.println("closing connection");
					try {
						connector.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					System.out.println("connection closed");
				}
			
			i++;
		}
	}
}