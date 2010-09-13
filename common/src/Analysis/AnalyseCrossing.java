package Analysis;

import java.util.List;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import misc.Direction;
import misc.Helper;
import misc.Robot;

public class AnalyseCrossing {

	private Robot robot;
	private Direction startDirection;
	private float drehToleranz = 45;

	public AnalyseCrossing(Robot robot) {
		this.robot = robot;
	}

	public List<Direction> analyseCrossing(Direction heading) {
		startDirection = heading;
		List<Direction> directionList;
		robot.getPilot().rotate(360, true);
		int a = 0;
		float winkel = 0;
		LCD.clearDisplay();
		while (robot.getLeftLight().groundChange()) {

		}
		robot.getPilot().reset();
		while (robot.getPilot().getAngle() < 360) {

			if (robot.getLeftLight().groundChange()) {
				winkel = robot.getPilot().getAngle();
				Sound.beep();
				if (startDirection == Direction.EAST) {
					winkel = winkel + 270;
				} else if (startDirection == Direction.SOUTH) {
					winkel = winkel + 180;
				} else if (startDirection == Direction.WEST) {
					winkel = winkel + 90;
				} else {
				}
				winkel = winkel % 360;

				direction(winkel);
			}
			a++;
			while (robot.getLeftLight().groundChange()) {

			}

		}

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		robot.getPilot().rotate(360, true);
		return null;
	}
	
	public void direction(float winkel){
		if (winkel < 20) {
		} else if (winkel < 110) {
			Helper.drawString("WEST", 0, 0);
		} else if (winkel < 200) {
			Helper.drawString("SOUTH", 0, 1);
		} else if (winkel < 290) {
			Helper.drawString("EAST", 0, 2);
		} else {
			Helper.drawString("NORTH", 0, 3);
		}
		
	}

}
