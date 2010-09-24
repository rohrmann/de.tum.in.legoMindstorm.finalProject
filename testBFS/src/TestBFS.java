import Color.Color;
import ColorBrick.ColorSettings;
import Graph.Graph;
import Graph.Pair;
import LightBrick.LightSettings;
import NavigationBrick.RoomNavigator;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorSensor;
import lejos.robotics.navigation.TachoPilot;
import misc.GraphExample;
import miscBrick.Config;
import miscBrick.Robot;


public class TestBFS {

	public static void main(String [] args){
		Graph graph = GraphExample.getGraph();
		
		TachoPilot pilot = new TachoPilot(Config.wheelHeight,Config.wheelToWheel,Motor.A,Motor.B);
		pilot.setMoveSpeed(Config.mapperMoveSpeed);
		ColorSensor colorSensor = new ColorSensor(SensorPort.S3);
		LightSensor leftLightSensor = new LightSensor(SensorPort.S1);
		LightSensor rightLightSensor = new LightSensor(SensorPort.S2);		
		Color[] colors = {Color.WHITE,Color.BLACK,Color.BLUE,Color.RED,Color.GREEN,Color.YELLOW};
		ColorSettings color = new ColorSettings(colorSensor);
		//color.init(colors,Config.colorScanTimes,Config.mapperPollingInterval);
		LightSettings leftLightSettings = new LightSettings(leftLightSensor);
		LightSettings rightLightSettings = new LightSettings(rightLightSensor);
		int tolerance =5;
		//leftLightSettings.init(tolerance);
		//rightLightSettings.init(tolerance);
		
		Robot robot = new Robot(pilot,color,leftLightSettings, rightLightSettings);
		
		RoomNavigator navi = new RoomNavigator(robot,graph);
		
		navi.bfs(new Pair(2,2));
	}
}
