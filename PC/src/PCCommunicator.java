import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import Bluetooth.BTPCConnectToBrick;
import Bluetooth.BTSendCommand;
import Bluetooth.BTStreams;

import misc.RobotType;


public class PCCommunicator {
	
	private static DataInputStream mapperIn;
	private static DataInputStream pusherIn;
	private static DataInputStream pullerIn;
	private static DataOutputStream mapperOut;
	private static DataOutputStream pusherOut;
	private static DataOutputStream pullerOut;
	
	public static void main(String[] args) throws IOException, InterruptedException{
		
		//BTPCConnectToBrick.connectToBrick(RobotType.PUSHER);
		//BTPCConnectToBrick.connectToBrick(RobotType.MAPPER);
		BTPCConnectToBrick.connectToBrick(RobotType.PULLER);
		
		//mapperIn = BTStreams.getMapperIn();
		//pusherIn = BTStreams.getPusherIn();
		pullerIn = BTStreams.getPullerIn();
		//mapperOut = BTStreams.getMapperOut();
		//pusherOut = BTStreams.getPusherOut();
		pullerOut = BTStreams.getPullerOut();
		
//		BTReceiveNodes.receiveNodes(mapperOut, mapperIn, false);
//		GraphToTxt.writeTxt(BTReceiveNodes.getGraph(), PCConfig.getTextFile());
		int[] test = new int[5];
		for (int i=0;i<5;i++){
			test[i]=i;
		}
		test[3]=-4;
		BTSendCommand.sendCommand(test, pullerOut, pullerIn);
		
		BTStreams.closeAllStreams();
		BTPCConnectToBrick.closeCon();
		
	}
}
