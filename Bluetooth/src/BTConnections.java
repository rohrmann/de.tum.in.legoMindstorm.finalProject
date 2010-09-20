import java.io.DataInputStream;
import java.io.DataOutputStream;


public class BTConnections {
	
	private static DataInputStream mapperIn;
	private static DataOutputStream mapperOut;
	private static DataInputStream pusherIn;
	private static DataOutputStream pusherOut;
	private static DataInputStream pullerIn;
	private static DataOutputStream pullerOut;
	
	public static DataInputStream getMapperIn(){
		return mapperIn;
	}
	
	public static DataOutputStream getMapperOut(){
		return mapperOut;
	}
	
	public static DataInputStream getPusherIn(){
		return pusherIn;
	}
	
	public static DataOutputStream getPusherOut(){
		return pusherOut;
	}
	
	public static DataInputStream getPullerIn(){
		return pullerIn;
	}
	
	public static DataOutputStream getPullerOut(){
		return pullerOut;
	}
	
	public static void setMapperIn(DataInputStream in){
		mapperIn = in;
	}
	
	public static void setMapperOut(DataOutputStream out){
		mapperOut = out;
	}
	
	public static void setPusherIn(DataInputStream in){
		pusherIn = in;
	}
	
	public static void setPusherOut(DataOutputStream out){
		pusherOut = out;
	}
	
	public static void setPullerIn(DataInputStream in){
		pullerIn = in;
	}
	
	public static void setPullerOut(DataOutputStream out){
		pullerOut = out;
	}
	
}
