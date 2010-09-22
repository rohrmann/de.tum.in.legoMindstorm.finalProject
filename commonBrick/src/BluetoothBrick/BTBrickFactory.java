package BluetoothBrick;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import Bluetooth.BluetoothConnection;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import miscBrick.Helper;

public class BTBrickFactory {
	
	public static BluetoothConnection createConnection(){
		BTConnection conn = Bluetooth.waitForConnection();
		
		DataOutputStream dos = conn.openDataOutputStream();
		DataInputStream dis = conn.openDataInputStream();
		
		if(dos == null || dis == null){
			Helper.error("BTBrickFactory.createConnection: could not open data streams");
		}
		
		return new BluetoothConnection(dos,dis);
	}

}
