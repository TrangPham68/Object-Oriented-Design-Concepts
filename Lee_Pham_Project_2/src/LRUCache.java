import java.util.HashMap;
/**
 * An implementation of <tt>Cache</tt> that uses a least-recently-used (LRU)
 * eviction policy.
 */
public class LRUCache<T, U> implements Cache<T, U> {
	/**
	 * @param provider the data provider to consult for a cache miss
	 * @param capacity the exact number of (key,value) pairs to store in the cache
	 */
	private DataProvider _provider;
	private int _capacity;
	private Node end = null;
	private Node begin = null;
	private int miss = 0;
	private HashMap<T, Node> mapTest = new HashMap<T, Node>();

	public LRUCache(DataProvider<T, U> provider, int capacity) {
		_provider = provider;
		_capacity = capacity;
		if (_capacity <= 0) {
			throw new IllegalArgumentException();
		}
	}
	/**
	 * Returns the value associated with the specified key.
	 *
	 * @param key the key
	 * @return the value associated with the key
	 */
	public U get(T key) {
		if (mapTest.containsKey(key)) {
			Node n = mapTest.get(key); //return the associate value
			remove(n);  //got remove first
			setHead(n); //then got add to head
			overloadRemove(); // remove the last key if capacity is overflown
			return (U) n.value;
		}

		else {
			miss += 1;
			U value = (U) _provider.get(key); //find key in provider because it is not in cache
			Node fin = new Node(key, value);
			mapTest.put(key, fin);
			if (miss == 1) {
				end = fin;
			}
			setHead(fin);
			overloadRemove(); //remove the last key if capacity is overflown
			return value;
		}
	}
	/**
     * Check to see if the cache is full
     * if yet, remove the last node which is the least used one
     */
	private void overloadRemove()
    {
        if (mapTest.size() > _capacity)  //remove the last one when the list is empty
        {
            mapTest.remove(end.key);
        }
    }
    /**
     * Sets the New node to the head of the list
     * @param n
     */
	private void setHead(Node n)
	{
		n.next = begin;
		n.pre = null;
		//begin = n; //why do u set begin = n?
		if(begin!=null) //how to know end and begin? //
		{
			begin.pre = n;
		}
		begin = n;
		if(end == null)     //end = n.pre
		{
			end = begin;
		}
	}
	/**
	 * Returns the number of cache misses since the object's instantiation.
	 * @return the number of cache misses since the object's instantiation.
	 */
	public int getNumMisses ()
	{
		return miss;
	}

    /**
     * Removes the given Node
     * @param n
     */
	private void remove(Node n)
	{
		if(n.next!=null)
		{
			n.next.pre = n.pre;
		}
		else
		{
			end=n.pre;//if n is last and got remove --> last one is the previous one
		}
		if(n.pre!=null)
		{
			n.pre.next = n.next;
		}
		else
		{
			begin = n.next;
		}
	}
}
