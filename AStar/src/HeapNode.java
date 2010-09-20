import Graph.*;

public class HeapNode {

	int realDistance;
	int estimatedDistance;
	Node graphNode;
	HeapNode leftNode;
	HeapNode rightNode;	
	Node antecessor;
	
	public HeapNode(Node graphNode, int distance)
	{
		this.graphNode = graphNode;
		this.distance = distance;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public HeapNode getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(HeapNode leftNode) {
		this.leftNode = leftNode;
	}

	public HeapNode getRightNode() {
		return rightNode;
	}

	public void setRightNode(HeapNode rightNode) {
		this.rightNode = rightNode;
	}

	public void setGraphNode(Node graphNode) {
		this.graphNode = graphNode;
	}

	public Node getGraphNode() {
		return graphNode;
	}

	public Node getAntecessor() {
		return antecessor;
	}

	public void setAntecessor(Node antecessor) {
		this.antecessor = antecessor;
	}
	
}
