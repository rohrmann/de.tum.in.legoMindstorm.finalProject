import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorSensor;


public class Test {
	
	public static void main(String[] args){
		ColorSensor c1 = new ColorSensor(SensorPort.S1);
		ColorSensor c2 = new ColorSensor(SensorPort.S2);
		ColorSettings settings1 = null;
		ColorSettings settings2 = null;
		String[] colors = {"white","black","blue","red","yellow","green"};
		int times = 4;
		
		settings1 = Helper.initColors(colors,times,SensorPort.S1);
		settings2 = Helper.initColors(colors, times, SensorPort.S2);
		
		while(!Button.ESCAPE.isPressed()){
			
			Helper.drawString(settings1.getColor(c1.getColor()), 0,0);
			Helper.drawString(settings2.getColor(c2.getColor()), 0, 1);
			
			try{
				Thread.sleep(500);
			}catch(InterruptedException ex){
				
			}
		}
		
		
	}
	
	
}