package misc;

import java.util.List;

import Graph.Pair;

public class Update {
	public List<Pair> boxes;
	public Pair pusher;
	public Pair puller;
	
	public Update(List<Pair> boxes, Pair pusher, Pair puller){
		this.boxes = boxes;
		this.pusher = pusher;
		this.puller = puller;
	}

}
