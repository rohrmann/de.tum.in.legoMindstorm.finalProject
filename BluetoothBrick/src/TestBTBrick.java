import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import misc.Helper;


public class TestBTBrick {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Helper.drawString("Press Any Button", 0, 0);
		Button.waitForPress();
		LCD.clearDisplay();
		Helper.drawString("Wait for Connect", 0, 0);
		BTConnection btc = Bluetooth.waitForConnection();
		Helper.drawString("Connection Success", 0, 1);
		DataInputStream is = btc.openDataInputStream();
		Helper.drawString("Inputstream geöffnet",0,2);
		DataOutputStream os = btc.openDataOutputStream();
		Helper.drawString("Outputstream geöffnet",0,3);
		String a = "Test bisschen anspruchsvoller";
		for(int i=0;i<100;i++){
			os.writeInt(i);
			os.flush();
		}
		os.close();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}

}
