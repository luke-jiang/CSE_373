package datastructures;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;

/**
 * This file should contain any tests that check and make sure your
 * delete method is efficient.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDeleteStress extends TestDoubleLinkedList {
    protected IList<Integer> makeBigList() {
        
        IList<Integer> list = new DoubleLinkedList<>();
        int cap = 5000000;
        for (int i = 0; i < cap; i++) {
            list.insert(list.size(), i * 2);
        }

        return list;
    }

    @Test(timeout = 15 * SECOND)
    public void testDeleteAtFrontIsEfficient() {
        IList<Integer> list = makeBigList();
        int cap = 5000000;
        for (int i = 0; i < cap; i++) {
            list.delete(0);
        }
        assertEquals(0, list.size());
    }
    
    @Test(timeout = 15 * SECOND)
    public void testDeleteAtEndIsEfficient() {
        IList<Integer> list = makeBigList();
        int cap = 5000000;
        for (int i = cap - 1; i >= 0; i--) {
            list.delete(i);
        }
        assertEquals(0, list.size());
    }
    
    @Test(timeout = 15 * SECOND)
    public void testDeleteNearEndIsEfficient() {
        IList<Integer> list = makeBigList();
        int cap = 5000000;
        for (int i = cap - 1; i >= 3; i--) {
            list.delete(i - 3);
        }
        assertEquals(3, list.size());
    }

}
