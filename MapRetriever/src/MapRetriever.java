import Bluetooth.BTCommunicator;
import BluetoothPC.BTConnectionPC;
import Graph.Graph;
import PCConfig.PCConfig;
import Text.GraphToTxt;


public class MapRetriever {
	
	public static void main(String[] args){
		MapRetriever retriever = new MapRetriever();
		
		retriever.start();
	}
	
	public void start(){
		BTConnectionPC connection = new BTConnectionPC(PCConfig.getMapper(), PCConfig.getMapperAddr());
		
		Graph graph = BTCommunicator.recvGraph(connection);
		
		if(graph == null){
			System.out.println("error receiving map");
			System.exit(1);
		}
		
		
		GraphToTxt.writeTxt(graph, PCConfig.getTextFile());
		
		connection.close();
	}

}
