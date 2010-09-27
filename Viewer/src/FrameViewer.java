import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class FrameViewer extends Frame {

	int scale;
	String map;
	int mapWidth;
	int mapHeight;
	static FrameViewer instance;
	
	public FrameViewer(String file)
	{
		scale = 3;
		readMap(file);
		this.setSize(new Dimension(800, 600));
		super.setVisible(true);
		instance = this;
		
		this.setTitle("map viewer - press any key to close!");
		
		this.addKeyListener(new KeyListener() {
			
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void keyPressed(KeyEvent e) {
				FrameViewer.instance.dispose();
			}
		});
		//super.addWindowListener()))
	}
	
	@Override
	public void paint(Graphics g)
	{
	for(int y=0; y<mapHeight; y++)
		{
			for(int x=0; x<mapWidth; x++)
			{
				int drawx = x * 22*scale + 50;
				int drawy = y * 22*scale + 50;
				if(getColor(x,y) != null)
				{
					g.setColor(getColor(x,y));
					g.fillRect(drawx, drawy, 2*scale, 2*scale);

					if(getColor(x+1,y) != null)
					{
						g.setColor(Color.black);
						g.fillRect(drawx+2*scale, drawy, 20*scale, 2*scale);
					}
					if(getColor(x,y+1) != null)
					{
						g.setColor(Color.black);
						g.fillRect(drawx, drawy+2*scale, 2*scale, 20*scale);
					}
				}
			}
		}
		
		

	}
	
	public Color getColor(int x, int y)
	{
		if(x >= mapWidth) return null;
		if(y >= mapHeight) return null;
		
		//y = mapHeight - y-1;
		
		String c = map.substring(x+y*mapWidth,x+y*mapWidth+1);
		
		if(c.equals("#"))
			return null;
		else if(c.equals(" "))
			return Color.red;
		else if(c.equals("."))
			return Color.green;
		else if(c.equals("@"))
			return Color.yellow;
		else if(c.equals("!"))
			return Color.orange;
		else if(c.equals("$"))
			return Color.blue;
		
		return null;
		
	}
	
	
	public void readMap(String file)
	{
		try {
			java.io.FileReader fr = new FileReader(file);
			java.io.BufferedReader br = new BufferedReader(fr);
			
			map = "";
			String line = null;
			while(null != (line = br.readLine()))
			{
				map += line;
				mapWidth = line.length();
			}
			
			mapHeight = map.length() / mapWidth;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
