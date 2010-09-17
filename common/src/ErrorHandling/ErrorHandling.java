package ErrorHandling;

import lejos.nxt.Button;
import lejos.robotics.subsumption.Behavior;
import misc.Helper;
import misc.Robot;


public class ErrorHandling implements Behavior {

	private Robot robot;
	boolean heading;
	boolean x;
	boolean y;
	boolean active;
	
	public ErrorHandling(){
		heading = true;
		x = false;
		y = false;
		active = false;
	}
	
	
	public ErrorHandling (Robot robot){
		this.robot = robot;
		heading = true;
		x = false;
		y = false;
		active = false;
	}
	
	@Override
	public void action() {
		robot.getPilot().stop();
		
		/*0x01 ENTER 
		0x02 LEFT
		0x04 RIGHT
		0x08 ESCAPE
		*/	
		
		while ((Button.readButtons() & 8) != 0){
			int dirCount = -1;
			Button.waitForPress();
			Helper.drawText("Press Right to choose dir and Enter to submit");
			while ((Button.readButtons() & 1) != 0){
				Button.waitForPress();
				if ((Button.readButtons() & 2) != 0){
					dirCount = (dirCount + 1)% 4;
					switch (dirCount){
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
				}
			}
			// update direction -> forceDir?
			switch (dirCount){
			case 0: 
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			}
			//enter the x-coordinate
			int xCount = -1;
			Button.waitForPress();
			Helper.drawText("Press Right to inc x and Enter to submit");
			while ((Button.readButtons() & 1) != 0){
				Button.waitForPress();
				if ((Button.readButtons() & 2) != 0){
					xCount ++;
					Helper.drawText("x: " + xCount);
					}
				}
			
			//enter the y-coordinate
			int yCount = -1;
			Button.waitForPress();
			Helper.drawText("Press Right to inc x and Enter to submit");
			while ((Button.readButtons() & 1) != 0){
				Button.waitForPress();
				if ((Button.readButtons() & 2) != 0){
					yCount ++;
					Helper.drawText("y: " + yCount);
				}
			}
			
			
			// update coordinated -> forcepos?
		}
			Button.waitForPress();
			
		}

	@Override
	public void suppress() {
		robot.getPilot().reset();
		robot.getPilot().stop();
		active = false;
	}

	@Override
	public boolean takeControl() {
		if ( Button.ESCAPE.isPressed() || active == true){
			active =true;
			return true;
		}
		
		return false;
	}

}
/*import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.robotics.subsumption.Behavior;
//import misc.Helper;
//import misc.Robot;


public class ErrorHandling implements Behavior {

	//private Robot robot;
	boolean heading;
	boolean x;
	boolean y;
	boolean active;
	int dirCount;
	int xCount; 
	int yCount;
	
	public ErrorHandling(){
		heading = true;
		x = false;
		y = false;
		active = false;
		dirCount = -1;
		xCount = -1;
		yCount = -1;
	}
	
	
	/*public ErrorHandling (Robot robot){
		this.robot = robot;
		heading = true;
		x = false;
		y = false;
		active = false;
		dirCount = -1;
		xCount = -1;
		yCount = -1;
	}*/
	
	@Override
	public void action() {
		//robot.getPilot().stop();
		
		/*0x01 ENTER 
		0x02 LEFT
		0x04 RIGHT
		0x08 ESCAPE
		*/	
		
		while(active){
			;
		}
		
	
		Button.ENTER.addButtonListener(new ButtonListener() {
			
			@Override
			public void buttonReleased(Button arg0) {
								
			}
			
			@Override
			public void buttonPressed(Button arg0) {
				if(heading == true){
					heading = false;
					x = true;
					switch (dirCount){
					case 0: 
						LCD.drawString("North   ", 0, 2);
						//Helper.drawText("North");
						break;
					case 1:
						LCD.drawString("East   ", 0, 2);
						//Helper.drawText("East");
						break;
					case 2:
						LCD.drawString("South  ", 0, 2);
						//Helper.drawText("South");
						break;
					case 3:
						LCD.drawString("West    ", 0, 2);
						//Helper.drawText("West");
						break;
					}
				}else if(x == true){
					x = false;
					y = true;
				}else{
					y = false;
					active = false;
					// update pos
					LCD.drawString("X: " + xCount + " Y: " + yCount, 0, 1);
				}
			}
		});
		
		/*while ((Button.waitForPress() & 8) != 0){
			int dirCount = -1;
			//Helper.drawText("Press Right to choose dir and Enter to submit");
			LCD.drawString("Dir", 0, 1);
			while ((Button.waitForPress() & 1) != 0){
				if ((Button.waitForPress() & 2) != 0){
					dirCount = (dirCount + 1)% 4;
					switch (dirCount){
					case 0: 
						LCD.drawString("North   ", 0, 2);
						//Helper.drawText("North");
						break;
					case 1:
						LCD.drawString("East   ", 0, 2);
						//Helper.drawText("East");
						break;
					case 2:
						LCD.drawString("South  ", 0, 2);
						//Helper.drawText("South");
						break;
					case 3:
						LCD.drawString("West    ", 0, 2);
						//Helper.drawText("West");
						break;
					}
				}
			}
			// update direction -> forceDir?
			switch (dirCount){
			case 0: 
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			}
			//enter the x-coordinate
			int xCount = -1;
			//Helper.drawText("Press Right to inc x and Enter to submit");
			while ((Button.waitForPress() & 1) != 0){
				
				if ((Button.waitForPress() & 2) != 0){
					xCount ++;
					//Helper.drawText("x: " + xCount);
					}
				}
			
			//enter the y-coordinate
			int yCount = -1;
			Button.waitForPress();
			//Helper.drawText("Press Right to inc x and Enter to submit");
			while ((Button.waitForPress() & 1) != 0){
				if ((Button.waitForPress() & 2) != 0){
					yCount ++;
					//Helper.drawText("y: " + yCount);
				}
			}
			// update coordinated -> forcepos?
		}
			Button.waitForPress();
			*/
		}

