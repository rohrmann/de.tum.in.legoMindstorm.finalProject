import lejos.robotics.subsumption.Behavior;
import misc.Robot;


public class DriveForward implements Behavior {
	private Robot robot;
	
	public DriveForward(Robot robot){
		this.robot = robot;
	}

	@Override
	public void action() {
		robot.getPilot().forward();
	}

	@Override
	public void suppress() {
		robot.getPilot().stop();

	}

	@Override
	public boolean takeControl() {
		return true;
	}

}
