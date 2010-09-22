package Memory;

import Graph.Pair;

public class Move {
private static Pair move;
	
	public static Pair getMove(){
		return move;
	}
	
	public static void setMove(int x, int y){
		move = new Pair(x,y);
	}
}