	@Override
	public void suppress() {
		//robot.getPilot().reset();
		//robot.getPilot().stop();
		active = false;
	}

	@Override
	public boolean takeControl() {
		if ( Button.ESCAPE.isPressed() || active == true){
			active = true;
			return true;
		}
		
		return false;
	}

}
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.robotics.subsumption.Behavior;
//import misc.Helper;
//import misc.Robot;


public class ErrorHandling implements Behavior {

	//private Robot robot;
	boolean heading;
	boolean x;
	boolean y;
	boolean active;
	int dirCount;
	int xCount; 
	int yCount;
	
	public ErrorHandling(){
		heading = true;
		x = false;
		y = false;
		active = false;
		dirCount = -1;
		xCount = -1;
		yCount = -1;
	}
	
	
	/*public ErrorHandling (Robot robot){
		this.robot = robot;
		heading = true;
		x = false;
		y = false;
		active = false;
		dirCount = -1;
		xCount = -1;
		yCount = -1;
	}*/
	
	@Override
	public void action() {
		//robot.getPilot().stop();
		
		/*0x01 ENTER 
		0x02 LEFT
		0x04 RIGHT
		0x08 ESCAPE
		*/	
		
		while(active){
			;
		}
		
	
		Button.ENTER.addButtonListener(new ButtonListener() {
			
			@Override
			public void buttonReleased(Button arg0) {
								
			}
			
			@Override
			public void buttonPressed(Button arg0) {
				if(heading == true){
					heading = false;
					x = true;
					switch (dirCount){
					case 0: 
						LCD.drawString("North   ", 0, 2);
						//Helper.drawText("North");
						break;
					case 1:
						LCD.drawString("East   ", 0, 2);
						//Helper.drawText("East");
						break;
					case 2:
						LCD.drawString("South  ", 0, 2);
						//Helper.drawText("South");
						break;
					case 3:
						LCD.drawString("West    ", 0, 2);
						//Helper.drawText("West");
						break;
					}
				}else if(x == true){
					x = false;
					y = true;
				}else{
					y = false;
					active = false;
					// update pos
					LCD.drawString("X: " + xCount + " Y: " + yCount, 0, 1);
				}
			}
		});
		
		/*while ((Button.waitForPress() & 8) != 0){
			int dirCount = -1;
			//Helper.drawText("Press Right to choose dir and Enter to submit");
			LCD.drawString("Dir", 0, 1);
			while ((Button.waitForPress() & 1) != 0){
				if ((Button.waitForPress() & 2) != 0){
					dirCount = (dirCount + 1)% 4;
					switch (dirCount){
					case 0: 
						LCD.drawString("North   ", 0, 2);
						//Helper.drawText("North");
						break;
					case 1:
						LCD.drawString("East   ", 0, 2);
						//Helper.drawText("East");
						break;
					case 2:
						LCD.drawString("South  ", 0, 2);
						//Helper.drawText("South");
						break;
					case 3:
						LCD.drawString("West    ", 0, 2);
						//Helper.drawText("West");
						break;
					}
				}
			}
			// update direction -> forceDir?
			switch (dirCount){
			case 0: 
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			}
			//enter the x-coordinate
			int xCount = -1;
			//Helper.drawText("Press Right to inc x and Enter to submit");
			while ((Button.waitForPress() & 1) != 0){
				
				if ((Button.waitForPress() & 2) != 0){
					xCount ++;
					//Helper.drawText("x: " + xCount);
					}
				}
			
			//enter the y-coordinate
			int yCount = -1;
			Button.waitForPress();
			//Helper.drawText("Press Right to inc x and Enter to submit");
			while ((Button.waitForPress() & 1) != 0){
				if ((Button.waitForPress() & 2) != 0){
					yCount ++;
					//Helper.drawText("y: " + yCount);
				}
			}
			// update coordinated -> forcepos?
		}
			Button.waitForPress();
			*/
		}

