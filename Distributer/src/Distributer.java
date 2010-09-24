import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import misc.Action;
import misc.RobotType;

import Bluetooth.MessageComm;
import BluetoothPC.BTConnectionPC;
import Graph.Graph;
import Graph.Pair;
import PCConfig.PCConfig;
import Text.TxtToGraph;


public class Distributer {
	
	public static void main(String [] args){
		Distributer commander = new Distributer();
		
		commander.start(args[0]);
	}
	
	public void start(String filename){
		
		BufferedReader input = null;
		List<String> commands = new ArrayList<String>();
		
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
		
		
		BTConnectionPC pusher = new BTConnectionPC(PCConfig.s_brick,PCConfig.s_brickAddr);
		BTConnectionPC puller = new BTConnectionPC(PCConfig.rightoo,PCConfig.rightooAddr);

		System.out.println("send map to pusher");
		MessageComm.sendMap(graph, pusher);
		System.out.println("send map to puller");
		MessageComm.sendMap(graph,puller);
		String line = "";
		
		try {
			while((line = input.readLine())!= null){
				commands.add(line);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for(String command : commands){
	
			System.out.println(command);
			String [] parts = command.split(" " );
			
			if(parts[0].equals("puller")){
				Pair pos = Pair.parsePair(parts[1]);
				System.out.println("Send pull " + pos + " to pusher");
				MessageComm.sendMove(RobotType.PULLER,pos,pusher);
				System.out.println("Send pull " + pos + " to puller");
				MessageComm.sendMove(RobotType.PULLER,pos,puller);
			}
			else if(parts[0].equals("pull")){
				Pair src = Pair.parsePair(parts[1]);
				Pair dest = Pair.parsePair(parts[2]);
				Action action = new Action(src,dest);
				System.out.println("Send pull" + action + " to pusher");
				MessageComm.sendAction(RobotType.PULLER, action, pusher);
				System.out.println("Send pull" + action + " to puller");
				MessageComm.sendAction(RobotType.PULLER, action, puller);
			}
			else if(parts[0].equals("pusher")){
				Pair pos = Pair.parsePair(parts[1]);
				System.out.println("Send pusher " + pos + " to pusher");
				MessageComm.sendMove(RobotType.PUSHER,pos,pusher);
				System.out.println("Send pusher " + pos + " to puller");
				MessageComm.sendMove(RobotType.PUSHER,pos,puller);

			}
			else if(parts[0].equals("push")){
				Pair src = Pair.parsePair(parts[1]);
				Pair dest = Pair.parsePair(parts[2]);
				Action action = new Action(src,dest);
				System.out.println("Send push" + action + " to pusher");
				MessageComm.sendAction(RobotType.PUSHER, new Action(src,dest), pusher);
				System.out.println("Send push" + action + " to puller");
				MessageComm.sendAction(RobotType.PUSHER, new Action(src,dest), puller);

			}
		}
		
		MessageComm.sendFinish(pusher);
		MessageComm.sendFinish(puller);
		
		
		try{
			input.close();
		}catch(IOException e){
			
		}
		
		pusher.close();
		puller.close();
	}

}
