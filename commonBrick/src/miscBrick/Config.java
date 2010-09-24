package miscBrick;

public class Config {
	
	// Map
	public static float lineLength = 17.5f;// Laenge der schwarzen Linie
	public static float squareSize = 1.9f; // Seitenlaenge der farbigen Quadrate an Kreuzungen
	public static float wheelToBucket = 5.3f; // Abstand von der Mitte der Raeder bzw. Farbsensor zum Schaufelinnenraum
	public static float boxSize = 4f; //Seitenlaenge der Box
	public static float roomDistance = 19.5f;

	
	// Roboter allgemein
	public static float wheelHeight = 5.6f;
	public static int colorScanTimes = 5;	
	public static int checkRoomTolerance = 4;
	public static int checkRoomSpeed = 5;
	
	// Pusher
	public static float pusherBackOffDistance = 6f; // Stecke die zurueckgesetzt werden soll
	public static int pusherMoveSpeed = 10;
	public static int pusherTurnSpeed = 20;
	public static float pusherWheelToWheel = 10.35f;
	public static int pusherLightTolerance = 7;

	// Puller
	public static int pullerMoveSpeed = 15;
	public static int pullerTurnSpeed = 40;
	public static int pullerArmSpeed = 60;
	public static int pullerArmAngle = 163;
	public static float pullerReleaseDistance = 14.5f;
	public static float pullerTakeDistance = 6.0f;
	public static float pullerWheelToWheel = 10.7f;
	public static int pullerLightTolerance = 7;
	
	// Mapper
	public static int mapperMoveSpeed = 15;
	public static int mapperPollingInterval = 25;
	public static int acceptionPeriodForColor = 150;
	public static float mapperWheelToWheel = 10.28f;
	public static int mapperLightTolerance = 7;
	
	//default
	public static int defaultMoveSpeed = 10;
	public static int defaultTurnSpeed = 30;

	// Check Room
	public static int checkRoomPollingInterval = 10;
	
	// RoomMissed
	public static final int roomMissedPollingInterval = 10;
	
	// Helper
	public static final int columnLength = 16;
	public static final int numRows = 8;

	// BluetoothBrick
	public static int arrayLength = 6;
	
	public static String rightoo = "Rightoo";
	public static String s_brick = "s_brick";
}