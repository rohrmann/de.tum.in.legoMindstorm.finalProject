package NavigationBrick;

import lejos.robotics.subsumption.Behavior;
import miscBrick.Robot;


public class DriveDistanceForward implements Behavior{

	Robot robot;
	float distance;
	boolean active = false;
	boolean terminated = false;
	
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
		robot.getPilot().forward();
		
		while(active && distance > robot.getPilot().getTravelDistance())
		{
			Thread.yield();
		}
		
		active = false;
		robot.getPilot().stop();
		distance -= robot.getPilot().getTravelDistance();
		robot.getPilot().reset();
		terminated = true;
	}

	public void suppress() {
		active = false;
		while(!terminated)
			Thread.yield();
	}

	public boolean takeControl() {
		if(distance>0)
		{
			active = true;
			terminated = false;
			return true;
		}
		return false;
		
	}

}
