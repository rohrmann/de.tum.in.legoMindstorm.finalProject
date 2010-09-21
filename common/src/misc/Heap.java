package misc;
import java.util.Vector;

import Graph.*;

public class Heap {
	
	Vector nodes;
	HashMap<Integer> indices;
	
	public Heap()
	{
		nodes = new Vector();
		indices = new HashMap<Integer>();
	}
	
	public void insert(HeapNode node)
	{
		nodes.addElement(node);
		indices.put(node.getGraphNode().getID(), nodes.size()-1);
		int pos = heapify(nodes.size()-1);
	}	
	
	private int heapify(int index)
	{
		return siftdown(siftup(index));
	}
	
	private int siftup(int index)
	{
		if(parent(index) != null && parent(index).getEstimatedScore() > ((HeapNode) nodes.elementAt(index)).getEstimatedScore())
		{
			HeapNode temp = parent(index);
			nodes.setElementAt(nodes.elementAt(index), parentIndex(index));
			nodes.setElementAt(temp, index);
			indices.remove(((HeapNode)nodes.elementAt(index)).getGraphNode().getID());
			indices.put(((HeapNode)nodes.elementAt(index)).getGraphNode().getID(), index);
			return siftup(parentIndex(index));
		}
		indices.remove(((HeapNode)nodes.elementAt(index)).getGraphNode().getID());
		indices.put(((HeapNode)nodes.elementAt(index)).getGraphNode().getID(), index);
		return index;
	}
	
	private int siftdown(int index)
	{
		if(leftChild(index) != null && leftChild(index).getEstimatedScore() < ((HeapNode) nodes.elementAt(index)).getEstimatedScore())
		{
			HeapNode temp = leftChild(index);
			nodes.setElementAt(nodes.elementAt(index), leftChildIndex(index));
			nodes.setElementAt(temp, index);
			indices.remove(((HeapNode)nodes.elementAt(index)).getGraphNode().getID());
			indices.put(((HeapNode)nodes.elementAt(index)).getGraphNode().getID(), index);
			return siftdown(leftChildIndex(index));
		}
		else if(rightChild(index) != null && rightChild(index).getEstimatedScore() < ((HeapNode) nodes.elementAt(index)).getEstimatedScore())
		{
			HeapNode temp = rightChild(index);
			nodes.setElementAt(nodes.elementAt(index), rightChildIndex(index));
			nodes.setElementAt(temp, index);
			indices.remove(((HeapNode)nodes.elementAt(index)).getGraphNode().getID());
			indices.put(((HeapNode)nodes.elementAt(index)).getGraphNode().getID(), index);
			return siftdown(rightChildIndex(index));
		}
		indices.remove(((HeapNode)nodes.elementAt(index)).getGraphNode().getID());
		indices.put(((HeapNode)nodes.elementAt(index)).getGraphNode().getID(), index);
		return index;
	}
	
	public HeapNode getMin()
	{
		return (HeapNode) (nodes.size() == 0 ? null : nodes.elementAt(0));
	}
	
	public HeapNode removeMin()
	{
		HeapNode min = getMin();
		
		nodes.setElementAt(nodes.elementAt(nodes.size()-1), 0);
		nodes.removeElementAt(nodes.size()-1);
		indices.remove(min.getGraphNode().getID());
		
		if(!nodes.isEmpty())
		{
			int pos = siftup(0);
			indices.remove(((HeapNode) nodes.elementAt(pos)).getGraphNode().getID());
			indices.put(((HeapNode) nodes.elementAt(pos)).getGraphNode().getID(),pos);
		}
		return min;
	}
	
	public boolean contains(Pair id)
	{
		return indices.containsKey(id);
	}
		 
	public boolean isEmpty()
	{
		return nodes.isEmpty();
	}
	
	public void update(Pair id, int currentScore, int estimatedScore, HeapNode predecessor)
	{
		int index = get(id);
		
		get(index).setCurrentScore(currentScore);
		get(index).setEstimatedScore(estimatedScore);
		get(index).setPredecessor(predecessor);
		
		heapify(index);
	}
	
	public int get(Pair id)
	{
		return (Integer) indices.get(id);
	}
	
	public HeapNode get(int index)
	{
		return (HeapNode) (index < 0 ? null : nodes.elementAt(index));
	}
	
	private int leftChildIndex(int index)
	{
		return (index+1)*2;
	}
	
	private HeapNode leftChild(int index)
	{
		return (HeapNode) (leftChildIndex(index) >= nodes.size() ? null : nodes.elementAt(leftChildIndex(index)));  
	}
	
	private int rightChildIndex(int index)
	{
		return (index+1)*2+1;
	}
	
	private HeapNode rightChild(int index)
	{
		return (HeapNode) (rightChildIndex(index) >= nodes.size() ? null : nodes.elementAt(rightChildIndex(index)));  
	}
	
	private int parentIndex(int index)
	{
		return (index+1)/2;
	}
	
	private HeapNode parent(int index)
	{
		return (HeapNode) (index <= 0 ? null : nodes.elementAt(parentIndex(index)));
	}
}
