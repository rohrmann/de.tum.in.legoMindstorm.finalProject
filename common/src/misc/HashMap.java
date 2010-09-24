package misc;

import Graph.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;


public class HashMap<T> {

//	Vector keys;
//	Vector vals;
//	
//	public HashMap()
//	{
//		keys = new Vector();
//		vals = new Vector();
//	}
//	
//	public void put(Pair pair, T i)
//	{
//		keys.addElement(pair);
//		vals.addElement(i);
//	}
//	
//	public boolean containsKey(Pair p)
//	{
//		for(int i=0; i<keys.size(); i++)
//			if(keys.elementAt(i) == p) return true;
//		return false;
//	}
//	
//	public void remove(Pair p)
//	{
//		int i = keys.indexOf(p);
//		vals.removeElementAt(i);
//		keys.removeElementAt(i);
//	}
//	
//	@SuppressWarnings("unchecked")
//	public T get(Pair p)
//	{
//		int i = keys.indexOf(p);
//		return (T)vals.elementAt(i);
//	}
	

	private int length = 14;
	private ArrayList<HashMapElement<T>>[] data;
	
	public HashMap()
	{
		data = new ArrayList[length];
		for(int i=0; i<data.length; i++)
			data[i] = new ArrayList<HashMapElement<T>>();
	}
	
	public void put(Pair pair, T i)
	{
		int h = hash(pair);
		if(!containsKey(pair))
		{
			data[h].add(new HashMapElement<T>(pair, i));
		}
		else
		{
			ListIterator<HashMapElement<T>> iterator = data[h].listIterator();
			while(iterator.hasNext())
			{
				if(iterator.next().key == pair)
					iterator.set(new HashMapElement<T>(pair, i));
			}
		}
	}
	
	public void remove(Pair pair)
	{
		ListIterator<HashMapElement<T>> iterator = data[hash(pair)].listIterator();
		while(iterator.hasNext())
		{
			if(iterator.next().key == pair)
				iterator.remove();
		}
	}
	
	public boolean containsKey(Pair pair)
	{
		ListIterator<HashMapElement<T>> iterator = data[hash(pair)].listIterator();
		while(iterator.hasNext())
		{
			if(iterator.next().key == pair)
				return true;
		}
		return false;
	}
	
	public T get(Pair pair)
	{
		ListIterator<HashMapElement<T>> iterator = data[hash(pair)].listIterator();
		while(iterator.hasNext())
		{
			if(iterator.next().key == pair)
				return iterator.previous().val;
		}
		return null;
	}
	
	private int hash(Pair pair)
	{
		return (pair.getX() + (pair.getY()%2 * length/2)) % length;
	}
	
	private class HashMapElement<T>
	{
		public Pair key;
		public T val;
		public HashMapElement(Pair key, T val){
			this.key = key;
			this.val = val;
		}
	}
}
