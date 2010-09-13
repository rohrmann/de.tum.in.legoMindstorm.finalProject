import Color.Color;
import Color.ColorSettings;
import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorSensor;
import misc.Helper;


public class Test {
	
	public static void main(String[] args){
		ColorSensor c1 = new ColorSensor(SensorPort.S3);
		ColorSettings settings1 = new ColorSettings(c1);
		Color[] colors = {Color.WHITE,Color.BLACK,Color.BLUE,Color.GREEN,Color.RED,Color.YELLOW};
		int times = 5;
		int pollingInterval = 25;
		
		settings1.init(colors, times, pollingInterval);
		while(!Button.ESCAPE.isPressed()){
			
			Helper.drawText(settings1.getColorName().toString());
			
			try{
				Thread.sleep(500);
			}catch(InterruptedException ex){
				
			}
		}
		
		
	}
	
	
}