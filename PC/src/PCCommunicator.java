import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import Bluetooth.BTPCConnectToBrick;
import Bluetooth.BTReceiveNodes;
import Bluetooth.BTStreams;
import Config.PCConfig;
import Graph.Graph;
import Text.GraphToTxt;
import Text.TxtToGraph;



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
		BTPCConnectToBrick.connectToBrick(RobotType.MAPPER);
		//BTPCConnectToBrick.connectToBrick(RobotType.PULLER);
		
		mapperIn = BTStreams.getMapperIn();
		//pusherIn = BTStreams.getPusherIn();
		//pullerIn = BTStreams.getPullerIn();
		mapperOut = BTStreams.getMapperOut();
		//pusherOut = BTStreams.getPusherOut();
		//pullerOut = BTStreams.getPullerOut();
		BTReceiveNodes.receiveNodes(mapperOut, mapperIn, false);
		GraphToTxt.writeTxt(BTReceiveNodes.getGraph(), PCConfig.getTextFile());
		//BTPCReceiveNodesFromMapper.receiveNodesFromBrick();
		//Graph graph = TxtToGraph.readtxt(PCConfig.getTextFile());
		
//		pullerOut.write(new byte[]{(byte) BTPCReceiveNodesFromMapper.getTotalNumberOfNodes()});
//		pullerOut.flush();
//		pusherOut.write(new byte[]{(byte) BTPCReceiveNodesFromMapper.getTotalNumberOfNodes()});
//		pusherOut.flush();
		
		BTStreams.closeAllStreams();
		BTPCConnectToBrick.closeCon();
		
	}
}
