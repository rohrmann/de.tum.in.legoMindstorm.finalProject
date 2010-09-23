package PCConfig;

public class PCConfig {

	private static String mapper = "explorer";
	private static String mapperAddr = "0016530A73F1";
	private static String pusher = "Rightoo";
	private static String pusherAddr = "0016530A2DF5";
	private static String puller = "Winch";
	private static String pullerAddr = "00165307944F";
	
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
	
	public static String getMapperAddr(){
		return mapperAddr;
	}
	
	public static String getPusherAddr(){
		return pusherAddr;
	}
	
	public static String getPullerAddr(){
		return pullerAddr;
	}
	
}
