import java.io.IOException;


public class PCCommunicator {
	public static void main(String[] args) throws IOException, InterruptedException{
		BTPCConnectToBrick.connectToBrick(RobotType.MAPPER);
		BTPCReceiveNodesFromMapper.receiveNodesFromBrick();
	}
}
