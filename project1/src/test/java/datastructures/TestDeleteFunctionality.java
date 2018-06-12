package datastructures;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;

//import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * This class should contain all the tests you implement to verify that
 * your 'delete' method behaves as specified.
 *
 * This test _extends_ your TestDoubleLinkedList class. This means that when
 * you run this test, not only will your tests run, all of the ones in
 * TestDoubleLinkedList will also run.
 *
 * This also means that you can use any helper methods defined within
 * TestDoubleLinkedList here. In particular, you may find using the
 * 'assertListMatches' and 'makeBasicList' helper methods to be useful.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDeleteFunctionality extends TestDoubleLinkedList {
    
    protected IList<String> makeLongList() {
        IList<String> list = new DoubleLinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");
        list.add("g");
        list.add("h");
        assertListMatches(new String[] {"a", "b", "c", "d", "e", "f", "g", "h"}, list);
        return list;
    }
    
    @Test(timeout=SECOND)
    public void testDeleteBasic() {
        IList<String> list1 = this.makeLongList();
        
        String s1 = list1.delete(0);
        assertEquals(s1, "a");
        assertEquals(-1, list1.indexOf("a"));
        this.assertListMatches(new String[] {"b", "c", "d", "e", "f", "g", "h"}, list1);
        list1.insert(0, "z");
        this.assertListMatches(new String[] {"z", "b", "c", "d", "e", "f", "g", "h"}, list1);
        
        
        String s2 = list1.delete(2);
        assertEquals(s2, "c");
        assertEquals(-1, list1.indexOf("c"));
        this.assertListMatches(new String[] {"z", "b", "d", "e", "f", "g", "h"}, list1);
        
        String s3 = list1.delete(6);
        assertEquals(s3, "h");
        assertEquals(-1, list1.indexOf("h"));
        this.assertListMatches(new String[] {"z", "b", "d", "e", "f", "g"}, list1);
        list1.insert(6, "y");
        this.assertListMatches(new String[] {"z", "b", "d", "e", "f", "g", "y"}, list1);
        
        String s4 = list1.delete(4);
        assertEquals(s4, "f");
        assertEquals(-1, list1.indexOf("f"));
        this.assertListMatches(new String[] {"z", "b", "d", "e", "g", "y"}, list1); 
    }
        
    @Test(timeout=SECOND)    
    public void testDeleteBasic2() {    
        IList<String> list3 = this.makeBasicList();
        String s5 = list3.delete(1);
        this.assertListMatches(new String[] {"a", "c"}, list3);
        assertEquals(s5, "b");
        assertEquals(list3.size(), 2);
        
        String s6 = list3.delete(1);
        this.assertListMatches(new String[] {"a"}, list3);
        assertEquals(s6, "c");
        assertEquals(list3.size(), 1);
        
        String s7 = list3.delete(0);
        this.assertListMatches(new String[] {}, list3);
        assertEquals(s7, "a");
        assertEquals(list3.size(), 0);
    }
    
    @Test(timeout=SECOND)
    public void testDeleteBasic3() {
        IList<String> list2 = this.makeLongList();
        list2.delete(0);
        list2.delete(6);
        list2.delete(0);
        list2.delete(4);
        list2.delete(0);
        list2.delete(2);
        list2.delete(0);
        assertListMatches(new String[] {"e"}, list2);
        list2.insert(0, "a");
        list2.insert(0, "b");
        list2.add("c");
        list2.add("d");
        assertListMatches(new String[] {"b", "a", "e", "c", "d"}, list2);
        list2.delete(2);
        list2.delete(2);
        assertListMatches(new String[] {"b", "a", "d"}, list2);   
    }
    
    @Test(timeout=SECOND)
    public void testDeleteOutOfBounds() {
        IList<String> list = this.makeBasicList();

        try {
            list.delete(-1);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException ex) {
            // Do nothing: this is ok
        }

        try {
            list.delete(3);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException ex) {
            // Do nothing: this is ok
        }
        
        try {
            list.delete(100);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException ex) {
            // Do nothing: this is ok
        }
    }
    
    @Test(timeout=SECOND)
    public void testDeleteOnEmptyList() {
        IList<String> list = new DoubleLinkedList<>();

        try {
            list.delete(0);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException ex) {
            // Do nothing: this is ok
        }
        
        try {
            list.delete(3);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException ex) {
            // Do nothing: this is ok
        }
        
        try {
            list.delete(100);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException ex) {
            // Do nothing: this is ok
        }
    }
    
    
}
