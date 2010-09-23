import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import misc.Action;
import misc.Direction;

import Bluetooth.MessageComm;
import BluetoothPC.BTConnectionPC;
import BluetoothPC.BTPCFactory;
import Graph.Graph;
import Graph.Pair;
import PCConfig.PCConfig;
import Text.TxtToGraph;


public class Commander {
	
	public static void main(String [] args){
		Commander commander = new Commander();
		
		commander.start(args[0]);
	}
	
	public void start(String filename){
		BTConnectionPC pusher = BTPCFactory.createConnectionInd("asd","0016530A73F6");
		BTConnectionPC puller = BTPCFactory.createConnectionInd("keen","00165308B785");
		
		
		BufferedReader input = null;
		
		try {
			input = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
		}
		
		if(input == null){
			return;
		}
		
		Graph graph = null;
		try {
			graph = TxtToGraph.readGraph(input);
		} catch (IOException e) {
		}
		
		MessageComm.sendMap(graph, pusher);
		MessageComm.sendMap(graph,puller);
		
		String line = "";
		try{
			while(( line = input.readLine()) != null){
				System.out.println(graph);
				String[] parts = line.split(" ");
				
				if(parts[0].equals("pusher")){
					MessageComm.sendUpdate(graph, pusher);
					
					Pair pair = Pair.parsePair(parts[1]);
					
					MessageComm.sendMove(pair, pusher);
			
					graph.setPusher(pair);
				}
				else if(parts[0].equals("puller")){
					MessageComm.sendUpdate(graph, puller);
					
					Pair pair = Pair.parsePair(parts[1]);
					
					MessageComm.sendMove(pair, puller);
			
					graph.setPuller(pair);
					
				}
				else if(parts[0].equals("pull")){
					MessageComm.sendUpdate(graph,puller);
					
					Pair src = Pair.parsePair(parts[1]);
					Pair dest = Pair.parsePair(parts[2]);
					
					MessageComm.sendAction(new Action(src,dest),puller);
					
					graph.updateBox(src,dest);
					
					graph.setPuller(dest.getNeighbour(Direction.findDirection(src, dest)));
					
				}
				else if(parts[0].equals("push")){
					MessageComm.sendUpdate(graph,pusher);
					Pair src = Pair.parsePair(parts[1]);
					Pair dest = Pair.parsePair(parts[2]);
					
					MessageComm.sendAction(new Action(src,dest),pusher);
					
					graph.updateBox(src,dest);
					
					graph.setPusher(dest.getNeighbour(Direction.findDirection(src, dest).opposite()));
				}
			}
		}catch(IOException e){
		}
		
		try{
			input.close();
		}catch(IOException e){
			
		}
		
		puller.close();
		pusher.close();
	}

}
