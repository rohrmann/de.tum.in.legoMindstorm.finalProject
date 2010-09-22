package ColorBrick;

import java.util.ArrayList;
import java.util.List;

import Color.Color;


import lejos.nxt.Button;
import lejos.nxt.addon.ColorSensor;
import miscBrick.Helper;

/**
 * 
 * @author rohrmann
 *
 */
public class ColorSettings {
	private class ColorSettingsEntry{
		Color color;
		int[] rgb;
		int tolerance;
		
		
		ColorSettingsEntry(Color color, int[] rgb,int tolerance){
			this.color = color;
			this.rgb =rgb;
			this.tolerance = tolerance;
		}
	}
	
	private ColorSensor sensor;
	private List<ColorSettingsEntry> colors;
	
	public ColorSettings(ColorSensor sensor){
		this.sensor = sensor;
		colors = new ArrayList<ColorSettingsEntry>();
	}
	
	public boolean addColor(Color color, int tolerance,int[] rgb){
		return colors.add(new ColorSettingsEntry(color,rgb, tolerance));
	}
	
	public int size(){
		return colors.size();
	}
	
	public boolean empty(){
		return size() == 0;
	}
	
	public ColorSensor getSensor(){
		return sensor;
	}
	
	public int[] getColor(){
		return sensor.getColor();
	}
	
	public void init(Color[] colors, int times,int pollingInterval){
		int[] tolerances = new int[colors.length];
		
		for(int i =0; i< tolerances.length;i++){
			tolerances[i] = Integer.MAX_VALUE;
		}
		
		init(colors,tolerances,times,pollingInterval);
	}
	
	public void init(Color[] colors, int[] tolerances, int times,int pollingInterval){
		Helper.drawText("White balancing Press Button");
		Button.waitForPress();
		
		sensor.initWhiteBalance();
		
		Helper.drawText("Black level Press Button");
		Button.waitForPress();
		
		sensor.initBlackLevel();
		
		for(int i=0;i<colors.length;i++){
			Helper.drawText("Color "+colors[i]+" Press Button");
			boolean notPressed = true;
			boolean pressed = false;
			
			while(notPressed){
				if(Button.readButtons()!= 0)
					pressed = true;
				
				if(pressed && Button.readButtons()==0)
					notPressed=false;
				
				Helper.drawColor(sensor.getColor(), 0, 2);
			}
			
			float[] accColor= {0,0,0};
			for(int j = 0;j<times;j++){
				accColor[0] += sensor.getRedComponent();
				accColor[1] += sensor.getGreenComponent();
				accColor[2] += sensor.getBlueComponent();
				
				try{
					Thread.sleep(pollingInterval);
				}catch(InterruptedException ex){
				}
			}
			
			accColor[0] /= times;
			accColor[1] /= times;
			accColor[2] /= times;
			
			int[] color = {Math.round(accColor[0]),Math.round(accColor[1]),Math.round(accColor[2])};
			
			addColor(colors[i],tolerances[i],color);
		}
	}
	
	public Color getColorName(){
		return getColorName(sensor.getColor());
	}
	
	public Color getColorName(int [] rgb){
		
		if(empty()){
			Helper.error("ColorSettings.getColor: is empty");
		}
		
		Color result = colors.get(0).color;
		int diff = getQuadraticDiff(rgb,colors.get(0).rgb);
		int cDiff;
		int tolerance = colors.get(0).tolerance;
		
		for(int i =1; i< colors.size();i++){
			cDiff = getQuadraticDiff(rgb,colors.get(i).rgb);
			if(cDiff < diff){
				diff = cDiff;
				result = colors.get(i).color;
				tolerance = colors.get(i).tolerance;
			}
		}
		
		if(diff < tolerance)
			return result;
		else
			return Color.UNKNOWN;
	}
	
	private int getQuadraticDiff(int[] rgb1, int [] rgb2){
		if(rgb1.length != rgb2.length){
			Helper.error("ColorSettings.getQuadraticDiff: arguments have different length");
		}
		
		int diff = 0;
		
		for(int i=0;i<rgb1.length;i++){
			diff += (rgb1[i]-rgb2[i])*(rgb1[i]-rgb2[i]);
		}
		
		return diff;
	}
}
