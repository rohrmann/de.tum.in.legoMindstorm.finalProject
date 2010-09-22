package Memory;

import Graph.Pair;

public class Action {
private static Pair boxIs;
private static Pair boxDestination;
	
	public static Pair[] getAction(){
		return new Pair[]{boxIs, boxDestination};
	}
	
	public static void setAction(Pair is, Pair dest){
		boxIs = is;
		boxDestination = dest;
	}
}
