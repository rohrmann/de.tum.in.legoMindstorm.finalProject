package miscBrick;

public class Config {
	
	// Map
	public static float lineLength = 17.5f;// Laenge der schwarzen Linie
	public static float squareSize = 1.9f; // Seitenlaenge der farbigen Quadrate an Kreuzungen
	public static float wheelToBucket = 5.3f; // Abstand von der Mitte der Raeder bzw. Farbsensor zum Schaufelinnenraum
	public static float boxSize = 4f; //Seitenlaenge der Box
	public static int roomDistance = 20;
	
	// Roboter allgemein
	public static float wheelHeight = 5.6f;
	public static float wheelToWheel = 10.35f;
	public static int lightTolerance = 5;
	public static int colorScanTimes = 5;	
	public static int checkRoomTolerance = 4;
	public static int checkRoomSpeed = 5;
	
	// Pusher
	public static float pusherBackOffDistance = 6f; // Stecke die zurueckgesetzt werden soll
	public static int pusherCheckRoomTolerance = 4; 
	public static int pusherMoveSpeed = 15;
	public static int pusherTurnSpeed = 40;
	public static float pusherWidth = 10.35f;

	// Puller
	public static int pullerMoveSpeed = 15;
	public static int pullerTurnSpeed = 40;
	public static int pullerArmSpeed = 60;
	public static int pullerArmAngle = 163;
	public static float pullerReleaseDistance = 14.5f;
	public static float pullerTakeDistance = 6.0f;
	public static float pullerWidth = 10.7f;
	
	// Mapper
	public static int mapperMoveSpeed = 15;
	public static int mapperTurnSpeed = 150;
	public static int mapperPollingInterval = 25;
	public static int roomDistanceTolerance = 3;
	public static int acceptionPeriodForColor = 150;
	public static float mapperWidth = 10.28f;
	
	//default
	public static int defaultMoveSpeed = 15;
	public static int defaultTurnSpeed = 40;

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