import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorSensor;
import lejos.robotics.navigation.TachoPilot;
import misc.AStar;
import misc.Direction;
import misc.GraphExample;
import miscBrick.Robot;
import Color.Color;
import ColorBrick.ColorSettings;
import Graph.Graph;
import Graph.Node;
import Graph.Pair;
import Graph.Type;
import LightBrick.LightSettings;
import NavigationBrick.RoomNavigator;


public class testAstar {
	/**
	 * @param aArg
	 * @throws Exception
	 */
	public static void main (String[] aArg)
	throws Exception
	{
		
//		TachoPilot pilot = new TachoPilot(5.6f,10.35f,Motor.A,Motor.B);
//		ColorSensor colorSensor = new ColorSensor(SensorPort.S3);
//		LightSensor leftLightSensor = new LightSensor(SensorPort.S1);
//		LightSensor rightLightSensor = new LightSensor(SensorPort.S2);
//		int times = 5;
//		int pollingInterval = 25;
//		Color[] colors = {Color.WHITE,Color.BLACK,Color.BLUE,Color.RED,Color.GREEN,Color.YELLOW};
//		ColorSettings color = new ColorSettings(colorSensor);
//		color.init(colors,times,pollingInterval);
//		LightSettings leftLightSettings = new LightSettings(leftLightSensor);
//		LightSettings rightLightSettings = new LightSettings(rightLightSensor);
//		int tolerance = 5;
//		leftLightSettings.init(tolerance);
//		rightLightSettings.init(tolerance);
//		
//		Robot robot = new Robot(pilot,color,leftLightSettings, rightLightSettings);
//		
//		Pusher pusher = new Pusher(robot, GraphExample.getGraph());
//		
//		System.out.println("press button to start test");
//		Button.waitForPress();
//		System.out.println("RUNNING...");
//
//		
//		RoomNavigator natschi = new RoomNavigator(robot, GraphExample.getGraph());
		
		System.out.println(GraphExample.getGraph().getNode(new Pair(4,3)));
		
		Pair[] path = AStar.findPath(GraphExample.getGraph(), new Pair(4,3),Direction.NORTH, new Pair(5,0)).toArray(new Pair[0]);
		
		for(int i=0;i<path.length; i++)
		{
			System.out.println(path[i].getX()+"/"+path[i].getY());
		}
		
		System.in.read();
		
		
		
		
//		System.out.println("pushed");
//		
//		while(!Button.ESCAPE.isPressed()){
//			
//			try{
//				Thread.sleep(500);
//			}catch(InterruptedException ex){
//				
//			}
//		}
		
	}
	

	
	
	
	
	
}
