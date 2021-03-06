import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;
import misc.Robot;


public class FollowLine implements Behavior {
	
	private Robot robot;
	private boolean active;
	private boolean terminated;

	public FollowLine(Robot robot){
		this.robot = robot;
		active = false;
		terminated = true;
	}
	@Override
	public void action() {
		
		if(robot.getLeftLight().groundChange()){
			Motor.B.forward();
			Motor.A.stop();
		}
		else{
			Motor.A.forward();
			Motor.B.stop();
		}
		
		while(active && robot.getLeftLight().groundChange() ^ robot.getRightLight().groundChange()){
		}
		
		robot.getPilot().stop();
		active =false;
		terminated = true;
	}

	@Override
	public void suppress() {
		active = false;
		robot.getPilot().stop();	
		
		while(!terminated)
			Thread.yield();
	}

	@Override
	public boolean takeControl() {
		boolean result =robot.getLeftLight().groundChange()^robot.getRightLight().groundChange();
		
		if(result){
			active =true;
			terminated = false;
			return true;
		}
		
		return false;
	}

}
