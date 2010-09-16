import lejos.nxt.*; 
import lejos.nxt.comm.*; 
import misc.Helper;

import java.io.*; 
import java.util.Enumeration;

import Graph.BuildGraph;
import Graph.Graph;
import Graph.Node;
import Graph.Pair;
import Graph.Type;

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
public class BluetoothBrick { 
	
	private static int arrayLength = 6;
	
	public static int getArrayLength(){
		return arrayLength;
	}

   public static void main(String [] args)  throws Exception 
   { 
      String connected = "Connected"; 
        String waiting = "Waiting for Con..."; 
        String closing = "Closing..."; 
        
      
         LCD.drawString(waiting,0,0); 
         LCD.refresh(); 

           BTConnection btc = Bluetooth.waitForConnection(); 
           
         LCD.clear(); 
         LCD.drawString(connected,0,0); 
         LCD.refresh();    

         DataInputStream dis = btc.openDataInputStream(); 
         DataOutputStream dos = btc.openDataOutputStream(); 
         Graph graph = BuildGraph.getGraph1();
         Enumeration enumeration = graph.getHashtable().keys();
         byte[] send = new byte[arrayLength];
         int control;
         while(enumeration.hasMoreElements()){
        	 Pair pair = (Pair) enumeration.nextElement();
        	 Node node = graph.getNode(pair);
        	 int type = typeToInt(node);
        	 control = (int) (Math.random()*255);
        	 send[4] = (byte)type;
        	 if(pair.getX()<0){
        		 send[1] = (byte)1;
        		 send[0] = (byte) -pair.getX();
        	 }
        	 else{
        		 send[1] = (byte)0;
        		 send[0] = (byte)pair.getX();
        	 }
        	 if(pair.getY()<0){
        		 send[3] = (byte)1;
        		 send[2] = (byte) -pair.getY();
        	 }
        	 else{
        		 send[3] = (byte)0;
        		 send[2] = (byte)pair.getY();
        		 
        	 }
      
        	 
        	
//        	 for(int i=0;i<=4;i++){
//        		 send[i]=(byte)i;//nodeCode[i];
//        	 }
        	 send[5]=(byte)control;
        	 dos.write(send);
             dos.flush(); 
             Helper.drawString("Data send", 0, 1);
             Helper.drawString("Waiting for Data" , 0, 2);
            // dis.read();
             Helper.drawString("Data received" , 0, 3);
            if(dis.read()== control){
          	Sound.beep();
             }
            else{
         	   Sound.buzz();
            }
            Thread.sleep(1000);
            LCD.clearDisplay();
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
   
   public static int typeToInt(Node node){
	   Type a = node.getType();
		switch(a){
		case EMPTY: 
			return 0;
		case UNKNOWN:
			return 1;
		case PUSHSTART:
			return 3;
		case PULLSTART:
			return 2;
		case BOX:
			return 4;
		case DEST:
			return 5;
		case UNDEFINED:
			return 6;
		}
		return 6; 
   }
   
} 