package datastructures.sorting;

import misc.BaseTest;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import misc.Searcher;

import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestTopKSortFunctionality extends BaseTest {
    
    protected <T> void assertListMatches(T[] expected, IList<T> actual) {
        assertEquals(expected.length, actual.size());
        assertEquals(expected.length == 0, actual.isEmpty());

        for (int i = 0; i < expected.length; i++) {
            try {
                assertEquals("Item at index " + i + " does not match", expected[i], actual.get(i));
            } catch (Exception ex) {
                String errorMessage = String.format(
                        "Got %s when getting item at index %d (expected '%s')",
                        ex.getClass().getSimpleName(),
                        i,
                        expected[i]);
                throw new AssertionError(errorMessage, ex);
            }
        }
    }
    
    @Test(timeout=SECOND)
    public void testSimpleUsage() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }

        IList<Integer> top = Searcher.topKSort(5, list);
        assertEquals(5, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(15 + i, top.get(i));
        }
    }
    
    @Test(timeout=SECOND)
    public void testNegativeKThrowException() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        
        try {
            Searcher.topKSort(-1, list);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException ex) {
            // do nothing
        }
    }
    
    @Test(timeout=SECOND)
    public void testKLargerOrEqualToInputSize() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        
        IList<Integer> top1 = Searcher.topKSort(20, list);
        IList<Integer> top2 = Searcher.topKSort(21, list);
        
        assertEquals(20, top1.size());
        assertEquals(20, top2.size());
        
        for (int i = 0; i < 20; i++) {
            assertEquals(i, top1.get(i));
            assertEquals(i, top2.get(i));
        }
    }
    
    @Test(timeout=SECOND)
    public void testKIsZeroOrOne() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        
        IList<Integer> top1 = Searcher.topKSort(0, list);
        IList<Integer> top2 = Searcher.topKSort(1, list);
        
        assertEquals(0, top1.size());
        assertEquals(1, top2.size());
        assertEquals(19, top2.get(0));
    }
    
    @Test(timeout=SECOND)
    public void testInputSizeIsZeroOrOne() {
        IList<Integer> list = new DoubleLinkedList<>();
        
        IList<Integer> top1 = Searcher.topKSort(0, list);
        assertEquals(0, top1.size());
        IList<Integer> top3 = Searcher.topKSort(1, list);
        assertEquals(0, top3.size());
        
        list.add(1);
        
        IList<Integer> top2 = Searcher.topKSort(1, list);
        assertEquals(1, top2.size());
        assertEquals(1, top2.get(0));
        IList<Integer> top4 = Searcher.topKSort(1, list);
        assertEquals(1, top4.size());
        assertEquals(1, top4.get(0));
    }
    
    @Test(timeout=SECOND)
    public void testInputListPerserved() {
        IList<String> list = new DoubleLinkedList<>();
        Searcher.topKSort(0, list);
        assertListMatches(new String[] {}, list);
        
        list.add("a");
        list.add("b");
        Searcher.topKSort(2, list);
        assertListMatches(new String[] {"a", "b"}, list);
    }
    
    @Test(timeout=SECOND)
    public void testCompareToUsedForComparison() {
        IList<String> list = new DoubleLinkedList<>();
        list.add("a");
        list.add("c");
        list.add("b");
        list.add("d");
        
        IList<String> top = Searcher.topKSort(3, list);
        assertListMatches(new String[] {"b", "c", "d"}, top);
    }
    
    @Test(timeout=SECOND)
    public void testKSmallerThanInputSize() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        
        IList<Integer> top1 = Searcher.topKSort(19, list);
        
        assertEquals(19, top1.size());
        
        int j = 1;
        for (int i = 0; i < 19; i++) {
            assertEquals(j, top1.get(i));
            j++;
        }
    }
    
}