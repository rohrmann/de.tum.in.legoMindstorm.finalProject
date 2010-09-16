import java.io.DataInputStream;

import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import lejos.robotics.subsumption.Behavior;
import misc.Robot;


public class WaitForInput implements Behavior {
	
	boolean end = false;
	
	@Override
	public void action() {
		
		try {
		NXTConnection connectionBT = Bluetooth.waitForConnection();
		DataInputStream disBT = connectionBT.openDataInputStream(); 
		}
		catch (Exception e) {
			
		}
		
		if(true) //TODO Bedingung
			end = true;
		else
			Puller.input = false;

	}

	@Override
	public void suppress() {
	}

	@Override
	public boolean takeControl() {
		if (end)
			return false;
		return Puller.input;
	}

}
