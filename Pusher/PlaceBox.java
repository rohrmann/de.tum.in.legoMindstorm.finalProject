package Pusher;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.robotics.navigation.TachoPilot;
import lejos.robotics.subsumption.Behavior;
import misc.Robot;


public class PlaceBox implements Behavior{

	Robot robot;
	
	public PlaceBox(Robot robot)
	{
		this.robot = robot;
	}
	
	public void action() {
		robot.getPilot().travel(-MapInformation.BackOffDistance);
		robot.getPilot().rotate(180);
	}

	public void suppress() {
				
	}

	public boolean takeControl() {
		return true;
	}

}
