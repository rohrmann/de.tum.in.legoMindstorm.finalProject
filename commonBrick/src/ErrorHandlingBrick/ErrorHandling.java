package ErrorHandlingBrick;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import misc.Direction;
import miscBrick.Helper;
import miscBrick.Robot;
import Graph.Pair;
import NavigationBrick.RoomNavigator;

public class ErrorHandling {

	private static boolean heading;
	private static boolean x;
	private static boolean active;
	private static int dirCount = 0;
	private static int xCount = 0;
	private static int yCount = 0;

	public static void resolvebyHand(Robot robot , final RoomNavigator navi) {
		robot.getPilot().stop();

		heading = true;
		x = false;
		active = true;

		Helper.drawText("Default: North, x:0, y:0. " +
						"Change with left and right ->Choose heading first.");

		Button.ENTER.addButtonListener(new ButtonListener() {

			@Override
			public void buttonReleased(Button arg0) {

			}

			@Override
			public void buttonPressed(Button arg0) {
				if (heading == true) {
					heading = false;
					x = true;
					String result = "";
					switch (dirCount) {
					case 0:
						result += "North chosen";
						navi.updateHeading(Direction.NORTH);
						break;
					case 1:
						result += "East chosen";
						navi.updateHeading(Direction.EAST);
						break;
					case 2:
						result += "South chosen";
						navi.updateHeading(Direction.SOUTH);
						break;
					case 3:
						result += "West chosen";
						navi.updateHeading(Direction.WEST);
						break;
					}
					result += " " + navi.getHeading().toString() + " ->Choose x-coordinate";
					Helper.drawText(result);
				} else if (x == true) {
					x = false;
					Helper.drawText("X chosen: " + xCount
							+ " ->Now choose y-coordinate");
				} else {
					Helper.drawText("Y chosen: " + yCount);
					navi.forcePosition(new Pair(xCount, yCount));
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
					}
					Helper.drawText(navi.getHeading().toString() + " " + navi.getPosition().toString());
					active = false;

				}
			}
		});

		Button.RIGHT.addButtonListener(new ButtonListener() {

			@Override
			public void buttonReleased(Button arg0) {

			}

			@Override
			public void buttonPressed(Button arg0) {
				if (heading == true) {
					dirCount = (dirCount + 1) % 4;
					switch (dirCount) {
					case 0:
						Helper.drawText("North");
						break;
					case 1:
						Helper.drawText("East");
						break;
					case 2:
						Helper.drawText("South");
						break;
					case 3:
						Helper.drawText("West");
						break;
					}
				} else if (x == true) {
					xCount++;
					Helper.drawText("X: " + xCount);
				} else {
					yCount++;
					Helper.drawText("Y: " + yCount);
				}
			}

		});

		Button.LEFT.addButtonListener(new ButtonListener() {

			@Override
			public void buttonReleased(Button arg0) {

			}

			@Override
			public void buttonPressed(Button arg0) {
				if (heading == true) {
					dirCount = dirCount -1;
					if (dirCount == -1) {
						dirCount = 3;
					}
					switch (dirCount) {
					case 0:
						Helper.drawText("North");
						break;
					case 1:
						Helper.drawText("East");
						break;
					case 2:
						Helper.drawText("South");
						break;
					case 3:
						Helper.drawText("West");
						break;
					}
				} else if (x == true) {
					xCount--;
					Helper.drawText("X: " + xCount);
				} else {
					yCount--;
					Helper.drawText("Y: " + yCount);
				}
			}

		});

		while (active) {
			;
		}

	}

}