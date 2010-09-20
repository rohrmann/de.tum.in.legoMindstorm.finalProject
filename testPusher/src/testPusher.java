import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorSensor;
import lejos.robotics.navigation.TachoPilot;
import misc.Robot;
import Color.Color;
import Color.ColorSettings;
import Graph.Graph;
import Graph.Node;
import Graph.Pair;
import Graph.Type;
import Light.LightSettings;
import Navigation.RoomNavigator;
import Pusher.Pusher;


public class testPusher {
	/**
	 * @param aArg
	 * @throws Exception
	 */
	public static void main (String[] aArg)
	throws Exception
	{
		
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
		int tolerance = 5;
		leftLightSettings.init(tolerance);
		rightLightSettings.init(tolerance);
		
		Robot robot = new Robot(pilot,color,leftLightSettings, rightLightSettings);
		
		Pusher pusher = new Pusher(robot, GraphExample.getGraph());
		
		System.out.println("press button to start test");
		Button.waitForPress();
		System.out.println("RUNNING...");

		// faehrt nen Weg auf der Karte
		pusher.getNavigator().turnNorth();
		pusher.getNavigator().goToNextRoom();
		pusher.getNavigator().goToNextRoom();
		
		pusher.getNavigator().turnEast();
		pusher.getNavigator().goToNextRoom();
		pusher.getNavigator().goToNextRoom();
		pusher.getNavigator().goToNextRoom();
		pusher.push();
		
		pusher.getNavigator().turnNorth();
		pusher.getNavigator().goToNextRoom();
		
		pusher.getNavigator().turnEast();
		pusher.getNavigator().goToNextRoom();
		
		pusher.getNavigator().turnSouth();
		pusher.getNavigator().goToNextRoom();
		pusher.getNavigator().goToNextRoom();
		pusher.push();

		pusher.getNavigator().turnNorth();
		pusher.getNavigator().goToNextRoom();
		
		pusher.getNavigator().turnEast();
		pusher.getNavigator().goToNextRoom();
		pusher.push();
		
		pusher.getNavigator().turnSouth();
		pusher.getNavigator().goToNextRoom();
		
		pusher.getNavigator().turnEast();
		pusher.getNavigator().goToNextRoom();
		
		pusher.getNavigator().turnNorth();
		pusher.getNavigator().goToNextRoom();
		pusher.push();
		
		//pusher.getNavigator().turnSouth();
		pusher.getNavigator().goToNextRoom();
		pusher.getNavigator().goToNextRoom();
		
		pusher.getNavigator().turnWest();
		pusher.getNavigator().goToNextRoom();
		pusher.getNavigator().goToNextRoom();
		pusher.getNavigator().goToNextRoom();
		pusher.push();
		
		
		System.out.println("pushed");
		
		while(!Button.ESCAPE.isPressed()){
			
			try{
				Thread.sleep(500);
			}catch(InterruptedException ex){
				
			}
		}
		
	}
	

	
	
	
	
	
}
