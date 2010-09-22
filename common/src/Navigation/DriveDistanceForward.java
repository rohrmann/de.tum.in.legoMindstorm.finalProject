package Navigation;

import lejos.robotics.subsumption.Behavior;
import misc.Robot;


public class DriveDistanceForward implements Behavior{

	Robot robot;
	float distance;
	boolean active = false;
	
	public DriveDistanceForward(Robot robot, float distance)
	{
		this.distance = distance;
		this.robot = robot;
		robot.getPilot().reset();
	}
	
	public void setDistance(float distance)
	{
		this.distance = distance;
		robot.getPilot().reset();
	}
	
	public void action() {
		//robot.getPilot().travel(distance, false);
		robot.getPilot().forward();
		
		while(active && distance > robot.getPilot().getTravelDistance())
		{
			//if(!robot.getPilot().isMoving()) robot.getPilot().forward();
		}
		
		suppress();
		
	}

	public void suppress() {
		active = false;
		robot.getPilot().stop();
		distance -= robot.getPilot().getTravelDistance();
		robot.getPilot().reset();
	}

	public boolean takeControl() {
		if(distance>0)
		{
			active = true;
			return true;
		}
		return false;
		
	}

}
