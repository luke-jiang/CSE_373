package datastructures.dictionaries;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import misc.BaseTest;
import datastructures.concrete.KVPair;
import datastructures.interfaces.IDictionary;
import misc.exceptions.NoSuchKeyException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Note: Rather then running this class directly, run either
 * TestArrayDictionary or TestChainedHashDictionary.
 *
 * Both classes will inherit (and re-use) the tests defined here.
 */
public abstract class TestDictionary extends BaseTest {
    protected abstract <K, V> IDictionary<K, V> newDictionary();

    protected IDictionary<String, String> makeBasicDictionary() {
        IDictionary<String, String> dict = this.newDictionary();
        dict.put("keyA", "valA");
        dict.put("keyB", "valB");
        dict.put("keyC", "valC");
        return dict;
    }

    protected <K, V> void assertDictMatches(K[] expectedKeys, V[] expectedValues, IDictionary<K, V> actual) {
        if (expectedKeys.length != expectedValues.length) {
            throw new IllegalArgumentException("Error! Number of expected keys and values don't match!");
        }

        assertEquals(expectedKeys.length, actual.size());
        assertEquals(expectedKeys.length == 0, actual.isEmpty());

        for (int i = 0; i < expectedKeys.length; i++) {
            K key = expectedKeys[i];
            V value = expectedValues[i];
            try {
                V actualValue = actual.get(key);
                assertEquals(
                        String.format(
                                "Dictionary contains key-value pair '%s' => '%s'; expected value '%s'",
                                key,
                                value,
                                actualValue),
                        value,
                        actualValue);
            } catch (NoSuchKeyException ex) {
                String message = String.format(
                        "Expected key '%s' was missing from dictionary",
                        key);
                throw new AssertionError(message, ex);
            }
        }
    }

    @Test(timeout=SECOND)
    public void testPutAndGetBasic() {
        IDictionary<String, String> dict = this.makeBasicDictionary();

        this.assertDictMatches(
                new String[] {"keyA", "keyB", "keyC"},
                new String[] {"valA", "valB", "valC"},
                dict);
    }

    @Test(timeout=SECOND)
    public void testPutAndGetSameKeyRepeated() {
        IDictionary<Integer, Integer> dict = this.newDictionary();

        // First insertion
        dict.put(3, 4);
        this.assertDictMatches(
                new Integer[] {3},
                new Integer[] {4},
                dict);

        // Second insertion
        dict.put(3, 5);
        this.assertDictMatches(
                new Integer[] {3},
                new Integer[] {5},
                dict);

        // Third insertion
        dict.put(3, 4);
        this.assertDictMatches(
                new Integer[] {3},
                new Integer[] {4},
                dict);
    }

    @Test(timeout=SECOND)
    public void testPutAndGetSameKeyRepeatedMany() {
        IDictionary<String, String> dict = this.newDictionary();
        dict.put("a", "1");
        dict.put("b", "1");
        dict.put("a", "2");
        dict.put("a", "3");
        dict.put("c", "1");
        dict.put("a", "4");
        dict.put("c", "2");

        this.assertDictMatches(
                new String[] {"a", "b", "c"},
                new String[] {"4", "1", "2"},
                dict);
    }

    @Test(timeout=SECOND)
    public void testGetErrorHandling() {
        IDictionary<String, Integer> dict = this.newDictionary();

        try {
            dict.get("foo");
            fail("Expected NoSuchKeyException");
        } catch (NoSuchKeyException ex) {
            // This is ok: do nothing
        }

        dict.put("foo", 3);
        dict.put("bar", 3);

        try {
            dict.get("qux");
            fail("Expected NoSuchKeyException");
        } catch (NoSuchKeyException ex) {
            // This is ok: do nothing
        }
    }

    @Test(timeout=10 * SECOND)
    public void testPutAndGetMany() {
        IDictionary<Integer, Integer> dict = this.newDictionary();
        int cap = 10000;

        for (int i = 0; i < cap; i++) {
            dict.put(i, i * 2);
        }

        for (int i = cap - 1; i >= 0; i--) {
            int value = dict.get(i);
            assertEquals(i * 2, value);
        }

        assertEquals(cap, dict.size());
        assertFalse(dict.isEmpty());
    }

    @Test(timeout=SECOND)
    public void testRemoveBasic() {
        IDictionary<String, String> dict = this.makeBasicDictionary();

        assertEquals("valB", dict.remove("keyB"));
        this.assertDictMatches(
                new String[] {"keyA", "keyC"},
                new String[] {"valA", "valC"},
                dict);

        assertEquals("valA", dict.remove("keyA"));
        this.assertDictMatches(
                new String[] {"keyC"},
                new String[] {"valC"},
                dict);

        assertEquals("valC", dict.remove("keyC"));
        this.assertDictMatches(
                new String[] {},
                new String[] {},
                dict);
    }

