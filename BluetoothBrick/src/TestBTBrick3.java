import lejos.nxt.*; 
import lejos.nxt.comm.*; 
import java.io.*; 

/** 
 * Receive data from another NXT, a PC, a phone, 
 * or another bluetooth device. 
 * 
 * Waits for a connection, receives an int and returns 
 * its negative as a reply, 100 times, and then closes 
 * the connection, and waits for a new one. 
 * 
 * @author Lawrie Griffiths 
 * 
 */ 
public class TestBTBrick3 { 

   public static void main(String [] args)  throws Exception 
   { 
      String connected = "Connected"; 
        String waiting = "Waiting..."; 
        String closing = "Closing..."; 
        
      while (true) 
      { 
         LCD.drawString(waiting,0,0); 
         LCD.refresh(); 

           BTConnection btc = Bluetooth.waitForConnection(); 
           
         LCD.clear(); 
         LCD.drawString(connected,0,0); 
         LCD.refresh();    

         DataInputStream dis = btc.openDataInputStream(); 
         DataOutputStream dos = btc.openDataOutputStream(); 
        int k =0;
         for(int i=0;i<2;i++) { 
        	 byte[] n = new byte[10];
        	 for(int j=9;j>=0;j--){
            n[k]=(byte)(-10);
            k++;
        	 }
        	 k=0;
            dos.write(n);
            dos.flush(); 
         } 
         
         dis.close(); 
         dos.close(); 
         Thread.sleep(100); // wait for data to drain 
         LCD.clear(); 
         LCD.drawString(closing,0,0); 
         LCD.refresh(); 
         btc.close(); 
         LCD.clear(); 
      } 
   } 
} 