
import BluetoothPC.BTPCFactory;
import BluetoothPC.BluetoothConnectionPC;

public class TestBTPC {	
	public static void main(String[] args) {
		String brickname = "keen";
		String deviceURL = "00165308B785";
		BluetoothConnectionPC conn = BTPCFactory.createConnectionInd(brickname);
		
		System.out.println("Connection established");
		
		conn.close();
	}
}