	@Override
	public void suppress() {
		//robot.getPilot().reset();
		//robot.getPilot().stop();
		active = false;
	}

	@Override
	public boolean takeControl() {
		if ( Button.ESCAPE.isPressed() || active == true){
			active = true;
			return true;
		}
		
		return false;
	}

}
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.robotics.subsumption.Behavior;
//import misc.Helper;
//import misc.Robot;


public class ErrorHandling implements Behavior {

	//private Robot robot;
	boolean heading;
	boolean x;
	boolean y;
	boolean active;
	int dirCount;
	int xCount; 
	int yCount;
	
	public ErrorHandling(){
		heading = true;
		x = false;
		y = false;
		active = false;
		dirCount = -1;
		xCount = -1;
		yCount = -1;
	}
	
	
	/*public ErrorHandling (Robot robot){
		this.robot = robot;
		heading = true;
		x = false;
		y = false;
		active = false;
		dirCount = -1;
		xCount = -1;
		yCount = -1;
	}*/
	
	@Override
	public void action() {
		//robot.getPilot().stop();
		
		/*0x01 ENTER 
		0x02 LEFT
		0x04 RIGHT
		0x08 ESCAPE
		*/	
		
		while(active){
			;
		}
		
	
		Button.ENTER.addButtonListener(new ButtonListener() {
			
			@Override
			public void buttonReleased(Button arg0) {
								
			}
			
			@Override
			public void buttonPressed(Button arg0) {
				if(heading == true){
					heading = false;
					x = true;
					switch (dirCount){
					case 0: 
						LCD.drawString("North   ", 0, 2);
						//Helper.drawText("North");
						break;
					case 1:
						LCD.drawString("East   ", 0, 2);
						//Helper.drawText("East");
						break;
					case 2:
						LCD.drawString("South  ", 0, 2);
						//Helper.drawText("South");
						break;
					case 3:
						LCD.drawString("West    ", 0, 2);
						//Helper.drawText("West");
						break;
					}
				}else if(x == true){
					x = false;
					y = true;
				}else{
					y = false;
					active = false;
					// update pos
					LCD.drawString("X: " + xCount + " Y: " + yCount, 0, 1);
				}
			}
		});
		
		/*while ((Button.waitForPress() & 8) != 0){
			int dirCount = -1;
			//Helper.drawText("Press Right to choose dir and Enter to submit");
			LCD.drawString("Dir", 0, 1);
			while ((Button.waitForPress() & 1) != 0){
				if ((Button.waitForPress() & 2) != 0){
					dirCount = (dirCount + 1)% 4;
					switch (dirCount){
					case 0: 
						LCD.drawString("North   ", 0, 2);
						//Helper.drawText("North");
						break;
					case 1:
						LCD.drawString("East   ", 0, 2);
						//Helper.drawText("East");
						break;
					case 2:
						LCD.drawString("South  ", 0, 2);
						//Helper.drawText("South");
						break;
					case 3:
						LCD.drawString("West    ", 0, 2);
						//Helper.drawText("West");
						break;
					}
				}
			}
			// update direction -> forceDir?
			switch (dirCount){
			case 0: 
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			}
			//enter the x-coordinate
			int xCount = -1;
			//Helper.drawText("Press Right to inc x and Enter to submit");
			while ((Button.waitForPress() & 1) != 0){
				
				if ((Button.waitForPress() & 2) != 0){
					xCount ++;
					//Helper.drawText("x: " + xCount);
					}
				}
			
			//enter the y-coordinate
			int yCount = -1;
			Button.waitForPress();
			//Helper.drawText("Press Right to inc x and Enter to submit");
			while ((Button.waitForPress() & 1) != 0){
				if ((Button.waitForPress() & 2) != 0){
					yCount ++;
					//Helper.drawText("y: " + yCount);
				}
			}
			// update coordinated -> forcepos?
		}
			Button.waitForPress();
			*/
		}

	@Override
	public void suppress() {
		//robot.getPilot().reset();
		//robot.getPilot().stop();
		active = false;
	}

