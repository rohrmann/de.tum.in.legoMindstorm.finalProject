import Graph.*;

public class Heap {
	
	java.util.Vector<HeapNode> nodes;
	java.util.HashMap<Pair, Integer> indices;
	
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
		if(parent(index) != null && parent(index).getDistance() > nodes.get(index).getDistance())
		{
			HeapNode temp = parent(index);
			nodes.set(parentIndex(index), nodes.get(index));
			nodes.set(index, temp);
			indices.remove(nodes.get(index).getGraphNode().getID());
			indices.put(nodes.get(index).getGraphNode().getID(), index);
			return siftup(parentIndex(index));
		}
		indices.remove(nodes.get(index).getGraphNode().getID());
		indices.put(nodes.get(index).getGraphNode().getID(), index);
		return index;
	}
	
	private int siftdown(int index)
	{
		if(leftChild(index) != null && leftChild(index).getDistance() < nodes.get(index).getDistance())
		{
			HeapNode temp = leftChild(index);
			nodes.set(leftChildIndex(index), nodes.get(index));
			nodes.set(index, temp);
			indices.remove(nodes.get(index).getGraphNode().getID());
			indices.put(nodes.get(index).getGraphNode().getID(), index);
			return siftdown(leftChildIndex(index));
		}
		else if(rightChild(index) != null && rightChild(index).getDistance() < nodes.get(index).getDistance())
		{
			HeapNode temp = rightChild(index);
			nodes.set(rightChildIndex(index), nodes.get(index));
			nodes.set(index, temp);
			indices.remove(nodes.get(index).getGraphNode().getID());
			indices.put(nodes.get(index).getGraphNode().getID(), index);
			return siftdown(rightChildIndex(index));
		}
		indices.remove(nodes.get(index).getGraphNode().getID());
		indices.put(nodes.get(index).getGraphNode().getID(), index);
		return index;
	}
	
	public HeapNode getMin()
	{
		return nodes.size() == 0 ? null : nodes.get(0);
	}
	
	public HeapNode removeMin()
	{
		HeapNode min = getMin();
		nodes.set(0,nodes.remove(nodes.size()-1));
		int pos = siftup(0);
		indices.remove(min.getGraphNode().getID());
		indices.remove(nodes.get(pos).getGraphNode().getID());
		indices.put(nodes.get(pos).getGraphNode().getID(),pos);
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
	
	public void updateDistance(Pair id, int newDistance)
	{
		int index = get(id);
		get(index).setDistance(newDistance);
		heapify(index);
	}
	
	public int get(Pair id)
	{
		return indices.get(id);
	}
	
	public HeapNode get(int index)
	{
		return nodes.get(index);
	}
	
	private int leftChildIndex(int index)
	{
		return (index+1)*2;
	}
	
	private HeapNode leftChild(int index)
	{
		return leftChildIndex(index) >= nodes.size() ? null : nodes.get(leftChildIndex(index));  
	}
	
	private int rightChildIndex(int index)
	{
		return (index+1)*2+1;
	}
	
	private HeapNode rightChild(int index)
	{
		return rightChildIndex(index) >= nodes.size() ? null : nodes.get(rightChildIndex(index));  
	}
	
	private int parentIndex(int index)
	{
		return (index+1)/2;
	}
	
	private HeapNode parent(int index)
	{
		return parentIndex(index) < 0 ? null : nodes.get(parentIndex(index));
	}
}
