package misc;

import Graph.Pair;
import java.util.Hashtable;

public class HashMap<T> {

	Hashtable hashtable;
	
	public HashMap()
	{
		hashtable = new Hashtable();
	}
	
	public void put(Pair pair, T i)
	{
		hashtable.put(pair, i);
	}
	
	public void remove(Pair pair)
	{
		hashtable.put(pair, null);
	}
	
	public boolean containsKey(Pair pair)
	{
		return hashtable.get(pair) != null;
	}
	
	public T get(Pair pair)
	{
		return (T)hashtable.get(pair);
	}
}
