package misc;
import Graph.*;

public class HeapNode {

	int currentScore;
	int estimatedScore;
	Node graphNode;
	HeapNode predecessor;
	Direction heading;
	


	public HeapNode(Node graphNode, int currentScore, int estimatedScore, HeapNode predecessor, Direction heading)
	{
		this.graphNode = graphNode;
		this.currentScore = currentScore;
		this.estimatedScore = estimatedScore;
		this.predecessor = predecessor;
		this.heading = heading;
	}
	
	public int getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}

	public int getEstimatedScore() {
		return estimatedScore;
	}

	public void setEstimatedScore(int estimatedScore) {
		this.estimatedScore = estimatedScore;
	}

	public void setGraphNode(Node graphNode) {
		this.graphNode = graphNode;
	}

	public Node getGraphNode() {
		return graphNode;
	}

	public HeapNode getPredecessor() {
		return predecessor;
	}

	public void setPredecessor(HeapNode predecessor) {
		this.predecessor = predecessor;
	}
	
	public Direction getHeading() {
		return heading;
	}

	public void setHeading(Direction heading) {
		this.heading = heading;
	}
	
}
