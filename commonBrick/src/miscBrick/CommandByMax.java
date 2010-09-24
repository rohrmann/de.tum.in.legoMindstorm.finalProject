package miscBrick;

import Bluetooth.MessageType;
import Graph.Graph;
import Graph.Pair;
import misc.Action;
import misc.RobotType;
import misc.Update;

public class CommandByMax {
	
	MessageType messageType = null;
	RobotType robotType = null;
	Pair moveTo = null;
	Action action = null;
	Update update = null;
	Graph graph = null;
	
	
	public CommandByMax(MessageType mT, RobotType rT, Pair moTo, Action act, Update upd, Graph graph1){
		messageType = mT;
		robotType = rT;
		moveTo = moTo;
		action = act;
		update = upd;
		graph = graph1;
	}
	
	public MessageType getMessageType(){
		return messageType;
	}
	public RobotType getRobotType(){
		return robotType;
	}
	public Pair getPair(){
		return moveTo;
	}
	public Action getAction(){
		return action;
	}
	public Update getUpdate(){
		return update;
	}
	public Graph getGraph(){
		return graph;
	}	

}