	@Override
	public boolean takeControl() {
		if ( Button.ESCAPE.isPressed() || active == true){
			active = true;
			return true;
		}
		
		return false;
	}

}
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.robotics.subsumption.Behavior;
//import misc.Helper;
//import misc.Robot;


public class ErrorHandling implements Behavior {

	//private Robot robot;
	boolean heading;
	boolean x;
	boolean y;
	boolean active;
	int dirCount;
	int xCount; 
	int yCount;
	
	public ErrorHandling(){
		heading = true;
		x = false;
		y = false;
		active = false;
		dirCount = -1;
		xCount = -1;
		yCount = -1;
	}
	
	
	/*public ErrorHandling (Robot robot){
		this.robot = robot;
		heading = true;
		x = false;
		y = false;
		active = false;
		dirCount = -1;
		xCount = -1;
		yCount = -1;
	}*/
	
	@Override
	public void action() {
		//robot.getPilot().stop();
		
		/*0x01 ENTER 
		0x02 LEFT
		0x04 RIGHT
		0x08 ESCAPE
		*/	
		
		while(active){
			;
		}
		
	
		Button.ENTER.addButtonListener(new ButtonListener() {
			
			@Override
			public void buttonReleased(Button arg0) {
								
			}
			
			@Override
			public void buttonPressed(Button arg0) {
				if(heading == true){
					heading = false;
					x = true;
					switch (dirCount){
					case 0: 
						LCD.drawString("North   ", 0, 2);
						//Helper.drawText("North");
						break;
					case 1:
						LCD.drawString("East   ", 0, 2);
						//Helper.drawText("East");
						break;
					case 2:
						LCD.drawString("South  ", 0, 2);
						//Helper.drawText("South");
						break;
					case 3:
						LCD.drawString("West    ", 0, 2);
						//Helper.drawText("West");
						break;
					}
				}else if(x == true){
					x = false;
					y = true;
				}else{
					y = false;
					active = false;
					// update pos
					LCD.drawString("X: " + xCount + " Y: " + yCount, 0, 1);
				}
			}
		});
		
		/*while ((Button.waitForPress() & 8) != 0){
			int dirCount = -1;
			//Helper.drawText("Press Right to choose dir and Enter to submit");
			LCD.drawString("Dir", 0, 1);
			while ((Button.waitForPress() & 1) != 0){
				if ((Button.waitForPress() & 2) != 0){
					dirCount = (dirCount + 1)% 4;
					switch (dirCount){
					case 0: 
						LCD.drawString("North   ", 0, 2);
						//Helper.drawText("North");
						break;
					case 1:
						LCD.drawString("East   ", 0, 2);
						//Helper.drawText("East");
						break;
					case 2:
						LCD.drawString("South  ", 0, 2);
						//Helper.drawText("South");
						break;
					case 3:
						LCD.drawString("West    ", 0, 2);
						//Helper.drawText("West");
						break;
					}
				}
			}
			// update direction -> forceDir?
			switch (dirCount){
			case 0: 
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			}
			//enter the x-coordinate
			int xCount = -1;
			//Helper.drawText("Press Right to inc x and Enter to submit");
			while ((Button.waitForPress() & 1) != 0){
				
				if ((Button.waitForPress() & 2) != 0){
					xCount ++;
					//Helper.drawText("x: " + xCount);
					}
				}
			
			//enter the y-coordinate
			int yCount = -1;
			Button.waitForPress();
			//Helper.drawText("Press Right to inc x and Enter to submit");
			while ((Button.waitForPress() & 1) != 0){
				if ((Button.waitForPress() & 2) != 0){
					yCount ++;
					//Helper.drawText("y: " + yCount);
				}
			}
			// update coordinated -> forcepos?
		}
			Button.waitForPress();
			*/
		}

	@Override
	public void suppress() {
		//robot.getPilot().reset();
		//robot.getPilot().stop();
		active = false;
	}

	@Override
	public boolean takeControl() {
		if ( Button.ESCAPE.isPressed() || active == true){
			active = true;
			return true;
		}
		
		return false;
	}

}
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.robotics.subsumption.Behavior;
//import misc.Helper;
//import misc.Robot;


public class ErrorHandling implements Behavior {

	//private Robot robot;
	boolean heading;
	boolean x;
	boolean y;
	boolean active;
	int dirCount;
	int xCount; 
	int yCount;
	
	public ErrorHandling(){
		heading = true;
		x = false;
		y = false;
		active = false;
		dirCount = -1;
		xCount = -1;
		yCount = -1;
	}
	
	
	/*public ErrorHandling (Robot robot){
		this.robot = robot;
		heading = true;
		x = false;
		y = false;
		active = false;
		dirCount = -1;
		xCount = -1;
		yCount = -1;
	}*/
	
