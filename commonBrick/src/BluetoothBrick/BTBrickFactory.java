package BluetoothBrick;

import java.io.DataInputStream;
import java.io.DataOutputStream;


import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import miscBrick.Helper;

public class BTBrickFactory {
	
	public static BTConnectionBrick createConnection(){
		
		Helper.drawText("Waiting for BT-Connection");
		BTConnection conn = Bluetooth.waitForConnection();
		Helper.drawText("Connection established");
		
		DataOutputStream dos = conn.openDataOutputStream();
		DataInputStream dis = conn.openDataInputStream();
		
		if(dos == null || dis == null){
			Helper.error("BTBrickFactory.createConnection: could not open data streams");
		}
		
		return new BTConnectionBrick(dos,dis,conn);
	}

}
