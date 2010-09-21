
import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;
import miscBrick.Robot;

public class FindLine implements Behavior {
	
	private Robot robot;
	private boolean active;
	private boolean terminated;

	public FindLine(Robot robot){
		this.robot = robot;
		active = false;
		terminated = true;
	}

	public void action() {
		
		robot.getPilot().setSpeed(100);
		
		if(robot.getLeftLight().groundChange()){
			Motor.B.forward();
			Motor.A.backward();
		}
		else{
			Motor.A.forward();
			Motor.B.backward();
		}	
		
		while(active && robot.getLeftLight().groundChange() ^ robot.getRightLight().groundChange()){
		}
		
		robot.getPilot().stop();
		active =false;
		terminated = true;
	}

	public void suppress() {
		active = false;
		robot.getPilot().stop();	
		
		while(!terminated)
			Thread.yield();
	}

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
