package Pusher;

import lejos.robotics.subsumption.Behavior;
import misc.Robot;

public class Turn implements Behavior {

	boolean LineFound = false;
	Robot robot;
	int turnDegree;
	
	public Turn(Robot robot,int turnDegree)
	{
		this.robot = robot;
		this.turnDegree = turnDegree;
	}
	
	public void action() {
		robot.getPilot().rotate(turnDegree);
	}

	public void suppress() {
		robot.getPilot().stop();
		LineFound = true;
	}

	public boolean takeControl() {
		return !LineFound;
	}

}
