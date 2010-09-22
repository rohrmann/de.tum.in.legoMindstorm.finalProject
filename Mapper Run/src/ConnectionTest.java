import java.io.IOException;

import BluetoothBrick.BTBrick;
import BluetoothBrick.BTReceiveCommand;


public class ConnectionTest {

	
	public static void main(String[] args) throws InterruptedException, IOException {
		BTBrick.connectToPC();
		//BTBrick.receiveTestNumber();
		BTReceiveCommand.receiveCommand(BTBrick.getOutput(), BTBrick.getInput());
	}

}
