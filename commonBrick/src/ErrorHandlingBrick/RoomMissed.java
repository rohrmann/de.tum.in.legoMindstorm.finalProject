package ErrorHandlingBrick;

import Color.Color;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import miscBrick.Helper;
import miscBrick.Robot;
import miscBrick.RoomInformation;

public class RoomMissed {

	private static final int roomDistanceTolerance = 3;
	private static final int pollingInterval = 10;
	private static final int acceptionPeriodForColor = 150;

	public static Color action(Robot robot, int interval,
			RoomInformation information) {
		Sound.twoBeeps();
		Helper.drawString("RoomMissed started", 0, 5);
		Color color = Color.UNKNOWN;

		while (interval < 15) {

			// drive back first -> every time a little bit more
			robot.getPilot().reset();
			//robot.getPilot().setMoveSpeed(10);
			// rotate and find line
			robot.getPilot().stop();
			robot.getPilot().rotate(180);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
			robot.getPilot().stop();

			// go forward until distance traveled is bigger than interval
			while (robot.getPilot().getTravelDistance() <= interval + roomDistanceTolerance) {
				if (robot.getLeftLight().groundChange()
						|| robot.getRightLight().groundChange()) {
					if (robot.getLeftLight().groundChange()) {
						Motor.B.forward();
						Motor.A.stop();
					} else {
						Motor.A.forward();
						Motor.B.stop();
					}
				} else {
					robot.getPilot().forward();
				}

			}

			// rotate and find line again
			robot.getPilot().reset();
			robot.getPilot().stop();
			robot.getPilot().rotate(180);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
			
			robot.getPilot().stop();
			
			// go forward until distance to go is 5
			while (robot.getPilot().getTravelDistance() <= interval - 5) {
				if (robot.getLeftLight().groundChange()
						|| robot.getRightLight().groundChange()) {
					if (robot.getLeftLight().groundChange()) {
						Motor.B.forward();
						Motor.A.stop();
					} else {
						Motor.A.forward();
						Motor.B.stop();
					}
				} else {
					robot.getPilot().forward();
				}
			}

			//robot.getPilot().setMoveSpeed(5);
			long startTime;
			Color lastColor = Color.UNKNOWN;
			long startColor = System.currentTimeMillis();
			startTime = System.currentTimeMillis();
			lastColor = color;
			color = robot.getColor().getColorName();

			while (robot.getPilot().getTravelDistance() < interval
					+ roomDistanceTolerance) {
				robot.getPilot().forward();

				startTime = System.currentTimeMillis();
				lastColor = color;
				color = robot.getColor().getColorName();
				Helper.drawText(color.toString());

				if (color != lastColor) {
					startColor = System.currentTimeMillis();
				} else if (color == lastColor
						&& color.isRoomColor()
						&& System.currentTimeMillis() - startColor > acceptionPeriodForColor) {
					return color;
				}
				try {
					Thread.sleep(pollingInterval
							- (System.currentTimeMillis() - startTime));
				} catch (InterruptedException ex) {
				}
			}

			// decrement the interval for the next step to be smaller, update
			// distance travelled
			interval += 1;

		}
		return null;
	}

}
