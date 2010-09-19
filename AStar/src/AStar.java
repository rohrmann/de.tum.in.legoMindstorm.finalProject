import Graph.*;
import misc.*;
import java.util.*;

public class AStar {
	
	Graph graph;
	Heap openList;
	HashMap closedList;
	
	public AStar(Graph graph)
	{
		this.graph = graph;
	}
	
	public Pair[] findWay(Pair currentPosition, Pair target)
	{
		return findWay(graph.getNode(currentPosition), graph.getNode(target));
	}
	
	public Pair[] findWay(Node currentNode, Node targetNode)
	{
		openList = new Heap();
		openList.insert(new HeapNode(currentNode, 0));
		
		while(!openList.isEmpty())
		{
			HeapNode min = openList.removeMin();
			if(min.getGraphNode() == targetNode)
				return min.getGraphNode();
		}	
		
		
		return null;
	}
	
	private void expandNode(HeapNode currentNode)
	{
		int dist = currentNode.getDistance() + 1;
		
		examineNode(currentNode, currentNode.getGraphNode().get(Direction.getNorth()), dist);
	}
	
	private void examineNode(HeapNode currentNode, HeapNode neighbor, int dist)
	{
		
	}
}