	@Override
	public void action() {
		//robot.getPilot().stop();
		
		/*0x01 ENTER 
		0x02 LEFT
		0x04 RIGHT
		0x08 ESCAPE
		*/	
		
		while(active){
			;
		}
		
	
		Button.ENTER.addButtonListener(new ButtonListener() {
			
			@Override
			public void buttonReleased(Button arg0) {
								
			}
			
			@Override
			public void buttonPressed(Button arg0) {
				if(heading == true){
					heading = false;
					x = true;
					switch (dirCount){
					case 0: 
						LCD.drawString("North   ", 0, 2);
						//Helper.drawText("North");
						break;
					case 1:
						LCD.drawString("East   ", 0, 2);
						//Helper.drawText("East");
						break;
					case 2:
						LCD.drawString("South  ", 0, 2);
						//Helper.drawText("South");
						break;
					case 3:
						LCD.drawString("West    ", 0, 2);
						//Helper.drawText("West");
						break;
					}
				}else if(x == true){
					x = false;
					y = true;
				}else{
					y = false;
					active = false;
					// update pos
					LCD.drawString("X: " + xCount + " Y: " + yCount, 0, 1);
				}
			}
		});
		
		/*while ((Button.waitForPress() & 8) != 0){
			int dirCount = -1;
			//Helper.drawText("Press Right to choose dir and Enter to submit");
			LCD.drawString("Dir", 0, 1);
			while ((Button.waitForPress() & 1) != 0){
				if ((Button.waitForPress() & 2) != 0){
					dirCount = (dirCount + 1)% 4;
					switch (dirCount){
					case 0: 
						LCD.drawString("North   ", 0, 2);
						//Helper.drawText("North");
						break;
					case 1:
						LCD.drawString("East   ", 0, 2);
						//Helper.drawText("East");
						break;
					case 2:
						LCD.drawString("South  ", 0, 2);
						//Helper.drawText("South");
						break;
					case 3:
						LCD.drawString("West    ", 0, 2);
						//Helper.drawText("West");
						break;
					}
				}
			}
			// update direction -> forceDir?
			switch (dirCount){
			case 0: 
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			}
			//enter the x-coordinate
			int xCount = -1;
			//Helper.drawText("Press Right to inc x and Enter to submit");
			while ((Button.waitForPress() & 1) != 0){
				
				if ((Button.waitForPress() & 2) != 0){
					xCount ++;
					//Helper.drawText("x: " + xCount);
					}
				}
			
			//enter the y-coordinate
			int yCount = -1;
			Button.waitForPress();
			//Helper.drawText("Press Right to inc x and Enter to submit");
			while ((Button.waitForPress() & 1) != 0){
				if ((Button.waitForPress() & 2) != 0){
					yCount ++;
					//Helper.drawText("y: " + yCount);
				}
			}
			// update coordinated -> forcepos?
		}
			Button.waitForPress();
			*/
		}

	@Override
	public void suppress() {
		//robot.getPilot().reset();
		//robot.getPilot().stop();
		active = false;
	}

	@Override
	public boolean takeControl() {
		if ( Button.ESCAPE.isPressed() || active == true){
			active = true;
			return true;
		}
		
		return false;
	}

}
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.robotics.subsumption.Behavior;
//import misc.Helper;
//import misc.Robot;


public class ErrorHandling implements Behavior {

	//private Robot robot;
	boolean heading;
	boolean x;
	boolean y;
	boolean active;
	int dirCount;
	int xCount; 
	int yCount;
	
	public ErrorHandling(){
		heading = true;
		x = false;
		y = false;
		active = false;
		dirCount = -1;
		xCount = -1;
		yCount = -1;
	}
	
	
	/*public ErrorHandling (Robot robot){
		this.robot = robot;
		heading = true;
		x = false;
		y = false;
		active = false;
		dirCount = -1;
		xCount = -1;
		yCount = -1;
	}*/
	
