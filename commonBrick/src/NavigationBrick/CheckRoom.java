package NavigationBrick;
import Color.Color;
import ErrorHandlingBrick.ErrorHandling;
import ErrorHandlingBrick.RoomMissed;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;
import miscBrick.Config;
import miscBrick.Helper;
import miscBrick.Robot;
import miscBrick.RoomInformation;

/**
 * 
 * @author rohrmann
 *
 */
public class CheckRoom implements Behavior {
	
	private Robot robot;
	private boolean active;
	private boolean terminated;
	private boolean tachoReseted;
	private int distanceUntilActivation;
	private int tolerance;
	private RoomInformation information;
	private RoomNavigator navi;


	public CheckRoom(Robot robot,int distanceUntilActivation,int tolerance,
			RoomInformation information, RoomNavigator navi){
		this.robot = robot;
		this.distanceUntilActivation = distanceUntilActivation;
		this.tolerance = tolerance;
		active = false;
		terminated = true;
		tachoReseted = false;
		this.information = information;
		this.navi = navi;
	}

	//@Override
	public void action(){
		Sound.beep();
		robot.getPilot().setMoveSpeed(5);
		robot.getPilot().forward();
		long startTime;
		Color color=Color.UNKNOWN;
		Color lastColor=Color.UNKNOWN;
		long startColor=System.currentTimeMillis();
		
		while(active && robot.getPilot().getTravelDistance() < Config.roomDistance+Config.roomDistanceTolerance){
			startTime = System.currentTimeMillis();
			lastColor = color;
			color = robot.getColor().getColorName();
			
			Helper.drawText(color.toString());
			
			if(color != lastColor){
				startColor = System.currentTimeMillis();
			}
			else if(color== lastColor && color.isRoomColor() && System.currentTimeMillis()-startColor > Config.acceptionPeriodForColor){
				active = false;
				information.setRoomColor(color);
				navi.getErrorInformation().setError(false);
			}
			
			try{
				Thread.sleep(Config.checkRoomPollingInterval - (System.currentTimeMillis()-startTime));
			}catch(InterruptedException ex){
			}
		}
		
		robot.getPilot().stop();
	
		// active is set false when the color of the room is set -> if still active the room was
		// not found
		if(active){
			Color missColor = RoomMissed.action(robot, 6, information);
			if(missColor != null){
				active = false;
				information.setRoomColor(missColor);
				navi.getErrorInformation().setError(false);
			}else{
				robot.getPilot().stop();
				ErrorHandling.resolvebyHand(robot, navi);
				navi.getErrorInformation().setError(true);
			}
			
		}
		
		robot.getPilot().reset();
		robot.getPilot().setMoveSpeed(15);
		
		active = false;
		terminated = true;
	}

	//@Override
	public void suppress() {
		robot.getPilot().stop();
		active = false;
		while(!terminated){
			Thread.yield();
		}
		
		terminated = true;
	}

	//@Override
	public boolean takeControl() {
		
		if(!information.roomFound() && !active && terminated){
			if(tachoReseted == false){
				robot.getPilot().reset();
				tachoReseted = true;
			}
			else{
				if(robot.getPilot().getTravelDistance() >= distanceUntilActivation 
						&& robot.getPilot().getTravelDistance() <= distanceUntilActivation+tolerance){
					active = true;
					terminated = false;
					Helper.drawText("CheckRoom activated");
					return true;
				}
			}
			return false;
		}
		else
			return false;
	}

}
