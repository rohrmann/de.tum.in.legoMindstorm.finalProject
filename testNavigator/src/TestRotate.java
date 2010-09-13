import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorSensor;
import lejos.robotics.navigation.TachoPilot;
import misc.Direction;
import misc.Robot;
import Analysis.AnalyseCrossing;
import Color.Color;
import Color.ColorSettings;
import Light.LightSettings;


public class TestRotate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TachoPilot pilot = new TachoPilot(5.6f,10.35f,Motor.A,Motor.B);
		LightSensor leftLightSensor = new LightSensor(SensorPort.S1);
		LightSensor rightLightSensor = new LightSensor(SensorPort.S2);
		LightSettings leftLightSettings = new LightSettings(leftLightSensor);
		LightSettings rightLightSettings = new LightSettings(rightLightSensor);
		int tolerance =5;
		
		rightLightSettings.init(tolerance);
		leftLightSettings.init(tolerance);
		
		Robot robot = new Robot(pilot,null,leftLightSettings, rightLightSettings);
		
		AnalyseCrossing test = new AnalyseCrossing(robot);
		test.analyseCrossing(Direction.NORTH);
	}

}
