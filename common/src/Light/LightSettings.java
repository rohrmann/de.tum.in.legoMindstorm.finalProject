package Light;
import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import misc.Helper;


public class LightSettings {
	private int groundValue;
	private int tolerance;
	private LightSensor sensor;
	
	public LightSettings(LightSensor sensor){
		this.sensor = sensor;
	}
	
	public int getGroundValue(){
		return groundValue;
	}
	
	public int getTolerance(){
		return tolerance;
	}
	
	public void setTolerance(int tolerance){
		this.tolerance = tolerance;
	}
	
	public void setGroundValue(int groundValue){
		this.groundValue = groundValue;
	}
	
	public LightSensor getSensor(){
		return sensor;
	}
	
	public int getLightValue(){
		return sensor.getLightValue();
	}
	
	public boolean groundChange(){
		return Math.abs(sensor.getLightValue() - groundValue) > tolerance;
	}
	
	public boolean groundChange(int lightValue){
		return Math.abs(lightValue - groundValue) > tolerance;
	}
	
	public void init(int tolerance){
		Helper.drawText("Init light sensor");
		
		boolean notPressed = true;
		boolean pressed = false;
		
		this.tolerance = tolerance;
		
		while(notPressed){
			if(Button.readButtons()!= 0)
				pressed = true;
			
			if(pressed && Button.readButtons()==0)
				notPressed=false;
			
			Helper.drawInt(sensor.getLightValue(), 0, 2);
		}
		
		groundValue = sensor.getLightValue();
	}
	

}
