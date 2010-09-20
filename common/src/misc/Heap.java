package misc;
import Graph.*;

public class Heap {
	
	java.util.Vector nodes;
	java.util.Hashtable indices;
	
	public void insert(HeapNode node)
	{
		nodes.add(node);
		int pos = heapify(nodes.size()-1);
	}	
	
	private int heapify(int index)
	{
		return siftdown(siftup(index));
	}
	
	private int siftup(int index)
	{
		if(parent(index) != null && parent(index).getEstimatedScore() > ((HeapNode) nodes.get(index)).getEstimatedScore())
		{
			HeapNode temp = parent(index);
			nodes.set(parentIndex(index), nodes.get(index));
			nodes.set(index, temp);
			indices.remove(((HeapNode)nodes.get(index)).getGraphNode().getID());
			indices.put(((HeapNode)nodes.get(index)).getGraphNode().getID(), index);
			return siftup(parentIndex(index));
		}
		indices.remove(((HeapNode)nodes.get(index)).getGraphNode().getID());
		indices.put(((HeapNode)nodes.get(index)).getGraphNode().getID(), index);
		return index;
	}
	
	private int siftdown(int index)
	{
		if(leftChild(index) != null && leftChild(index).getEstimatedScore() < ((HeapNode) nodes.get(index)).getEstimatedScore())
		{
			HeapNode temp = leftChild(index);
			nodes.set(leftChildIndex(index), nodes.get(index));
			nodes.set(index, temp);
			indices.remove(((HeapNode)nodes.get(index)).getGraphNode().getID());
			indices.put(((HeapNode)nodes.get(index)).getGraphNode().getID(), index);
			return siftdown(leftChildIndex(index));
		}
		else if(rightChild(index) != null && rightChild(index).getEstimatedScore() < ((HeapNode) nodes.get(index)).getEstimatedScore())
		{
			HeapNode temp = rightChild(index);
			nodes.set(rightChildIndex(index), nodes.get(index));
			nodes.set(index, temp);
			indices.remove(((HeapNode)nodes.get(index)).getGraphNode().getID());
			indices.put(((HeapNode)nodes.get(index)).getGraphNode().getID(), index);
			return siftdown(rightChildIndex(index));
		}
		indices.remove(((HeapNode)nodes.get(index)).getGraphNode().getID());
		indices.put(((HeapNode)nodes.get(index)).getGraphNode().getID(), index);
		return index;
	}
	
	public HeapNode getMin()
	{
		return (HeapNode) (nodes.size() == 0 ? null : nodes.get(0));
	}
	
	public HeapNode removeMin()
	{
		HeapNode min = getMin();
		nodes.set(0,nodes.remove(nodes.size()-1));
		int pos = siftup(0);
		indices.remove(min.getGraphNode().getID());
		indices.remove(((HeapNode) nodes.get(pos)).getGraphNode().getID());
		indices.put(((HeapNode) nodes.get(pos)).getGraphNode().getID(),pos);
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
		return (HeapNode) nodes.get(index);
	}
	
	private int leftChildIndex(int index)
	{
		return (index+1)*2;
	}
	
	private HeapNode leftChild(int index)
	{
		return (HeapNode) (leftChildIndex(index) >= nodes.size() ? null : nodes.get(leftChildIndex(index)));  
	}
	
	private int rightChildIndex(int index)
	{
		return (index+1)*2+1;
	}
	
	private HeapNode rightChild(int index)
	{
		return (HeapNode) (rightChildIndex(index) >= nodes.size() ? null : nodes.get(rightChildIndex(index)));  
	}
	
	private int parentIndex(int index)
	{
		return (index+1)/2;
	}
	
	private HeapNode parent(int index)
	{
		return (HeapNode) (parentIndex(index) < 0 ? null : nodes.get(parentIndex(index)));
	}
}