    @Test(timeout=SECOND)
    public void testRemoveDuplicate() {
        IDictionary<String, String> dict = this.newDictionary();
        dict.put("a", "1");
        dict.put("b", "2");
        dict.put("c", "3");
        dict.put("a", "4");
        dict.put("d", "5");
        dict.put("b", "6");

        this.assertDictMatches(
                new String[] {"a", "b", "c", "d"},
                new String[] {"4", "6", "3", "5"},
                dict);

        assertEquals("4", dict.remove("a"));
        this.assertDictMatches(
                new String[] {"b", "c", "d"},
                new String[] {"6", "3", "5"},
                dict);

        assertEquals("6", dict.remove("b"));
        this.assertDictMatches(
                new String[] {"c", "d"},
                new String[] {"3", "5"},
                dict);

    }

    @Test(timeout=SECOND)
    public void testRemoveErrorHandling() {
        IDictionary<Integer, String> list = this.newDictionary();
        list.put(3, "a");

        try {
            list.remove(4);
            fail("Expected NoSuchKeyException");
        } catch (NoSuchKeyException ex) {
            // Do nothing: this is ok
        }

        list.remove(3);

        try {
            list.remove(3);
            fail("Expected NoSuchKeyException");
        } catch (NoSuchKeyException ex) {
            // Do nothing: this is ok
        }
    }


    @Test(timeout=5 * SECOND)
    public void testAddGetMany() {
        int cap = 15000;
        IDictionary<Integer, Integer> dict = this.newDictionary();

        for (int repeats = 0; repeats < 3; repeats++) {
            for (int i = 0; i < cap; i++) {
                dict.put(i, i * 2);
            }

            for (int i = 0; i < cap; i++) {
                int value = dict.get(i);
                assertEquals(i * 2, value);
            }
        }
    }

    @Test(timeout=SECOND)
    public void testContainsKeyBasic() {
        IDictionary<String, Integer> dict = this.newDictionary();

        dict.put("a", 1);
        dict.put("b", 2);
        dict.put("c", 3);
        dict.put("a", 4);
        dict.remove("c");
        dict.put("c", 5);
        dict.put("d", 6);
        dict.put("a", 5);
        dict.remove("c");

        assertTrue(dict.containsKey("a"));
        assertTrue(dict.containsKey("b"));
        assertFalse(dict.containsKey("c"));
        assertTrue(dict.containsKey("d"));
        assertFalse(dict.containsKey("e"));
    }

    @Test(timeout=SECOND)
    public void testLargeKeys() {
        // Force keys to be two separate objects
        String key1 = "abcdefghijklmnopqrstuvwxyz";
        String key2 = key1 + "";

        IDictionary<String, String> dict = this.newDictionary();
        dict.put(key1, "value");

        assertEquals("value", dict.get(key1));
        assertEquals("value", dict.get(key2));

        assertTrue(dict.containsKey(key1));
        assertTrue(dict.containsKey(key2));

        assertEquals("value", dict.remove(key2));

        assertFalse(dict.containsKey(key1));
        assertFalse(dict.containsKey(key2));
    }

    @Test(timeout=SECOND)
    public void testNullKey() {
        IDictionary<String, String> dict = this.makeBasicDictionary();

        dict.put(null, "hello");
        dict.put(null, "world");

        assertEquals(4, dict.size());
        assertEquals("world", dict.get(null));
        assertTrue(dict.containsKey(null));
        assertEquals("world", dict.remove(null));
        assertFalse(dict.containsKey(null));
    }

    @Test(timeout=SECOND)
    public void testNonNullKeys() {
        IDictionary<Wrapper<String>, String> dict = this.newDictionary();
        dict.put(new Wrapper<>("foo"), "foo");
        dict.put(new Wrapper<>("bar"), "bar");

        assertEquals("foo", dict.get(new Wrapper<>("foo")));
        assertEquals("bar", dict.get(new Wrapper<>("bar")));

        dict.put(new Wrapper<>("foo"), "hello");

        assertEquals(2, dict.size());

        assertEquals("hello", dict.get(new Wrapper<>("foo")));
    }

    @Test(timeout=SECOND)
    public void testGetMany() {
        IDictionary<String, String> dict = this.makeBasicDictionary();
        int cap = 100000;

        for (int i = 0; i < cap; i++) {
            dict.put("keyC", "newValC");
        }

        for (int i = 0; i < cap; i++) {
            assertEquals("newValC", dict.get("keyC"));
        }
    }

    @Test(timeout=SECOND)
    public void testIterator() {
        IDictionary<String, Integer> dict = this.newDictionary();
        IDictionary<String, Integer> copy = this.newDictionary();
        for (int i = 0; i < 1000; i++) {
            dict.put("" + i, i);
            copy.put("" + i, i);
        }

        for (KVPair<String, Integer> pair : dict) {
            String key = pair.getKey();
            int actualValue = pair.getValue();
            int expectedValue = copy.get(key);

            assertEquals(expectedValue, actualValue);
            copy.remove(key);
        }

        assertTrue(copy.isEmpty());
    }

