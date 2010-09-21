package misc;
import Graph.*;
import misc.*;
import java.util.*;

public class AStar {
	
	public final static int edgeScore = 5;
	public final static int turnScore = 1; 
	
	public static ArrayList<Pair> findPath(Graph graph, Pair startPosition, Pair target)
	{
		return findPath(graph.getNode(startPosition), graph.getNode(target));
	}
	
	public static ArrayList<Pair> findPath(Node startNode, Node targetNode)
	{
		HashMap<Boolean> closedList = new HashMap<Boolean>();
		Heap openList = new Heap();
		
		openList.insert(new HeapNode(startNode, 0, estimatedScore(startNode, targetNode), null));
		
		while(!openList.isEmpty())
		{
			HeapNode currentNode = openList.getMin();
			
			if(currentNode.getGraphNode() == targetNode)
				return reconstructPath(currentNode);
			
			openList.removeMin();
			closedList.put(currentNode.getGraphNode().getID(), true);
			
			for(int i=0; i<Direction.values().length; i++)
			{
				Direction dir = Direction.values()[i];
				if(!currentNode.getGraphNode().has(dir) || !currentNode.getGraphNode().get(dir).isFree())
					continue;
				
				Node neighbor = currentNode.getGraphNode().get(dir);
				
				if(closedList.containsKey(neighbor.getID()))
					continue;
					
				int newCurrentScore = currentNode.getCurrentScore() + edgeScore;
				
				HeapNode neighborEntry = null;
				if(openList.contains(neighbor.getID()))
					neighborEntry = openList.get(openList.get(neighbor.getID()));
				
				if(neighborEntry==null)
				{
					openList.insert(new HeapNode(neighbor, newCurrentScore, newCurrentScore + estimatedScore(neighbor, targetNode), currentNode));
				}
				else if(newCurrentScore < neighborEntry.getCurrentScore())
				{
					openList.update(neighbor.getID(), newCurrentScore, newCurrentScore + estimatedScore(neighbor, targetNode), currentNode);
				}
			}
		}	

		return null;
	}
	
	private static ArrayList<Pair> reconstructPath(HeapNode node)
	{
		ArrayList<Pair> path = new ArrayList<Pair>();
		
		path.add(node.getGraphNode().getID());
		
		while(node.getPredecessor() != null)
		{
			node = node.getPredecessor();
			path.add(0, node.getGraphNode().getID());
		}
		
		return path;
	}
	
	private static int estimatedScore(Node currentNode, Node targetNode)
	{
		int x = Math.abs(currentNode.getID().getX() - targetNode.getID().getX());
		int y = Math.abs(currentNode.getID().getY() - targetNode.getID().getY());
		
		return x+y;
	}
}