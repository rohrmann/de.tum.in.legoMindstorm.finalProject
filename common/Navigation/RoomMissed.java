package Navigation;

import Color.Color;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;
import misc.Helper;
import misc.Robot;
import misc.RoomInformation;

public class RoomMissed {

	private static final int roomDistance = 20;
	private static final int roomDistanceTolerance = 3;
	private static final int pollingInterval = 10;
	private static final int acceptionPeriodForColor = 150;
	
	public static Color action(Robot robot, int interval){
		Sound.twoBeeps();
		Helper.drawString("RoomMissed started", 0, 5);
		Color color = Color.UNKNOWN;
		float distanceTravelled = robot.getPilot().getTravelDistance();
		
		while(distanceTravelled - interval <= robot.getPilot().getTravelDistance()){
			robot.getPilot().backward();
		}
		robot.getPilot().stop();
		
		/*while(interval > 0){
			//drive backward first
			
			//robot.getPilot().forward();
			
			long startTime;
			Color lastColor=Color.UNKNOWN;
			long startColor=System.currentTimeMillis();
			
			
			startTime = System.currentTimeMillis();
			lastColor = color;
			color = robot.getColor().getColorName();
			
			Helper.drawText(color.toString());
			
			if(color != lastColor){
				startColor = System.currentTimeMillis();
			}
			else if(color== lastColor && color.isRoomColor() && System.currentTimeMillis()-startColor > 
			acceptionPeriodForColor){
				break;
			}*/
			
			
			
			//decrement the interval for the next step to be smaller, update distance travelled
			interval -= 0.5;
			//distanceTravelled = robot.getPilot().getTravelDistance();
		//}
		
		
		if (color.isRoomColor()){
			return color;
		}
		return null;

		
		/*
		
		long startTime;
		Color color=Color.UNKNOWN;
		Color lastColor=Color.UNKNOWN;
		long startColor=System.currentTimeMillis();
		
		
		startTime = System.currentTimeMillis();
		lastColor = color;
		color = robot.getColor().getColorName();
		
		Helper.drawText(color.toString());
		
		if(color != lastColor){
			startColor = System.currentTimeMillis();
		}
		else if(color== lastColor && color.isRoomColor() && System.currentTimeMillis()-startColor > acceptionPeriodForColor){
			information.setRoomColor(color);
		}
		
		try{
			Thread.sleep(pollingInterval - (System.currentTimeMillis()-startTime));
		}catch(InterruptedException ex){
		}
		
		
		
		
		
		robot.getPilot().setMoveSpeed(10);
		*/
	}
	
}
