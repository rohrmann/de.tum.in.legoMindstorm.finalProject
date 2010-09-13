import lejos.robotics.subsumption.Behavior;
import misc.Robot;


public class CheckRoom implements Behavior {

	private Robot robot;

	public CheckRoom(Robot robot){
		this.robot = robot;
	}
	@Override
	public void action() {
		// TODO Auto-generated method stub

	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return false;
	}

}
