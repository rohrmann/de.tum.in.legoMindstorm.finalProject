
import lejos.pc.comm.*; 
import java.io.*; 

/** 
 * This is a PC sample. It connects to the NXT, and then 
 * sends an integer and waits for a reply, 100 times. 
 * 
 * Compile this program with javac (not nxjc), and run it 
 * with java. 
 * 
 * You need pccomm.jar and bluecove.jar on the CLASSPATH. 
 * On Linux, you will also need bluecove-gpl.jar on the CLASSPATH. 
 * 
 * Run the program by: 
 * 
 *   java BTSend 
 * 
 * Your NXT should be running a sample such as BTReceive or 
 * SignalTest. Run the NXT program first until it is 
 * waiting for a connection, and then run the PC program. 
 * 
 * @author Lawrie Griffiths 
 * 
 */ 
public class BluetoothPC {    
   public static void main(String[] args) { 
      NXTConnector conn = new NXTConnector(); 
    
      conn.addLogListener(new NXTCommLogListener(){ 

         public void logEvent(String message) { 
            System.out.println("BTSend Log.listener: "+message); 
             
         } 

         public void logEvent(Throwable throwable) { 
            System.out.println("BTSend Log.listener - stack trace: "); 
             throwable.printStackTrace(); 
             
         } 
          
      } 
      ); 
      // Connect to any NXT over Bluetooth 
      boolean connected = conn.connectTo("keen"); 
    
       
      if (!connected) { 
         System.err.println("Failed to connect to any NXT"); 
         System.exit(1); 
      } 
       
      DataOutputStream dos = conn.getDataOut(); 
      DataInputStream dis = conn.getDataIn(); 
             
      for(int i=0;i<100;i++) { 
         try { 
            System.out.println("Sending " + (i*30000)); 
            dos.writeInt((i*30000)); 
            dos.flush();          
             
         } catch (IOException ioe) { 
            System.out.println("IO Exception writing bytes:"); 
            System.out.println(ioe.getMessage()); 
            break; 
         } 
          
         try { 
            System.out.println("Received " + dis.readInt()); 
         } catch (IOException ioe) { 
            System.out.println("IO Exception reading bytes:"); 
            System.out.println(ioe.getMessage()); 
            break; 
         } 
      } 
       
      try { 
         dis.close(); 
         dos.close(); 
         conn.close(); 
      } catch (IOException ioe) { 
         System.out.println("IOException closing connection:"); 
         System.out.println(ioe.getMessage()); 
      } 
   } 
} 