package datastructures.sorting;

import misc.BaseTest;
import misc.Searcher;

import org.junit.Test;

import datastructures.concrete.ArrayHeap;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import datastructures.interfaces.IPriorityQueue;

import static org.junit.Assert.assertTrue;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestSortingStress extends BaseTest {
    @Test(timeout=10*SECOND)
    public void testArrayHeapStresss() {
        IPriorityQueue<Integer> heap = new ArrayHeap<>();
            for (int i = 0; i < 10000000; i++) {
                heap.insert(i);
            }
            assertEquals(10000000, heap.size());
            for (int i = 0; i < 10000000; i++) {
                assertEquals(i, heap.removeMin());
            }
            assertTrue(heap.isEmpty());
    }
    
    @Test(timeout=10*SECOND)
    public void testTopKSortStress() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 1000000; i++) {
            list.add(i);
        }

        IList<Integer> top = Searcher.topKSort(5000, list);
        assertEquals(5000, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(1000000 - 5000 + i, top.get(i));
        }
    }

    @Test(timeout=10*SECOND)
    public void testLargeReverse() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 1999999; i >=0; i--) {
            list.add(i);
        }

        IList<Integer> top = Searcher.topKSort(100000, list);
        assertEquals(100000, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(2000000 - 100000 + i, top.get(i));
        }
    }
}