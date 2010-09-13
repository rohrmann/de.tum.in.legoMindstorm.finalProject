package Navigation;

import Color.Color;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import misc.Robot;
import misc.RoomInformation;

public class RoomPilot {
	
	private Arbitrator arbitrator;
	private Robot robot;
	
	public RoomPilot(Robot robot){
		this.robot = robot;
	}
	
	public Color goToNextRoom(){
		RoomInformation information= new RoomInformation();
		robot.getPilot().setMoveSpeed(5);
		robot.getPilot().setTurnSpeed(5);
		Behavior driveForward = new DriveForward(robot,information);
		Behavior followLine = new FollowLine(robot,information);
		Behavior checkRoom = new CheckRoom(robot,15,5,information);
		Behavior[] behaviors = {driveForward, followLine, checkRoom};
		arbitrator = new Arbitrator(behaviors,true);
		arbitrator.start();
		return information.getRoomColor();
	}

}
