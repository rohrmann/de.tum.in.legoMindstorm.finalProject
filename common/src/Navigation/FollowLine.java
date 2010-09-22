package Navigation;
import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;
import misc.Robot;
import misc.RoomInformation;


public class FollowLine implements Behavior {
	
	private Robot robot;
	private boolean active;
	private boolean terminated;
	private RoomInformation information;

	public FollowLine(Robot robot,RoomInformation information){
		this.robot = robot;
		this.information = information;
		active = false;
		terminated = true;
	}
	//@Override
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

	//@Override
	public void suppress() {
		active = false;
		robot.getPilot().stop();	
		
		while(!terminated)
			Thread.yield();
	}

	//@Override
	public boolean takeControl() {
		
		if(!information.roomFound()){
			
			boolean result =robot.getLeftLight().groundChange()^robot.getRightLight().groundChange();
			
			if(result){
				active =true;
				terminated = false;
				return true;
			}
		}
		return false;
	}

}
