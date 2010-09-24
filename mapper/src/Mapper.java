import java.util.ArrayList;
import java.util.List;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.addon.ColorSensor;
import lejos.robotics.navigation.TachoPilot;
import miscBrick.Config;
import misc.Direction;
import miscBrick.Helper;
import miscBrick.Robot;
import AnalysisBrick.AnalyseCrossing;
import Bluetooth.BluetoothCommunicator;
import Bluetooth.BluetoothConnection;
import BluetoothBrick.BTBrickFactory;
import Color.Color;
import ColorBrick.ColorSettings;
import Graph.Graph;
import Graph.Node;
import Graph.Pair;
import Graph.Type;
import LightBrick.LightSettings;
import NavigationBrick.RoomNavigator;

/**
 * 
 * @author rohrmann
 *
 */
public class Mapper {
	
	private RoomNavigator nav;
	private AnalyseCrossing analyser;
	private Graph map;
	private final Direction[] searchDirections = {Direction.NORTH,Direction.WEST,Direction.SOUTH,Direction.EAST};
	
	public static void main(String[] args){
		BluetoothConnection connection = BTBrickFactory.createConnection();
		Mapper mapper = new Mapper();
		Helper.drawText("Press Button for mapping");
		Button.waitForPress();
		
		mapper.map();
		
		BluetoothCommunicator.sendGraph(mapper.map, connection);
		
		Sound.beepSequence();
		
		BluetoothCommunicator.receiveAck(connection);
		
		connection.close();
		
		Sound.beepSequenceUp();
	}
	
	public Mapper(){
		TachoPilot pilot = new TachoPilot(Config.wheelHeight,Config.mapperWheelToWheel,Motor.A,Motor.B);
		
		pilot.setMoveSpeed(Config.mapperMoveSpeed);
		ColorSensor colorSensor = new ColorSensor(SensorPort.S3);
		LightSensor leftLightSensor = new LightSensor(SensorPort.S1);
		LightSensor rightLightSensor = new LightSensor(SensorPort.S2);		
		Color[] colors = {Color.WHITE,Color.BLACK,Color.BLUE,Color.RED,Color.GREEN,Color.YELLOW};
		ColorSettings color = new ColorSettings(colorSensor);
		color.init(colors,Config.colorScanTimes,Config.mapperPollingInterval);
		LightSettings leftLightSettings = new LightSettings(leftLightSensor);
		LightSettings rightLightSettings = new LightSettings(rightLightSensor);
		int tolerance = Config.mapperLightTolerance;
		leftLightSettings.init(tolerance);
		rightLightSettings.init(tolerance);
		
		Robot robot = new Robot(pilot,color,leftLightSettings, rightLightSettings);
		map = new Graph();
		nav = new RoomNavigator(robot,map); 
		
		analyser = new AnalyseCrossing(robot);
	}
	
	public void map(){
		Node startNode = new Node(Type.PULLSTART,nav.getPosition());
		map.addNode(startNode);
		
		dfs(startNode,Direction.UNDEFINED);
	}
	
	public void dfs(Node startNode,Direction sourceDirection){
		Node current = startNode;
		
		List<Direction> streets = analyser.analyseCrossing(nav.getHeading());
		
		addNodes(streets,current);
		
		for(int i =0;i< searchDirections.length;i++){
			if(map.getType(Helper.calcPos(nav.getPosition(), searchDirections[i]))==Type.UNKNOWN){
				Helper.drawText(searchDirections[i].toString());
				Color color = nav.move(searchDirections[i]);
				Node node = startNode.get(searchDirections[i]);
				node.setType(color);
				dfs(node,searchDirections[i].opposite());
			}
		}
		//Helper.drawText("Backtracking");
		nav.move(sourceDirection);
	}
	
	private List<Node> addNodes(List<Direction> streets, Node node){
		
		List<Node> result = new ArrayList<Node>();
		for(Direction dir : streets){
			if(!node.has(dir)){
				Pair position = Helper.calcPos(nav.getPosition(), dir);
				Node newNode = new Node(Type.UNKNOWN,position);
				
				map.addNode(newNode);
				result.add(newNode);
				
				Direction[] directions = {Direction.NORTH,Direction.SOUTH,Direction.EAST,Direction.WEST};
				
				for(Direction d: directions){
					Node neighbor = map.getNode(Helper.calcPos(position,d));
					if(neighbor!= null){
						newNode.set(neighbor,d);
					}
				}
			}
		}
				
		return result;
	}
	
	

}
