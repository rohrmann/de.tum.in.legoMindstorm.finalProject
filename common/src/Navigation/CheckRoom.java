package Navigation;
import Color.Color;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;
import misc.Helper;
import misc.Robot;
import misc.RoomInformation;


public class CheckRoom implements Behavior {
	
	private Robot robot;
	private boolean active;
	private boolean terminated;
	private boolean tachoReseted;
	private int distanceUntilActivation;
	private int tolerance;
	private final int roomDistance = 20;
	private final int roomDistanceTolerance = 3;
	private final int pollingInterval = 10;
	private final int acceptionPeriodForColor = 150;
	private RoomInformation information;


	public CheckRoom(Robot robot,int distanceUntilActivation,int tolerance,RoomInformation information){
		this.robot = robot;
		this.distanceUntilActivation = distanceUntilActivation;
		this.tolerance = tolerance;
		active = false;
		terminated = true;
		tachoReseted = false;
		this.information = information;
	}

	@Override
	public void action() {
		Sound.beep();
		robot.getPilot().setMoveSpeed(5);
		robot.getPilot().forward();
		long startTime;
		Color color=Color.UNKNOWN;
		Color lastColor=Color.UNKNOWN;
		long startColor=System.currentTimeMillis();
		
		while(active && robot.getPilot().getTravelDistance() < roomDistance+roomDistanceTolerance){
			startTime = System.currentTimeMillis();
			lastColor = color;
			color = robot.getColor().getColorName();
			
			Helper.drawText(color.toString());
			
			if(color != lastColor){
				startColor = System.currentTimeMillis();
			}
			else if(color== lastColor && color.isRoomColor() && System.currentTimeMillis()-startColor > acceptionPeriodForColor){
				active = false;
				information.setRoomColor(color);
			}
			
			try{
				Thread.sleep(pollingInterval - (System.currentTimeMillis()-startTime));
			}catch(InterruptedException ex){
			}
		}
		
		robot.getPilot().stop();
		robot.getPilot().reset();
	
		if(active)
			Helper.error("CheckCrossing.action: Room not found");
		
		robot.getPilot().setMoveSpeed(10);
		
		active = false;
		terminated = true;
	}

	@Override
	public void suppress() {
		robot.getPilot().stop();
		active = false;
		while(!terminated){
			Thread.yield();
		}
		
		terminated = true;
	}

	@Override
	public boolean takeControl() {
		
		if(!information.roomFound() && !active && terminated){
			if(tachoReseted == false){
				robot.getPilot().reset();
				tachoReseted = true;
			}
			else{
				if(robot.getPilot().getTravelDistance() >= distanceUntilActivation+tolerance){
					robot.getPilot().stop();
					Helper.error("CheckCrossing.takeControl: Room missed");
				}
				else if(robot.getPilot().getTravelDistance() >= distanceUntilActivation){
					active =true;
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
