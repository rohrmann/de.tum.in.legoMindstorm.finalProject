import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorSensor;


public class TestSensorUnit {
	
	public static void main(String[] args){
		
		String [] colors= {"white","black","blue","green","yellow","red"};
		int times = 5;
		ColorSensor color = new ColorSensor(SensorPort.S3);
		LightSensor left = new LightSensor(SensorPort.S2);
		LightSensor right = new LightSensor(SensorPort.S1);
		
		left.setFloodlight(false);
		right.setFloodlight(false);
		ColorSettings settings = Helper.initColors(colors,times,SensorPort.S3);
		//left.setFloodlight(true);
		LightSettings sLeft = Helper.initLight(5,SensorPort.S2);
		//left.setFloodlight(false);
		//right.setFloodlight(true);
		LightSettings sRight = Helper.initLight(5, SensorPort.S1);
		
		boolean colorMode = true;
		boolean enterPressed = false;
		
		
		
		while(!Button.ESCAPE.isPressed()){
			if(colorMode){
				Helper.drawString(settings.getColor(color.getColor()), 0, 0);
			}
			else{
				Helper.drawInt(left.getLightValue(), 0, 0);
				Helper.drawInt(right.getLightValue(),0, 1);
				Helper.drawString(sLeft.groundChange(left.getLightValue())?"Line":"Ground",0,2);
				Helper.drawString(sRight.groundChange(right.getLightValue())?"Line":"Ground", 0, 3);
			}
			
			if(Button.ENTER.isPressed()&& !enterPressed){
				enterPressed =true;
				colorMode=!colorMode;
				
				if(colorMode){
					Helper.drawString("ColorMode", 0, 2);
					//left.setFloodlight(false);
					//right.setFloodlight(false);
				}
				else{
					Helper.drawString("LightMode",0,2);
					//left.setFloodlight(true);
					//right.setFloodlight(true);
				}
				
			}
			
			if(!Button.ENTER.isPressed()){
				enterPressed =false;
			}
		}
		
	}

}
