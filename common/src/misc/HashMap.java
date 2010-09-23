package misc;

import Graph.Pair;

import java.util.Vector;


public class HashMap<T> {

	Vector keys;
	Vector vals;
	
	public HashMap()
	{
		keys = new Vector();
		vals = new Vector();
	}
	
	public void put(Pair pair, T i)
	{
		keys.addElement(pair);
		vals.addElement(i);
	}
	
	public boolean containsKey(Pair p)
	{
		for(int i=0; i<keys.size(); i++)
			if(keys.elementAt(i) == p) return true;
		return false;
	}
	
	public void remove(Pair p)
	{
		int i = keys.indexOf(p);
		vals.removeElementAt(i);
		keys.removeElementAt(i);
	}
	
	@SuppressWarnings("unchecked")
	public T get(Pair p)
	{
		int i = keys.indexOf(p);
		return (T)vals.elementAt(i);
	}
	
//	Hashtable hashtable;
//	
//	private int length = 14;
//	private ArrayList<HashMapElement<T>>[] data;
//	
//	public HashMap()
//	{
//		data = new ArrayList[length];
//		for(int i=0; i<data.length; i++)
//			data[i] = new ArrayList<HashMapElement<T>>();
//	}
//	
//	public void put(Pair pair, T i)
//	{
//		int h = hash(pair);
//		if(!containsKey(pair))
//		{
//			data[hash(pair)].add(new HashMapElement<T>(pair, i));
//		}
//			
//	}
//	
//	public void remove(Pair pair)
//	{
//		int h = hash(pair);
//		
//		Iterator<>
//		data[h].add(new HashMapElement<T>(pair, i));
//	}
//	
//	public boolean containsKey(Pair pair)
//	{
//		return hashtable.get(pair) != null;
//	}
//	
//	public T get(Pair pair)
//	{
//		return (T)hashtable.get(pair);
//	}
//	
//	private int hash(Pair pair)
//	{
//		return (pair.getX() + (pair.getY()/3 * 7)) % length;
//	}
//	
//	private class HashMapElement<T>
//	{
//		public Pair key;
//		public T val;
//		public HashMapElement(Pair key, T val){
//			this.key = key;
//			this.val = val;
//		}
//	}
}
