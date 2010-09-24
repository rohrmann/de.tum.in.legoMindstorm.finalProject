
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import PCConfig.PCConfig;
import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTConnector;

public class TestBTPC {	
	
	static Random rand = new Random();
	public static void main(String[] args) {
		
		NXTConnector connector = new NXTConnector();
		
		System.out.println("try to connect");
		
		while(!connector.connectTo(PCConfig.s_brick, PCConfig.s_brickAddr, NXTCommFactory.BLUETOOTH, NXTComm.PACKET)){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
			
		System.out.println("connection established");
			
		DataInputStream dis = connector.getDataIn();
		DataOutputStream dos = connector.getDataOut();
		
		String[] text = {"pull (1,2) (1,3)", "pusher (4,4)", "puller (4,3)", "terminate"};
		
		try {
			dos.writeInt(0);
			dos.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		for(String line : text){
			try {
				System.out.println(line);
				dos.writeChars(line);
				dos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try{
			System.out.println(dis.readInt());
		}catch(IOException e){
			e.printStackTrace();
		}
		
		try {
			connector.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}