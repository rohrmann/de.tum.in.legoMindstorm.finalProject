package NavigationBrick;
import lejos.robotics.subsumption.Behavior;
import miscBrick.Robot;
import miscBrick.RoomInformation;

/**
 * 
 * @author rohrmann
 *
 */
public class DriveForward implements Behavior {
	private Robot robot;
	private RoomInformation information;
	private boolean active;
	private boolean terminated;
	
	
	public DriveForward(Robot robot,RoomInformation information){
		this.information = information;
		this.robot = robot;
		active = false;
		terminated = true;
	}

	//@Override
	public void action() {
		robot.getPilot().forward();
		
		while(active){
			Thread.yield();
		}
		
		terminated = true;
	}

	//@Override
	public void suppress() {
		robot.getPilot().stop();
		
		active = false;
		
		while(!terminated){
			Thread.yield();
		}
		
	}

	//@Override
	public boolean takeControl() {
		if(!information.roomFound()){
			active = true;
			terminated = false;
		}
	
		
		
		
		return !information.roomFound();
	}

}
