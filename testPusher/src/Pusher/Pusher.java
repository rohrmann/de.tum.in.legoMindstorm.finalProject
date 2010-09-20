package Pusher;

import Graph.Graph;
import Navigation.*;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import misc.Config;
import misc.Robot;
import misc.RoomInformation;


public class Pusher {
 
	Robot robot;
	RoomNavigator navigator;
	
	public Pusher(Robot robot, Graph map)			//Konstruktor
	{
		this.robot = robot;
		robot.getPilot().setMoveSpeed(Config.pusherMoveSpeed);
		robot.getPilot().setTurnSpeed(Config.pusherTurnSpeed);
		navigator = new RoomNavigator(robot, map);
	}
	
	public RoomNavigator getNavigator()
	{
		return navigator;
	}
	
	public void push()
	{
		
		float travelDistance = Config.lineLength + Config.squareSize - Config.boxSize/2 - Config.wheelToBucket;		//Distanz, die noch zurueckgelegen werden soll	
		
		RoomInformation roomInfo = new RoomInformation();
		
		DriveDistanceForward driveDistanceForward = new DriveDistanceForward(robot, travelDistance);
		FollowLine followLine = new FollowLine(robot, roomInfo);
		DriveForward driveForward = new DriveForward(robot, roomInfo);
		CheckRoom checkRoom = new CheckRoom(robot, (int)(travelDistance - Config.BackOffDistance )-Config.pusherCheckRoomTolerance, Config.pusherCheckRoomTolerance, roomInfo);
		
		// Vorwaertz fahren bis die Kiste auf der naechsten Kreuzung liegt
		System.out.println("Follow line for "+travelDistance+"cm ...");
		Behavior[] driveForwardAndFollow = new Behavior[]{driveDistanceForward, followLine};
		Arbitrator a = new Arbitrator(driveForwardAndFollow, true);
		a.start();
		
		// rueckwaertz
		System.out.println("Travel back for "+Integer.toString((int)Config.BackOffDistance)+"cm ...");
		robot.getPilot().travel(-Config.BackOffDistance);
		
		// Drehung mit Linienkontrolle
		System.out.println("turn 90...");
		robot.getPilot().rotate(90);
		System.out.println("looking for line...");
		robot.getPilot().rotate(180, true);					// guckt nach Linie, nach der er sich, nach seiner theoretischen 180 Grad Drehung dann richten kann
		while(!robot.getLeftLight().groundChange())
		{ Thread.yield(); }
		while(robot.getLeftLight().groundChange())
		{ Thread.yield(); }
		robot.getPilot().stop();
		
		// zur Kreuzung zurueckkehren
		System.out.println("return to room...");
		Behavior[] returnToRoom = new Behavior[]{driveForward, followLine, checkRoom};
		a = new Arbitrator(returnToRoom, true);
		a.start();
		
		navigator.updateHeading(180);
	}

}
