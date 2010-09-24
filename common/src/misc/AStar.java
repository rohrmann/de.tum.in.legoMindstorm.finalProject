package misc;
import Graph.*;
import java.util.*;

public class AStar {
	
	public final static int edgeScore = 1;
	public final static int turnScore = 1; 
	
	public static ArrayList<Pair> findPath(Graph graph, Pair startPosition, Direction heading, Pair target)
	{
		return findPath(graph.getNode(startPosition), heading, graph.getNode(target));
	}
	
	public static ArrayList<Pair> findPath(Node startNode, Direction heading, Node targetNode)
	{
		HashMap<Boolean> closedList = new HashMap<Boolean>();
		Heap openList = new Heap();
		openList.insert(new HeapNode(startNode, 0, estimatedScore(heading, startNode, targetNode), null, heading));
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
				if(currentNode.getHeading() != dir)
					newCurrentScore += turnScore;	
					
				HeapNode neighborEntry = null;
				if(openList.contains(neighbor.getID()))
					neighborEntry = openList.get(openList.get(neighbor.getID()));
				
				if(neighborEntry==null)
				{
					openList.insert(new HeapNode(neighbor, newCurrentScore, newCurrentScore + estimatedScore(dir, neighbor, targetNode), currentNode, dir));
				}
				else if(newCurrentScore < neighborEntry.getCurrentScore())
				{
					openList.update(neighbor.getID(), newCurrentScore, newCurrentScore + estimatedScore(dir, neighbor, targetNode), currentNode, dir);
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
	
	private static int estimatedScore(Direction heading, Node currentNode, Node targetNode)
	{
		int x = Math.abs(currentNode.getID().getX() - targetNode.getID().getX());
		int y = Math.abs(currentNode.getID().getY() - targetNode.getID().getY());

		int hx = 0;
		int hy = 0;
		
		switch(heading)
		{
			case NORTH:
				hy = 1;
				break;
			case SOUTH:
				hy = -1;
				break;
			case EAST:
				hx = 1;
				break;
			default:
				hx = -1;
				break;
		}
		
		int dx = (int)Math.signum(targetNode.getID().getX() - currentNode.getID().getX());
		int dy = (int)Math.signum(targetNode.getID().getY() - currentNode.getID().getY());
		
		int turns = Math.abs(dx-hx)+Math.abs(dy-hy);
		turns = turns > 3 ? 2 : turns;
		
		return (x+y) * edgeScore + turns * turnScore;
	}
}