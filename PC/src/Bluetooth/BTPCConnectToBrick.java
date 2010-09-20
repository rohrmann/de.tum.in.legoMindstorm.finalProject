package Bluetooth;
import java.io.IOException;

import Config.PCConfig;

import lejos.pc.comm.NXTConnector;
import misc.RobotType;


public class BTPCConnectToBrick {
	
	private static NXTConnector conn;
	
	public static void connectToBrick(RobotType robot) throws InterruptedException{
		conn = new NXTConnector();
		String brick = getRobotName(robot);
		while (!conn.connectTo(brick)) {
			System.err.println("Failed to connect to " +robot);
			Thread.sleep(1000);
		}
		switch(robot){
		case MAPPER:
			BTStreams.setMapperOut(conn.getDataOut());
			BTStreams.setMapperIn(conn.getDataIn());
			System.out.println("In+OutputStream to " +robot);
			break;
		case PUSHER:
			BTStreams.setPusherOut(conn.getDataOut());
			BTStreams.setPusherIn(conn.getDataIn());
			System.out.println("In+OutputStream to " +robot);
			break;
		case PULLER:
			BTStreams.setPullerOut(conn.getDataOut());
			BTStreams.setPullerIn(conn.getDataIn());
			System.out.println("In+OutputStream to " +robot);
			break;
		}

	}
	
	public static void closeCon() throws IOException{
		conn.close();
	}
	
	public static String getRobotName(RobotType robot){
		switch(robot){
			case MAPPER:
				return PCConfig.getMapper();
			case PUSHER:
				return PCConfig.getPusher();
			case PULLER:
				return PCConfig.getPuller();
		}
		return null;
	}
	
}
