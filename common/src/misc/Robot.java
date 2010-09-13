package misc;
import Color.ColorSettings;
import Light.LightSettings;
import lejos.robotics.navigation.TachoPilot;


public class Robot {
	
	private TachoPilot pilot;
	private ColorSettings color;
	private LightSettings leftLight;
	private LightSettings rightLight;
	
	public Robot(TachoPilot pilot, ColorSettings color, LightSettings leftLight, LightSettings rightLight){
		this.pilot = pilot;
		this.color = color;
		this.leftLight = leftLight;
		this.rightLight = rightLight;
	}
	
	public TachoPilot getPilot(){
		return pilot;
	}
	
	public ColorSettings getColor(){
		return color;
	}
	
	public LightSettings getLeftLight(){
		return leftLight;
	}
	
	public LightSettings getRightLight(){
		return rightLight;
	}

}
