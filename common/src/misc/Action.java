package misc;

import Graph.Pair;

public class Action {
	private Pair src;
	private Pair dest;
	
	public Action(Pair src, Pair dest){
		this.src = src;
		this.dest = dest;
	}
	
	public Pair getSrc(){
		return src;
	}
	
	public Pair getDest(){
		return dest;
	}
}
