package Actor;

import Graph.Pair;
import misc.RobotType;

public class MoveCommand implements Command {
	private RobotType type;
	private Pair dest;
	
	public MoveCommand(Pair dest, RobotType type){
		this.type = type;
		this.dest = dest;
	}

	@Override
	public void execute(Actor actor) {
		actor.navi.moveTo(dest);
		
	}

	@Override
	public RobotType getType() {
		return type;
	}

	@Override
	public void update(Actor actor) {
		switch(type){
		case PULLER:
			actor.navi.getGraph().setPuller(dest);
			break;
		case PUSHER:
			actor.navi.getGraph().setPusher(dest);
			break;
		}
	}
	
	@Override
	public String toString(){
		return "Move " + type + " to " + dest;
	}

}
