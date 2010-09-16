import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;


import lejos.pc.comm.*;



public class TestBTPC {

	/**
	 * @param args
	 * @throws NXTCommException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws NXTCommException {
		System.out.println("Connecting ...");
		NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
		NXTInfo nxtInfo = new NXTInfo(NXTCommFactory.BLUETOOTH, "keen", "00:16:53:08:b7:85");
		//nxtComm.search("s_brick", NXTCommFactory.BLUETOOTH);
		nxtComm.open(nxtInfo);
		System.out.println("Connection Success");
		InputStream is = nxtComm.getInputStream();
		DataInputStream input = new DataInputStream(is);
		System.out.println("Inputstream geöffnet");
		OutputStream os = nxtComm.getOutputStream();
	
		System.out.println("Outputstream geöffnet");
		int a;
		while(true){
		try {
			
			a = input.readInt();
			
			System.out.println(a);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		}
		
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//		}
	}

}
