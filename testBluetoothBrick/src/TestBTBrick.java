import lejos.nxt.Button;
import misc.GraphExample;
import Bluetooth.BTCommunicator;
import BluetoothBrick.BTBrickFactory;
import BluetoothBrick.BTConnectionBrick;
import Graph.Graph;


public class TestBTBrick {
	public static void main(String[] args) {
		BTConnectionBrick conn = BTBrickFactory.createConnection();
		
		System.out.println("Connection established");
		
		Button.waitForPress();
		
		Graph graph = GraphExample.getGraph();
		
		BTCommunicator.sendGraph(graph, conn);
		
		System.out.println("Graph send...waiting for ack");
		
		BTCommunicator.receiveAck(conn);
		
		conn.close();
	}
	

}
