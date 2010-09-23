package NavigationBrick;

import Color.Color;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import miscBrick.Config;
import miscBrick.Helper;
import miscBrick.Robot;

/**
 * 
 * @author rohrmann
 *
 */
public class RoomMissed {

	public static Color action(Robot robot, int interval) {
		Sound.twoBeeps();
		Helper.drawString("RoomMissed started", 0, 5);
		Color color = Color.UNKNOWN;
		float distanceTravelled = robot.getPilot().getTravelDistance();

		while (interval < 15) {

			// drive backward first -> every time a little bit more
			while (distanceTravelled - interval <= robot.getPilot()
					.getTravelDistance()) {
				robot.getPilot().backward();
			}

			robot.getPilot().stop();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}

			// find line again
			while (robot.getPilot().getTravelDistance() < Config.roomDistance
					+ Config.roomDistanceTolerance - 5) {
				if (robot.getLeftLight().groundChange()) {
					Motor.B.forward();
					Motor.A.stop();
				} else {
					Motor.A.forward();
					Motor.B.stop();
				}
			}

			long startTime;
			Color lastColor = Color.UNKNOWN;
			long startColor = System.currentTimeMillis();
			startTime = System.currentTimeMillis();
			lastColor = color;
			color = robot.getColor().getColorName();

			while (robot.getPilot().getTravelDistance() < Config.roomDistance
					+ Config.roomDistanceTolerance) {
				robot.getPilot().forward();

				startTime = System.currentTimeMillis();
				lastColor = color;
				color = robot.getColor().getColorName();
				Helper.drawText(color.toString());

				if (color != lastColor) {
					startColor = System.currentTimeMillis();
				} else if (color == lastColor
						&& color.isRoomColor()
						&& System.currentTimeMillis() - startColor > Config.acceptionPeriodForColor) {
					return color;
				}
				try {
					Thread.sleep(Config.roomMissedPollingInterval
							- (System.currentTimeMillis() - startTime));
				} catch (InterruptedException ex) {
				}
			}

			// decrement the interval for the next step to be smaller, update
			// distance travelled
			interval += 1;
			distanceTravelled = robot.getPilot().getTravelDistance();
		}
		return null;
	}

}
