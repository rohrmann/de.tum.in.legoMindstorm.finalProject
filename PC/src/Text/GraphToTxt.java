package Text;



import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.util.Enumeration;

import Graph.*;

public class GraphToTxt {


	public static int[] range(Graph graph) {
		Enumeration enumeration = graph.getHashtable().keys();
		int maxX = 0;
		int maxY = 0;
		int minX = 0;
		int minY = 0;
		while (enumeration.hasMoreElements()) {
			Pair nextElement = (Pair) enumeration.nextElement();
			if (nextElement.getX() > maxX) {
				maxX = nextElement.getX();
			}
			else if (nextElement.getX() < minX) {
				minX = nextElement.getX();
			}
			if (nextElement.getY() > maxY) {
				maxY = nextElement.getY();
			}
			else if (nextElement.getY() < minY) {
				minY = nextElement.getY();
			}
			System.out.println("maxX: " + maxX + " maxY: " + maxY + " minX: " + minX + " minY: " + minY);
		}
		int[] a = new int[4];
		a[0]=minX;
		a[1]=maxX;
		a[2]=minY;
		a[3]=maxY;
		return a;
	}
	


	public static void writeTxt(Graph graph, String source) {
		int [] a = range(graph);
		try
		{
			FileWriter datenstrom = new FileWriter(new File(source));
			BufferedWriter ausgabe = new BufferedWriter(datenstrom);
			for(int i=a[3]+1;i>=a[2]-1;i--){
					for(int j=a[0]-1;j<=a[1]+1;j++){
						if(graph.hasNode(j, i)){
							
							ausgabe.write(getChar(graph.getNode(new Pair(j,i))));
						}
						else{
							ausgabe.write("#");
						}
					}
			ausgabe.newLine();
			}
			ausgabe.close();

		} catch (IOException e) { // konstruktor
			System.out.println("Fehler");
		}
	}
	
	public static char getChar(Node node){
		Type a = node.getType();
		switch(a){
		case EMPTY: 
			return ' ';
		case UNKNOWN:
			return '#';
		case PUSHSTART:
			return '@';
		case PULLSTART:
			return '!';
		case BOX:
			return '$';
		case DEST:
			return '.';
		case UNDEFINED:
			return '#';
		}
		return '#';
	}

}
