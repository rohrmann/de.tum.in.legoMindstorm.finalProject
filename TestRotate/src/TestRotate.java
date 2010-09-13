
import java.util.List;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.TachoPilot;
import misc.Direction;
import misc.Helper;
import misc.Robot;
import Analysis.AnalyseCrossing;
import Light.LightSettings;


public class TestRotate {
	public static void main(String[] args){
		TachoPilot pilot = new TachoPilot(5.6f,10.35f,Motor.A,Motor.B);
		LightSensor leftLightSensor = new LightSensor(SensorPort.S1);
		LightSensor rightLightSensor = new LightSensor(SensorPort.S2);
		LightSettings leftLightSettings = new LightSettings(leftLightSensor);
		LightSettings rightLightSettings = new LightSettings(rightLightSensor);
		int tolerance =5;
		leftLightSettings.init(tolerance);
		rightLightSettings.init(tolerance);
		
		Robot robot = new Robot(pilot,null,leftLightSettings, rightLightSettings);
		
		Direction heading = Direction.NORTH;
		
		AnalyseCrossing analyser = new AnalyseCrossing(robot);
		
		while(true){
			Helper.drawText("Press Button for analysis");
			Button.waitForPress();
			List<Direction> result = analyser.analyseCrossing(heading);
			
			for(int i =0; i< result.size();i++){
				Helper.drawString(result.get(i).toString(), 0, i);
			}
			
			Button.waitForPress();
		}
	}
}
