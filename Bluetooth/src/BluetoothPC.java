
import lejos.pc.comm.*; 
import java.io.*; 


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