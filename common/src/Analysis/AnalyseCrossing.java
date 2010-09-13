package Analysis;

import java.util.ArrayList;
import java.util.List;

import Light.LightSettings;

import lejos.robotics.navigation.TachoPilot;
import misc.Direction;
import misc.Robot;

public class AnalyseCrossing {

	private LightSettings leftLightSettings;
	private TachoPilot pilot;

	public AnalyseCrossing(Robot robot) {
		pilot = robot.getPilot();
		leftLightSettings = robot.getLeftLight();
		
	}

	public List<Direction> analyseCrossing(Direction heading) {
		List<Direction> directionList = new ArrayList<Direction>();
		boolean leftGround = true;
		boolean leftLine =false;
		
		pilot.rotate(360, true);
		
		while (leftLightSettings.groundChange()) 
			;
		
		pilot.stop();
		pilot.reset();
		
		pilot.rotate(360,true);
		
		while(pilot.isMoving()){
			if(!leftLightSettings.groundChange()){
				leftGround = true;
				
				if(leftLine){
					leftLine = false;
					directionList.add(getDirection(heading,pilot.getAngle()));
				}
			}
			
			else if(leftGround && leftLightSettings.groundChange()){
				leftGround = false;
				leftLine = true;
			}
		}

		pilot.rotate(360, true);

		while (leftLightSettings.groundChange()) 
			;

		pilot.stop();
		pilot.reset();
		
		return directionList;
	}
	
	public Direction getDirection(Direction heading, float angle){
		Direction result = Direction.UNDEFINED;
		if(heading == Direction.WEST){
			angle += 90;
		}
		else if(heading == Direction.SOUTH){
			angle += 180;
		}
		else if(heading == Direction.EAST){
			angle += 270;
		}

		while(angle < 0){
			angle += 360;
		}

		if(angle >=360){
			angle %= 360;
		}

		if(angle <= 45 || angle > 315){
			result = Direction.NORTH;
		}
		else if(angle <= 135){
			result = Direction.WEST;
		}
		else if(angle <= 225){
			result = Direction.SOUTH;
		}
		else
			result = Direction.EAST;
		
		return result;

	}
}