	@Override
	public void action() {
		//robot.getPilot().stop();
		
		/*0x01 ENTER 
		0x02 LEFT
		0x04 RIGHT
		0x08 ESCAPE
		*/	
		
		while(active){
			;
		}
		
	
		Button.ENTER.addButtonListener(new ButtonListener() {
			
			@Override
			public void buttonReleased(Button arg0) {
								
			}
			
			@Override
			public void buttonPressed(Button arg0) {
				if(heading == true){
					heading = false;
					x = true;
					switch (dirCount){
					case 0: 
						LCD.drawString("North   ", 0, 2);
						//Helper.drawText("North");
						break;
					case 1:
						LCD.drawString("East   ", 0, 2);
						//Helper.drawText("East");
						break;
					case 2:
						LCD.drawString("South  ", 0, 2);
						//Helper.drawText("South");
						break;
					case 3:
						LCD.drawString("West    ", 0, 2);
						//Helper.drawText("West");
						break;
					}
				}else if(x == true){
					x = false;
					y = true;
				}else{
					y = false;
					active = false;
					// update pos
					LCD.drawString("X: " + xCount + " Y: " + yCount, 0, 1);
				}
			}
		});
		
		/*while ((Button.waitForPress() & 8) != 0){
			int dirCount = -1;
			//Helper.drawText("Press Right to choose dir and Enter to submit");
			LCD.drawString("Dir", 0, 1);
			while ((Button.waitForPress() & 1) != 0){
				if ((Button.waitForPress() & 2) != 0){
					dirCount = (dirCount + 1)% 4;
					switch (dirCount){
					case 0: 
						LCD.drawString("North   ", 0, 2);
						//Helper.drawText("North");
						break;
					case 1:
						LCD.drawString("East   ", 0, 2);
						//Helper.drawText("East");
						break;
					case 2:
						LCD.drawString("South  ", 0, 2);
						//Helper.drawText("South");
						break;
					case 3:
						LCD.drawString("West    ", 0, 2);
						//Helper.drawText("West");
						break;
					}
				}
			}
			// update direction -> forceDir?
			switch (dirCount){
			case 0: 
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			}
			//enter the x-coordinate
			int xCount = -1;
			//Helper.drawText("Press Right to inc x and Enter to submit");
			while ((Button.waitForPress() & 1) != 0){
				
				if ((Button.waitForPress() & 2) != 0){
					xCount ++;
					//Helper.drawText("x: " + xCount);
					}
				}
			
			//enter the y-coordinate
			int yCount = -1;
			Button.waitForPress();
			//Helper.drawText("Press Right to inc x and Enter to submit");
			while ((Button.waitForPress() & 1) != 0){
				if ((Button.waitForPress() & 2) != 0){
					yCount ++;
					//Helper.drawText("y: " + yCount);
				}
			}
			// update coordinated -> forcepos?
		}
			Button.waitForPress();
			*/
		}

	@Override
	public void suppress() {
		//robot.getPilot().reset();
		//robot.getPilot().stop();
		active = false;
	}

	@Override
	public boolean takeControl() {
		if ( Button.ESCAPE.isPressed() || active == true){
			active = true;
			return true;
		}
		
		return false;
	}

}
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.robotics.subsumption.Behavior;
//import misc.Helper;
//import misc.Robot;


public class ErrorHandling implements Behavior {

	//private Robot robot;
	boolean heading;
	boolean x;
	boolean y;
	boolean active;
	int dirCount;
	int xCount; 
	int yCount;
	
	public ErrorHandling(){
		heading = true;
		x = false;
		y = false;
		active = false;
		dirCount = -1;
		xCount = -1;
		yCount = -1;
	}
	
	
	/*public ErrorHandling (Robot robot){
		this.robot = robot;
		heading = true;
		x = false;
		y = false;
		active = false;
		dirCount = -1;
		xCount = -1;
		yCount = -1;
	}*/
	
	@Override
	public void action() {
		//robot.getPilot().stop();
		
		/*0x01 ENTER 
		0x02 LEFT
		0x04 RIGHT
		0x08 ESCAPE
		*/	
		
		while(active){
			;
		}
		
	
		Button.ENTER.addButtonListener(new ButtonListener() {
			
			@Override
			public void buttonReleased(Button arg0) {
								
			}
			
			@Override
			public void buttonPressed(Button arg0) {
				if(heading == true){
					heading = false;
					x = true;
					switch (dirCount){
					case 0: 
						LCD.drawString("North   ", 0, 2);
						//Helper.drawText("North");
						break;
					case 1:
						LCD.drawString("East   ", 0, 2);
						//Helper.drawText("East");
						break;
					case 2:
						LCD.drawString("South  ", 0, 2);
						//Helper.drawText("South");
						break;
					case 3:
						LCD.drawString("West    ", 0, 2);
						//Helper.drawText("West");
						break;
					}
				}else if(x == true){
					x = false;
					y = true;
				}else{
					y = false;
					active = false;
					// update pos
					LCD.drawString("X: " + xCount + " Y: " + yCount, 0, 1);
				}
			}
		});
		
		/*while ((Button.waitForPress() & 8) != 0){
			int dirCount = -1;
			//Helper.drawText("Press Right to choose dir and Enter to submit");
			LCD.drawString("Dir", 0, 1);
			while ((Button.waitForPress() & 1) != 0){
				if ((Button.waitForPress() & 2) != 0){
					dirCount = (dirCount + 1)% 4;
					switch (dirCount){
					case 0: 
						LCD.drawString("North   ", 0, 2);
						//Helper.drawText("North");
						break;
					case 1:
						LCD.drawString("East   ", 0, 2);
						//Helper.drawText("East");
						break;
					case 2:
						LCD.drawString("South  ", 0, 2);
						//Helper.drawText("South");
						break;
					case 3:
						LCD.drawString("West    ", 0, 2);
						//Helper.drawText("West");
						break;
					}
				}
			}
			// update direction -> forceDir?
			switch (dirCount){
			case 0: 
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			}
			//enter the x-coordinate
			int xCount = -1;
			//Helper.drawText("Press Right to inc x and Enter to submit");
			while ((Button.waitForPress() & 1) != 0){
				
				if ((Button.waitForPress() & 2) != 0){
					xCount ++;
					//Helper.drawText("x: " + xCount);
					}
				}
			
			//enter the y-coordinate
			int yCount = -1;
			Button.waitForPress();
			//Helper.drawText("Press Right to inc x and Enter to submit");
			while ((Button.waitForPress() & 1) != 0){
				if ((Button.waitForPress() & 2) != 0){
					yCount ++;
					//Helper.drawText("y: " + yCount);
				}
			}
			// update coordinated -> forcepos?
		}
			Button.waitForPress();
			*/
		}

