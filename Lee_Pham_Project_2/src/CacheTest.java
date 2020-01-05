import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;


/**
 * Code to test an <tt>LRUCache</tt> implementation.
 */
public class CacheTest {

    ArrayList<String> tracker;
	DataProvider<String, String> provider;
	Cache<String, String> cache;
	static final int maxCapacity = 5;
	int testNumMisses;
	int testNumHits;
	int counter;

	public CacheTest() {
		provider = new StringData();
		testNumMisses = 4;
		testNumHits = 0;
		counter = 4;
		cache = new LRUCache<String, String>(provider, 5);
		cache.get("a");
		cache.get("b");
		cache.get("c");
		cache.get("d");

		tracker = new ArrayList(10);
		tracker.add(0, "a");
		tracker.add(0, "b");
		tracker.add(0, "c");
		tracker.add(0, "d");


	}

	/** update tracker list and the element in it as tracker list act like the linklist in LRU cahce; */
	public void updateTracker(String a) {
		int countHit = testNumHits;
		ArrayList<String> test = tracker;
		for (int i = 0; i < tracker.size(); i++) {
			String currKey = test.get(i);
			if (currKey.equals(a))   //find if the key is already in list
			{
				test.remove(a);     //first remove the key
				test.add(0, a);
				//then put it at the beginning at it is the most recent use
				testNumHits += 1;    //--> represent cacheHit
			}
		}

		if (countHit == testNumHits) {
			test.add(0, a);
			testNumMisses += 1;
		}  // countHit ==0 --> cache Miss; add the key in front

		if (test.size() > maxCapacity)   //check if the size reach over capacity
		{
			test.remove(test.get(test.size() - 1));   // remove the last element as it is the least use
		}
		tracker = test;

	}

	/**Check if cache hit!
     * cache key --> boolean
     * if the key is already in the cache --> return true
	 * else return false
     * @param key
     * @return boolean
     */
	public boolean isHit(String key) {
		int miss = cache.getNumMisses();
		cache.get(key);
		counter +=1;

		return (cache.getNumMisses() == miss);
		// if the cache hits, the number of misses should stay the same
	}

    /**check if cache miss!
     *cache key --> boolean
     *if the key is not in cache --> cache misses --> return true
     * @param key
     * @return boolean
     */
	public boolean isMiss(String key) {
		return (!isHit(key));
		// the the cache misses, number of cache miss should increase by 1
	}


	@Test
	public void TestHitMiss() {
        //test isMiss
		assertEquals(isHit("a"), true);
		updateTracker("a");
		assertEquals(tracker.size(), 4);

		assertEquals(isHit("e"), false);
		updateTracker("e");
		assertEquals(isHit("d"), true);
		updateTracker("d");
		assertNotEquals(isHit("e"), false);
		updateTracker("e");

        //test number of miss and hit

		assertEquals(testNumHits, 3);
		assertEquals (cache.getNumMisses(), 5);

        //Test isMiss

		assertEquals(isMiss("d"), false);
		updateTracker("d");
		assertEquals(isMiss("e"), false);
		updateTracker("e");
		assertEquals(isMiss("f"), true);
		updateTracker("f");

        //test to see if the number of Hits in catch match up with the number of Hit in tracker
		assertEquals (testNumHits, counter - cache.getNumMisses());

        //test to see if getNumMisses is right by comparing to the testNumMisses

		assertEquals(testNumMisses, cache.getNumMisses());
		assertEquals (tracker.size(), 5);

		ArrayList test = new ArrayList();
		test.add("f");
		test.add("e");
		test.add("d");
		test.add("a");
		test.add("c");

        //check to see if the tracker array getting right value with cache
		assertEquals (tracker, test);

	}

	@Test
	public void leastRecentlyUsedIsCorrect() {
		assertEquals(cache.get("a"), "1");
		updateTracker("a");
		assertEquals(cache.get("c"), "3");
		updateTracker("c");
		assertEquals(cache.get("d"), "4");
		updateTracker("d");
		assertEquals(cache.get("f"), "6");
		updateTracker("f");
		assertEquals(cache.get("e"), "5");
		updateTracker("e");
		assertEquals(cache.get("b"), "2");
		updateTracker("b");


		assertEquals(cache.getNumMisses(), 7);


	}
    @Test
    /**
     * test key that is not in provider or null
     */
    public void testKeyNotExisting()
    {
        assertEquals(cache.get("abc"), null); //test getting something doesn't exist in provider
        //test if try to get null
        assertEquals(cache.get(null), null);
    }

	@Test
	public void testMultiCache()
	{	Cache <String,String> cache2 = new LRUCache<String, String>(provider, 1);
		assertEquals(cache2.get("a"), "1");
        //test 2 different cache using same provider
		assertEquals(cache.get("a"), cache2.get("a"));

	}

    @Test
    /**
     * test with different provider using different type of value
     */
    public void TestCompanyData()
    {
        DataProvider<String,Integer> provider = new CompanyData(); // Need to instantiate an actual DataProvider
        Cache<String,Integer> cache = new LRUCache<String,Integer>(provider, 10);
        assertEquals (cache.get("Trump"), new Integer(10));
        assertNotEquals (cache.get("Trang"), new Integer(504));
    }


}