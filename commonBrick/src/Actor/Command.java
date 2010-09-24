package Actor;

import misc.RobotType;

public interface Command {
	
	RobotType getType();
	
	void execute(Actor actor);
	
	void update(Actor actor);

}
