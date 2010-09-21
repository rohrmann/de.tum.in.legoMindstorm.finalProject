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
	
	// Pusher
	public static float pusherBackOffDistance = 6f; // Stecke die zurueckgesetzt werden soll
	public static int pusherCheckRoomTolerance = 4; 
	public static int pusherMoveSpeed = 10;
	public static int pusherTurnSpeed = 50;

	// Mapper
	public static int mapperMoveSpeed = 10;
	public static int mapperPollingInterval = 25;
	public static int roomDistanceTolerance = 3;
	public static int acceptionPeriodForColor = 150;

	
	// Check Room
	public static int checkRoomPollingInterval = 10;

}