import Color.Color;
import lejos.nxt.Button;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;
import misc.Helper;
import misc.Robot;


public class CheckCrossing implements Behavior {
	
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


	public CheckCrossing(Robot robot,int distanceUntilActivation,int tolerance){
		this.robot = robot;
		this.distanceUntilActivation = distanceUntilActivation;
		this.tolerance = tolerance;
		active = false;
		terminated = true;
		tachoReseted = false;
	}

	@Override
	public void action() {
		Sound.beep();
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
				Helper.drawText("CheckCrossing.action: Room found. Color:"+color + " Dist:" + robot.getPilot().getTravelDistance());
			}
			
			try{
				Thread.sleep(pollingInterval - (System.currentTimeMillis()-startTime));
			}catch(InterruptedException ex){
			}
		}
		
		robot.getPilot().stop();
		Button.waitForPress();
		robot.getPilot().reset();
		
		
		if(active)
			Helper.error("CheckCrossing.action: Room not found");
		
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
		
		if(!active && terminated){
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
					return true;
				}
			}
			
			return false;
		}
		else
			return true;
	}

}
