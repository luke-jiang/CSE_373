package datastructures.dictionaries;

import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestChainedHashDictionary extends TestDictionary {
    protected <K, V> IDictionary<K, V> newDictionary() {
        return new ChainedHashDictionary<>();
    }

    @Test(timeout=SECOND)
    public void testManyObjectsWithSameHashCode() {
        IDictionary<Wrapper<String>, Integer> map = this.newDictionary();
        for (int i = 0; i < 1000; i++) {
            map.put(new Wrapper<>("" + i, 0), i);
        }

        assertEquals(1000, map.size());

        for (int i = 999; i >= 0; i--) {
            String key = "" + i;
            assertEquals(i, map.get(new Wrapper<>(key, 0)));

            assertFalse(map.containsKey(new Wrapper<>(key + "a", 0)));
        }

        Wrapper<String> key1 = new Wrapper<>("abc", 0);
        Wrapper<String> key2 = new Wrapper<>("cde", 0);

        map.put(key1, -1);
        map.put(key2, -2);

        assertEquals(1002, map.size());
        assertEquals(-1, map.get(key1));
        assertEquals(-2, map.get(key2));
    }

    @Test(timeout=SECOND)
    public void testNegativeHashCode() {
        IDictionary<Wrapper<String>, String> dict = this.newDictionary();

        Wrapper<String> key1 = new Wrapper<>("foo", -1);
        Wrapper<String> key2 = new Wrapper<>("bar", -100000);
        Wrapper<String> key3 = new Wrapper<>("baz", 1);
        Wrapper<String> key4 = new Wrapper<>("qux", -4);

        dict.put(key1, "val1");
        dict.put(key2, "val2");
        dict.put(key3, "val3");

        assertTrue(dict.containsKey(key1));
        assertTrue(dict.containsKey(key2));
        assertTrue(dict.containsKey(key3));
        assertFalse(dict.containsKey(key4));

        assertEquals("val1", dict.get(key1));
        assertEquals("val2", dict.get(key2));
        assertEquals("val3", dict.get(key3));

        dict.remove(key1);
        assertFalse(dict.containsKey(key1));
    }

    @Test(timeout=10*SECOND)
    public void stressTest() {
        int limit = 1000000;
        IDictionary<Integer, Integer> dict = this.newDictionary();

        for (int i = 0; i < limit; i++) {
            dict.put(i, i);
            assertEquals(i, dict.get(i));
        }

        for (int i = 0; i < limit; i++) {
            assertFalse(dict.containsKey(-1));
        }

        for (int i = 0; i < limit; i++) {
            dict.put(i, -i);
        }

        for (int i = 0; i < limit; i++) {
            assertEquals(-i, dict.get(i));
            dict.remove(i);
        }
    }
}
