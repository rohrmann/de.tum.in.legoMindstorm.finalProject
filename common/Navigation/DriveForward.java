package Navigation;
import lejos.robotics.subsumption.Behavior;
import misc.Robot;
import misc.RoomInformation;


public class DriveForward implements Behavior {
	private Robot robot;
	private RoomInformation information;
	
	public DriveForward(Robot robot,RoomInformation information){
		this.information = information;
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
		return !information.roomFound();
	}

}
