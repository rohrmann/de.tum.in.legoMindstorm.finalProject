package NavigationBrick;

import Color.Color;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import miscBrick.Config;
import miscBrick.Robot;
import miscBrick.RoomInformation;

/**
 * 
 * @author rohrmann
 *
 */
public class RoomPilot {
	
	private Arbitrator arbitrator;
	private Robot robot;
	private boolean roomMissedActive;
	
	
	public RoomPilot(Robot robot, boolean roomMissedActive){
		this.robot = robot;
		this.roomMissedActive = roomMissedActive;
	}
	
	public Color goToNextRoom(){
		RoomInformation information= new RoomInformation();
		Behavior driveForward = new DriveForward(robot,information);
		Behavior followLine = new FollowLine(robot,information);
		Behavior checkRoom = new CheckRoom(robot,Config.roomDistance-Config.lightSensorToColorSensorDistance,Config.checkRoomTolerance,information, roomMissedActive);
		Behavior[] behaviors = {driveForward, followLine, checkRoom};
		robot.getPilot().reset();
		arbitrator = new Arbitrator(behaviors,true);
		arbitrator.start();
		return information.getRoomColor();
	}
	
	public void setRoomMissedActive(boolean roomMissedActive){
		this.roomMissedActive = roomMissedActive;
	}
	
	public void rotate(float angle){
		robot.getPilot().rotate(angle);
	}
	
	public void rotate(float angle,boolean immediateReturn){
		robot.getPilot().rotate(angle, immediateReturn);
	}
	
	public boolean isMoving(){
		return robot.getPilot().isMoving();
	}
	
	public void stop(){
		robot.getPilot().stop();
	}
	
	public void reset(){
		robot.getPilot().reset();
	}
	
	public float getAngle(){
		return robot.getPilot().getAngle();
	}

}
