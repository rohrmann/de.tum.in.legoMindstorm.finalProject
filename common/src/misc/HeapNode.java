package misc;
import Graph.*;

public class HeapNode {

	int currentScore;
	int estimatedScore;
	Node graphNode;
	HeapNode predecessor;
	


	public HeapNode(Node graphNode, int currentScore, int estimatedScore, HeapNode predecessor)
	{
		this.graphNode = graphNode;
		this.currentScore = currentScore;
		this.estimatedScore = estimatedScore;
		this.predecessor = predecessor;
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
	
}