	@Override
	public void suppress() {
		//robot.getPilot().reset();
		//robot.getPilot().stop();
		active = false;
	}

	@Override
	public boolean takeControl() {
		if ( Button.ESCAPE.isPressed() || active == true){
			active = true;
			return true;
		}
		
		return false;
	}

}
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.robotics.subsumption.Behavior;
//import misc.Helper;
//import misc.Robot;


public class ErrorHandling implements Behavior {

	//private Robot robot;
	boolean heading;
	boolean x;
	boolean y;
	boolean active;
	int dirCount;
	int xCount; 
	int yCount;
	
	public ErrorHandling(){
		heading = true;
		x = false;
		y = false;
		active = false;
		dirCount = -1;
		xCount = -1;
		yCount = -1;
	}
	
	
	/*public ErrorHandling (Robot robot){
		this.robot = robot;
		heading = true;
		x = false;
		y = false;
		active = false;
		dirCount = -1;
		xCount = -1;
		yCount = -1;
	}*/
	
	@Override
	public void action() {
		//robot.getPilot().stop();
		
		/*0x01 ENTER 
		0x02 LEFT
		0x04 RIGHT
		0x08 ESCAPE
		*/	
		
		while(active){
			;
		}
		
	
		Button.ENTER.addButtonListener(new ButtonListener() {
			
			@Override
			public void buttonReleased(Button arg0) {
								
			}
			
			@Override
			public void buttonPressed(Button arg0) {
				if(heading == true){
					heading = false;
					x = true;
					switch (dirCount){
					case 0: 
						LCD.drawString("North   ", 0, 2);
						//Helper.drawText("North");
						break;
					case 1:
						LCD.drawString("East   ", 0, 2);
						//Helper.drawText("East");
						break;
					case 2:
						LCD.drawString("South  ", 0, 2);
						//Helper.drawText("South");
						break;
					case 3:
						LCD.drawString("West    ", 0, 2);
						//Helper.drawText("West");
						break;
					}
				}else if(x == true){
					x = false;
					y = true;
				}else{
					y = false;
					active = false;
					// update pos
					LCD.drawString("X: " + xCount + " Y: " + yCount, 0, 1);
				}
			}
		});
		
		/*while ((Button.waitForPress() & 8) != 0){
			int dirCount = -1;
			//Helper.drawText("Press Right to choose dir and Enter to submit");
			LCD.drawString("Dir", 0, 1);
			while ((Button.waitForPress() & 1) != 0){
				if ((Button.waitForPress() & 2) != 0){
					dirCount = (dirCount + 1)% 4;
					switch (dirCount){
					case 0: 
						LCD.drawString("North   ", 0, 2);
						//Helper.drawText("North");
						break;
					case 1:
						LCD.drawString("East   ", 0, 2);
						//Helper.drawText("East");
						break;
					case 2:
						LCD.drawString("South  ", 0, 2);
						//Helper.drawText("South");
						break;
					case 3:
						LCD.drawString("West    ", 0, 2);
						//Helper.drawText("West");
						break;
					}
				}
			}
			// update direction -> forceDir?
			switch (dirCount){
			case 0: 
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			}
			//enter the x-coordinate
			int xCount = -1;
			//Helper.drawText("Press Right to inc x and Enter to submit");
			while ((Button.waitForPress() & 1) != 0){
				
				if ((Button.waitForPress() & 2) != 0){
					xCount ++;
					//Helper.drawText("x: " + xCount);
					}
				}
			
			//enter the y-coordinate
			int yCount = -1;
			Button.waitForPress();
			//Helper.drawText("Press Right to inc x and Enter to submit");
			while ((Button.waitForPress() & 1) != 0){
				if ((Button.waitForPress() & 2) != 0){
					yCount ++;
					//Helper.drawText("y: " + yCount);
				}
			}
			// update coordinated -> forcepos?
		}
			Button.waitForPress();
			*/
		}

