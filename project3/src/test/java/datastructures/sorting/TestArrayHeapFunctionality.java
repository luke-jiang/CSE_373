package datastructures.sorting;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import misc.BaseTest;
import misc.exceptions.EmptyContainerException;
import datastructures.concrete.ArrayHeap;
import datastructures.interfaces.IPriorityQueue;
import org.junit.Test;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestArrayHeapFunctionality extends BaseTest {
    protected <T extends Comparable<T>> IPriorityQueue<T> makeInstance() {
        return new ArrayHeap<>();
    }

    protected IPriorityQueue<String> makeBasicList() {
        IPriorityQueue<String> heap = new ArrayHeap<>();

        heap.insert("a");
        heap.insert("b");
        heap.insert("c");
        heap.insert("d");
        heap.insert("e");

        return heap;
    }
    
    @Test(timeout=SECOND)
    public void testBasicSize() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        assertEquals(1, heap.size());
        assertTrue(!heap.isEmpty());
    }
    
    void testResize() {
        IPriorityQueue<Integer> heap = makeInstance();
        int max = 20;
        for (int i = max; i >= 0; i--) {
            heap.insert(i);
            assertEquals(heap.peekMin(), i);
            assertEquals(heap.size(), max - i + 1);
        }
    }
    
    public void testInsertBasic() {
        IPriorityQueue<Integer> heap = makeInstance();
        int max = 4;
        for (int i = max; i >= 0; i--) {
            heap.insert(i);
            assertEquals(heap.peekMin(), i);
            assertEquals(heap.size(), max - i + 1);
        }
    }
    
    @Test(timeout=SECOND)
    public void testRemoveAndPeek() {
        IPriorityQueue<Integer> heap = makeInstance();
        for (int i = 4; i >= 0; i--) {
            heap.insert(i);
            assertEquals(heap.peekMin(), i);
        }
        for (int i = 0; i < 5; i++) {
            assertEquals(heap.peekMin(), i);
            assertEquals(heap.removeMin(), i);
        }
    }
    
    @Test(timeout=SECOND)
    public void testInsertDuplicate() {
        IPriorityQueue<Integer> heap = makeInstance();
        heap.insert(5);
        heap.insert(1);
        heap.insert(1);
        assertEquals(heap.removeMin(), 1);
        assertEquals(heap.removeMin(), 1);
    }
    
    
    @Test(timeout=SECOND)
    public void testEmpty() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
            heap.peekMin();
            fail("Exception!");
        } catch (EmptyContainerException ex){
            // do nothing is ok
        }
        
        try {
            heap.removeMin();
            fail("Exception!");
        } catch (EmptyContainerException ex) {
            // do nothing is ok
        }
    }
    
    @Test(timeout=SECOND)
    public void testNullInput() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
            heap.insert(null);
            fail("Exception!");
        } catch (IllegalArgumentException ex) {
            // do nothing
        }
    }
    
    @Test(timeout=SECOND)
    public void testMinHeapStructure() {
        IPriorityQueue<Integer> heap = makeInstance();
        
        for (int i = 0; i < 100; i++) {
            heap.insert(i);
        }
        
        for (int i = 0; i < 5; i++) {
            heap.insert(i);
        }
        
        int first = heap.removeMin();
        while (heap.size() > 0) {
            int next = heap.removeMin();
            assertTrue(first <= next);
            first = next;
        }
    }
    
    @Test(timeout=SECOND)
    public void testRandomElements() {
        IPriorityQueue<Integer> heap = makeInstance();
        heap.insert(-2);
        heap.insert(5000);
        heap.insert(3);
        heap.insert(-100);
        heap.insert(10);
        heap.insert(10);
        heap.insert(-4);
        
        assertEquals(-100, heap.removeMin());
        assertEquals(-4, heap.removeMin());
        assertEquals(-2, heap.removeMin());
        assertEquals(3, heap.removeMin());
        assertEquals(10, heap.removeMin());
        assertEquals(10, heap.removeMin());
        assertEquals(5000, heap.removeMin());
    }
}
