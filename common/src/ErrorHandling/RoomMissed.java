package ErrorHandling;

import Color.Color;
import Navigation.DriveForward;
import Navigation.FollowLine;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import misc.Helper;
import misc.Robot;
import misc.RoomInformation;

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
			// rotate and find line
			Behavior turn = new Turn(robot, 180);
			Behavior findLine = new FindLine(robot);
			Behavior[] turnAndFindLine = new Behavior[] { turn, findLine };
			Arbitrator a = new Arbitrator(turnAndFindLine, true);
			a.start();

			// go forward until distance travelled is bigger than interval
			while (robot.getPilot().getTravelDistance() <= interval) {
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

			robot.getPilot().reset();

			// rotate and find line again
			a = new Arbitrator(turnAndFindLine, true);
			a.start();

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
