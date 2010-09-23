package PCConfig;

public class PCConfig {

	private static String mapper = "explorer";
	private static String pusher = "Rightoo";
	private static String puller = "Winch";
	
	private static String textFile = "map.xsb";
	
	public static String getMapper(){
		return mapper;
	}
	
	public static String getPusher(){
		return pusher;
	}
	
	public static String getPuller(){
		return puller;
	}
	
	public static String getTextFile(){
		return textFile;
	}
	
}
