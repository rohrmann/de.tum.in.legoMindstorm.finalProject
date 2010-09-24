package Actor;

import Graph.Pair;
import Graph.Type;
import misc.Direction;
import misc.RobotType;

public class ActionCommand implements Command {
	
	private Pair src;
	private Pair dest;
	private RobotType type;
	
	public ActionCommand(Pair src, Pair dest,RobotType type){
		this.src = src;
		this.dest = dest;
		this.type = type;
	}


	public void execute(Actor actor) {
		actor.navi.turn(Direction.findDirection(actor.navi.getPosition(),src));
		actor.prolog();
		
		actor.navi.moveStraightForward(Math.abs(src.getX()-dest.getX())+Math.abs(src.getY()-dest.getY())-1);
		
		actor.epilog();
	}

	public RobotType getType() {
		return type;
	}

	public void update(Actor actor) {
		actor.navi.getGraph().setNode(src, Type.EMPTY);
		actor.navi.getGraph().setNode(dest, Type.BOX);
		
		switch(type){
		case PULLER:
			Pair pos = dest.getNeighbour(Direction.findDirection(src, dest));
			actor.navi.getGraph().setPuller(pos);
			break;
		case PUSHER:
			pos = dest.getNeighbour(Direction.findDirection(src,dest).opposite());
			actor.navi.getGraph().setPusher(pos);
			break;
		}
	}
	
	public String toString(){
		return "Action " + type + " from " + src + " to " + dest;
	}

}
