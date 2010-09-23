package BluetoothPC;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTConnector;

public class BTPCFactory {
	
	public static BTConnectionPC createConnection(String brick){
		NXTConnector connector = new NXTConnector();
		
		boolean connected = connector.connectTo(brick);
				
		if(!connected){
			return null;
		}
		
		return new BTConnectionPC(connector.getDataOut(),connector.getDataIn(),connector,brick);
	}
	
	public static BTConnectionPC createConnectionInd(String brick, String addr){
		NXTConnector connector = new NXTConnector();
		
		while(!connector.connectTo(brick, addr, NXTCommFactory.BLUETOOTH, NXTComm.PACKET)){
			;
		}
		
		return new BTConnectionPC(connector.getDataOut(),connector.getDataIn(),connector,brick);
	}

}