	@Override
	public void suppress() {
		//robot.getPilot().reset();
		//robot.getPilot().stop();
		active = false;
	}

	@Override
	public boolean takeControl() {
		if ( Button.ESCAPE.isPressed() || active == true){
			active = true;
			return true;
		}
		
		return false;
	}

}
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.robotics.subsumption.Behavior;
//import misc.Helper;
//import misc.Robot;


public class ErrorHandling implements Behavior {

	//private Robot robot;
	boolean heading;
	boolean x;
	boolean y;
	boolean active;
	int dirCount;
	int xCount; 
	int yCount;
	
	public ErrorHandling(){
		heading = true;
		x = false;
		y = false;
		active = false;
		dirCount = -1;
		xCount = -1;
		yCount = -1;
	}
	
	
	/*public ErrorHandling (Robot robot){
		this.robot = robot;
		heading = true;
		x = false;
		y = false;
		active = false;
		dirCount = -1;
		xCount = -1;
		yCount = -1;
	}*/
	
	@Override
	public void action() {
		//robot.getPilot().stop();
		
		/*0x01 ENTER 
		0x02 LEFT
		0x04 RIGHT
		0x08 ESCAPE
		*/	
		
		while(active){
			;
		}
		
	
		Button.ENTER.addButtonListener(new ButtonListener() {
			
			@Override
			public void buttonReleased(Button arg0) {
								
			}
			
			@Override
			public void buttonPressed(Button arg0) {
				if(heading == true){
					heading = false;
					x = true;
					switch (dirCount){
					case 0: 
						LCD.drawString("North   ", 0, 2);
						//Helper.drawText("North");
						break;
					case 1:
						LCD.drawString("East   ", 0, 2);
						//Helper.drawText("East");
						break;
					case 2:
						LCD.drawString("South  ", 0, 2);
						//Helper.drawText("South");
						break;
					case 3:
						LCD.drawString("West    ", 0, 2);
						//Helper.drawText("West");
						break;
					}
				}else if(x == true){
					x = false;
					y = true;
				}else{
					y = false;
					active = false;
					// update pos
					LCD.drawString("X: " + xCount + " Y: " + yCount, 0, 1);
				}
			}
		});
		
		/*while ((Button.waitForPress() & 8) != 0){
			int dirCount = -1;
			//Helper.drawText("Press Right to choose dir and Enter to submit");
			LCD.drawString("Dir", 0, 1);
			while ((Button.waitForPress() & 1) != 0){
				if ((Button.waitForPress() & 2) != 0){
					dirCount = (dirCount + 1)% 4;
					switch (dirCount){
					case 0: 
						LCD.drawString("North   ", 0, 2);
						//Helper.drawText("North");
						break;
					case 1:
						LCD.drawString("East   ", 0, 2);
						//Helper.drawText("East");
						break;
					case 2:
						LCD.drawString("South  ", 0, 2);
						//Helper.drawText("South");
						break;
					case 3:
						LCD.drawString("West    ", 0, 2);
						//Helper.drawText("West");
						break;
					}
				}
			}
			// update direction -> forceDir?
			switch (dirCount){
			case 0: 
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			}
			//enter the x-coordinate
			int xCount = -1;
			//Helper.drawText("Press Right to inc x and Enter to submit");
			while ((Button.waitForPress() & 1) != 0){
				
				if ((Button.waitForPress() & 2) != 0){
					xCount ++;
					//Helper.drawText("x: " + xCount);
					}
				}
			
			//enter the y-coordinate
			int yCount = -1;
			Button.waitForPress();
			//Helper.drawText("Press Right to inc x and Enter to submit");
			while ((Button.waitForPress() & 1) != 0){
				if ((Button.waitForPress() & 2) != 0){
					yCount ++;
					//Helper.drawText("y: " + yCount);
				}
			}
			// update coordinated -> forcepos?
		}
			Button.waitForPress();
			*/
		}

	@Override
	public void suppress() {
		//robot.getPilot().reset();
		//robot.getPilot().stop();
		active = false;
	}

	@Override
	public boolean takeControl() {
		if ( Button.ESCAPE.isPressed() || active == true){
			active = true;
			return true;
		}
		
		return false;
	}

}