    @Test(timeout=SECOND)
    public void testIteratorUnusualKeys() {
        IDictionary<String, String> map = this.newDictionary();

        map.put(null, "hello");
        map.put("", "world");

        boolean metNullKey = false;
        boolean metEmptyKey = true;
        int numItems = 0;
        for (KVPair<String, String> pair : map) {
            if (pair.getKey() == null) {
                metNullKey = true;
                assertEquals("hello", pair.getValue());
            } else if (pair.getKey().equals("")) {
                metEmptyKey = true;
                assertEquals("world", pair.getValue());
            }

            numItems += 1;
        }

        assertEquals(2, numItems);
        assertTrue(metNullKey);
        assertTrue(metEmptyKey);
    }

    @Test(timeout=SECOND)
    public void testIteratorEndsCorrectly() {
        IDictionary<String, String> dict = this.makeBasicDictionary();

        Iterator<KVPair<String, String>> iter = dict.iterator();

        for (int i = 0; i < dict.size(); i++) {
            for (int j = 0; j < 1000; j++) {
                assertTrue(iter.hasNext());
            }
            iter.next();
        }

        for (int j = 0; j < 1000; j++) {
            assertFalse(iter.hasNext());
        }

        try {
            iter.next();
            fail("Expected NoSuchElementException");
        } catch (NoSuchElementException ex) {
            // This is ok; deliberately empty
        }
    }

    @Test(timeout=SECOND)
    public void testIteratorsAreIndependent() {
        IDictionary<String, String> dict = makeBasicDictionary();
        Iterator<KVPair<String, String>> iter1 = dict.iterator();
        Iterator<KVPair<String, String>> iter2 = dict.iterator();

        for (int i = 0; i < dict.size(); i++) {
            assertEquals(iter1.hasNext(), iter2.hasNext());
            assertEquals(iter1.next(), iter2.next());
        }

        assertFalse(iter1.hasNext());
        assertFalse(iter2.hasNext());

        dict.put("nextKey1", "val");
        dict.put("nextKey2", "val");

        assertEquals(5, dict.size());
        Iterator<KVPair<String, String>> iter3 = dict.iterator();
        Iterator<KVPair<String, String>> iter4 = dict.iterator();

        for (int i = 0; i < dict.size(); i++) {
            assertEquals(iter3.hasNext(), iter4.hasNext());
            assertEquals(iter3.next(), iter4.next());
        }

        assertFalse(iter3.hasNext());
        assertFalse(iter4.hasNext());
    }

    @Test(timeout=SECOND)
    public void testIteratorOverEmptyDictionary() {
        IDictionary<String, String> dict = this.newDictionary();

        for (int i = 0; i < 10; i++) {
            Iterator<KVPair<String, String>> iter = dict.iterator();
            for (int j = 0; j < 3; j++) {
                assertFalse(iter.hasNext());
            }
            for (int j = 0; j < 3; j++) {
                try {
                    iter.next();
                    fail("Expected NoSuchElementException");
                } catch (NoSuchElementException ex) {
                    // This is ok
                }
            }
        }
    }

    @Test(timeout=SECOND)
    public void testIteratorOverDictionaryWithOneElement() {
        IDictionary<String, String> dict = this.newDictionary();
        dict.put("foo", "bar");

        for (int i = 0; i < 10; i++) {
            Iterator<KVPair<String, String>> iter = dict.iterator();
            for (int j = 0; j < 3; j++) {
                assertTrue(iter.hasNext());
            }
            KVPair<String, String> pair = iter.next();
            assertEquals("foo", pair.getKey());
            assertEquals("bar", pair.getValue());

            for (int j = 0; j < 3; j++) {
                assertFalse(iter.hasNext());
            }
            for (int j = 0; j < 3; j++) {
                try {
                    iter.next();
                    fail("Expected NoSuchElementException");
                } catch (NoSuchElementException ex) {
                    // This is ok
                }
            }
        }
    }

    @Test(timeout=SECOND)
    public void testIteratorRunsMultipleTimes() {
        IDictionary<String, String> dict = this.newDictionary();
        for (int i = 0; i < 100; i++) {
            dict.put("key" + i, "val" + i);
        }

        List<KVPair<String, String>> expectedOutput = new ArrayList<>();
        for (KVPair<String, String> pair : dict) {
            expectedOutput.add(pair);
        }

        for (int i = 0; i < 3; i++) {
            Iterator<KVPair<String, String>> iter = dict.iterator();
            for (int j = 0; j < expectedOutput.size(); j++) {
                assertTrue(iter.hasNext());
                assertEquals(expectedOutput.get(j), iter.next());
            }

            assertFalse(iter.hasNext());
        }
    }
}
