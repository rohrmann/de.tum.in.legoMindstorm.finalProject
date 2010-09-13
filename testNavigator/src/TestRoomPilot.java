import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorSensor;
import lejos.robotics.navigation.TachoPilot;
import misc.Helper;
import misc.Robot;
import Color.Color;
import Color.ColorSettings;
import Light.LightSettings;
import Navigation.RoomPilot;


public class TestRoomPilot {
	
	public static void main(String[] args){
		TachoPilot pilot = new TachoPilot(5.6f,10.35f,Motor.A,Motor.B);
		ColorSensor colorSensor = new ColorSensor(SensorPort.S3);
		LightSensor leftLightSensor = new LightSensor(SensorPort.S1);
		LightSensor rightLightSensor = new LightSensor(SensorPort.S2);
		int times = 5;
		int pollingInterval = 25;
		Color[] colors = {Color.WHITE,Color.BLACK,Color.BLUE,Color.RED,Color.GREEN,Color.YELLOW};
		ColorSettings color = new ColorSettings(colorSensor);
		color.init(colors,times,pollingInterval);
		LightSettings leftLightSettings = new LightSettings(leftLightSensor);
		LightSettings rightLightSettings = new LightSettings(rightLightSensor);
		int tolerance =5;
		leftLightSettings.init(tolerance);
		rightLightSettings.init(tolerance);
		
		Robot robot = new Robot(pilot,color,leftLightSettings, rightLightSettings);
		
		RoomPilot rPilot = new RoomPilot(robot);
		
		boolean escapePressed = false;
		while(!escapePressed){
			Helper.drawText("Room:"+rPilot.goToNextRoom().toString());
			
			int result = Button.waitForPress();
			
			if((result & 8) != 0)
				escapePressed = true;
		}
	}

}
