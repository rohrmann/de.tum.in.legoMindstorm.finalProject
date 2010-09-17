import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import misc.Helper;


public class Muell {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		Motor.A.setSpeed(100);
		Motor.A.forward();
		Thread.sleep(1000);
		Motor.A.setSpeed(200);
		Thread.sleep(1000);
		Motor.A.setSpeed(300);
		Thread.sleep(1000);
		Motor.A.setSpeed(400);
		Thread.sleep(1000);
		Motor.A.setSpeed(500);
		Thread.sleep(1000);
		Motor.A.setSpeed(600);
		Thread.sleep(1000);
		Motor.A.setSpeed(700);
		Thread.sleep(1000);
		Motor.A.setSpeed(800);
		Thread.sleep(1000);
		Motor.A.setSpeed(900);
		Thread.sleep(10000);
	}

}
