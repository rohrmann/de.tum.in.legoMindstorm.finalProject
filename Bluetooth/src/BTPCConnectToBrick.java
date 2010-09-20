import lejos.pc.comm.NXTConnector;


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
			BTConnections.setMapperOut(conn.getDataOut());
			BTConnections.setMapperIn(conn.getDataIn());
			System.out.println("In+OutputStream to " +robot);
			break;
		case PUSHER:
			BTConnections.setPusherOut(conn.getDataOut());
			BTConnections.setPusherIn(conn.getDataIn());
			System.out.println("In+OutputStream to " +robot);
			break;
		case PULLER:
			BTConnections.setPullerOut(conn.getDataOut());
			BTConnections.setPullerIn(conn.getDataIn());
			System.out.println("In+OutputStream to " +robot);
			break;
		}

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
