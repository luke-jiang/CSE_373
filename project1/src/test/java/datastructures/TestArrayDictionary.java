package datastructures;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import datastructures.concrete.dictionaries.ArrayDictionary;
import datastructures.interfaces.IDictionary;
import misc.BaseTest;
import misc.exceptions.NoSuchKeyException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestArrayDictionary extends BaseTest {
    private IDictionary<String, String> makeBasicDictionary() {
        IDictionary<String, String> dict = new ArrayDictionary<>();
        dict.put("keyA", "valA");
        dict.put("keyB", "valB");
        dict.put("keyC", "valC");
        return dict;
    }

    private <K, V> void assertDictMatches(K[] expectedKeys, V[] expectedValues, IDictionary<K, V> actual) {
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
    public void basicTestConstructor() {
        IDictionary<String, String> dict = this.makeBasicDictionary();
        this.assertDictMatches(
                new String[] {"keyA", "keyB", "keyC"},
                new String[] {"valA", "valB", "valC"},
                dict);
    }
    
    @Test(timeout=SECOND)
    public void basicTestPutUpdatesSize() {
        IDictionary<String, String> dict = new ArrayDictionary<>();
        int initSize = dict.size();
        dict.put("keyA", "valA");
        
        assertEquals(initSize + 1, dict.size());
    }
    
    @Test(timeout=SECOND)
    public void basicTestPutSameKey() {
        IDictionary<String, String> dict = new ArrayDictionary<>();
        dict.put("a", "b");
        int size = dict.size();
        
        dict.put("a", "c");
        assertEquals(size, dict.size());
        assertEquals("c", dict.get("a"));
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
        IDictionary<Integer, Integer> dict = new ArrayDictionary<>();

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
        IDictionary<String, String> dict = new ArrayDictionary<>();
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
        IDictionary<String, Integer> dict = new ArrayDictionary<>();

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
        IDictionary<Integer, Integer> dict = new ArrayDictionary<>();
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
        IDictionary<String, String> dict = new ArrayDictionary<>();
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
        IDictionary<Integer, String> list = new ArrayDictionary<>();
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
    public void testAddRemoveMany() {
        int cap = 15000;
        IDictionary<Integer, Integer> dict = new ArrayDictionary<>();

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
        IDictionary<String, Integer> dict = new ArrayDictionary<>();

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

        IDictionary<String, String> dict = new ArrayDictionary<>();
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

        assertEquals("world", dict.get(null));
        assertTrue(dict.containsKey(null));
        assertEquals("world", dict.remove(null));
        assertFalse(dict.containsKey(null));
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
}
