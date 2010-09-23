import Bluetooth.BTCommunicator;
import BluetoothPC.BTConnectionPC;
import BluetoothPC.BTPCFactory;
import Graph.Graph;
import PCConfig.PCConfig;
import Text.GraphToTxt;


public class MapRetriever {
	
	public static void main(String[] args){
		MapRetriever retriever = new MapRetriever();
		
		retriever.start();
	}
	
	public void start(){
		String keen ="keen";
		BTConnectionPC connection = BTPCFactory.createConnectionInd(keen);
		
		Graph graph = BTCommunicator.receiveGraph(connection);
		
		if(graph == null){
			System.out.println("error receiving map");
			System.exit(1);
		}
		
		BTCommunicator.ack(connection);
		
		GraphToTxt.writeTxt(graph, PCConfig.getTextFile());
		
		connection.close();
	}

}
