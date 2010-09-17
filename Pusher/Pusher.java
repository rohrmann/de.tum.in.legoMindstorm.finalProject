package Pusher;

import Graph.Graph;
import Navigation.*;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import misc.Robot;
import misc.RoomInformation;


public class Pusher {
 
	Robot robot;
	RoomNavigator navigator;
	
	public Pusher(Robot robot, Graph map)
	{
		this.robot = robot;
		robot.getPilot().setSpeed(100);
		navigator = new RoomNavigator(robot, map);
	}
	
	public RoomNavigator getNavigator()
	{
		return navigator;
	}
	
	public void push(int distance)
	{
//		RoomNavigator r = new RoomNavigator(robot);
//		
//		for(int i=0;i<distance-1;i++)
//			r.goToNextRoom();
//		
//		System.out.println("DONE");
//		
//		push();
	}
	
	public void push()
	{
		
		float travelDistance = MapInformation.lineLength + MapInformation.squareSize - MapInformation.boxSize/2 - MapInformation.wheelToBucket;
		int turnTolerance=20;
		
		
		RoomInformation roomInfo = new RoomInformation();
		
		DriveDistanceForward driveDistanceForward = new DriveDistanceForward(robot, travelDistance);
		FollowLine followLine = new FollowLine(robot, roomInfo);
		DriveForward driveForward = new DriveForward(robot, roomInfo);
		CheckRoom checkRoom = new CheckRoom(robot, (int)(travelDistance - MapInformation.BackOffDistance )-4, 4, roomInfo);
		Turn turn = new Turn(robot, 180);
		FindLine findLine = new FindLine(robot);
		
		// Vorwaertz fahren bis die Kiste auf der naechsten Kreuzung liegt
		System.out.println("Follow line for "+travelDistance+"cm ...");
		Behavior[] driveForwardAndFollow = new Behavior[]{driveDistanceForward, followLine};
		Arbitrator a = new Arbitrator(driveForwardAndFollow, true);
		a.start();
		
		// rueckwaertz und drehen
		System.out.println("Travel back for "+Integer.toString((int)MapInformation.BackOffDistance)+"cm ...");
		robot.getPilot().travel(-MapInformation.BackOffDistance);
		System.out.println("turn 170 and travel back for 1 cm...");
		robot.getPilot().rotate(90);// - turnTolerance);

		System.out.println("looking for line...");
		
		// Linie wiederfinden
		Behavior[] turnAndFindLine = new Behavior[]{turn, findLine};
		a = new Arbitrator(turnAndFindLine, true);
		a.start();
		
		//robot.getPilot().travel(-2);
		
		System.out.println("return to room...");
		//
		Behavior[] returnToRoom = new Behavior[]{driveForward, followLine, checkRoom};
		a = new Arbitrator(returnToRoom, true);
		a.start();
		
		navigator.updateHeading(180);
	}

}
