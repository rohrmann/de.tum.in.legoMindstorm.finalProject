import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import misc.Action;
import misc.Direction;

import Bluetooth.MessageComm;
import BluetoothPC.BTConnectionPC;
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
		
		System.out.println(graph);
		
		BTConnectionPC pusher = new BTConnectionPC(PCConfig.getPusher(),PCConfig.getPusherAddr());
		BTConnectionPC puller = new BTConnectionPC(PCConfig.getKeen(),PCConfig.getKeenAddr());
		
		System.out.println("send map to pusher");
		MessageComm.sendMap(graph, pusher);
		System.out.println("send map to puller");
		MessageComm.sendMap(graph,puller);
		
		String line = "";
		try{
			while(( line = input.readLine()) != null){
				String[] parts = line.split(" ");
				
				if(parts[0].equals("pusher")){
					System.out.println("send update to pusher");
					MessageComm.sendUpdate(graph, pusher);
					
					Pair pair = Pair.parsePair(parts[1]);
					
					System.out.println("move pusher to " + pair);
					
					MessageComm.sendMove(pair, pusher);
			
					graph.setPusher(pair);
				}
				else if(parts[0].equals("puller")){
					System.out.println("send update to puller");
					MessageComm.sendUpdate(graph, puller);
					
					Pair pair = Pair.parsePair(parts[1]);
					
					System.out.println("move puller to " + pair);
					
					MessageComm.sendMove(pair, puller);
			
					graph.setPuller(pair);
					
				}
				else if(parts[0].equals("pull")){
					System.out.println("send update to puller");

					MessageComm.sendUpdate(graph,puller);
					
					Pair src = Pair.parsePair(parts[1]);
					Pair dest = Pair.parsePair(parts[2]);
					
					System.out.println("pull from " + src + " to " + dest);
					
					MessageComm.sendAction(new Action(src,dest),puller);
					
					graph.updateBox(src,dest);
					
					graph.setPuller(dest.getNeighbour(Direction.findDirection(src, dest)));
					
				}
				else if(parts[0].equals("push")){
					System.out.println("send update to pusher");
					MessageComm.sendUpdate(graph,pusher);
					Pair src = Pair.parsePair(parts[1]);
					Pair dest = Pair.parsePair(parts[2]);
					
					System.out.println("push from " + src + " to " + dest);
					
					MessageComm.sendAction(new Action(src,dest),pusher);
					
					
					
					graph.updateBox(src,dest);
					
					graph.setPusher(dest.getNeighbour(Direction.findDirection(src, dest).opposite()));
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		
		try{
			input.close();
		}catch(IOException e){
			
		}
		
		puller.close();
		pusher.close();
	}

}
