import lejos.robotics.subsumption.Behavior;
import miscBrick.Robot;


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
