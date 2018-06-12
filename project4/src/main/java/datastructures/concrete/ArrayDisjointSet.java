package datastructures.concrete;

import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IDisjointSet;


/**
 * See IDisjointSet for more details.
 */
public class ArrayDisjointSet<T> implements IDisjointSet<T> {
    // Note: do NOT rename or delete this field. We will be inspecting it
    // directly within our private tests.
    private int[] pointers;

    // However, feel free to add more methods and private helper methods.
    // You will probably need to add one or two more fields in order to
    // successfully implement this class.
    private int itemCount;
    private IDictionary<T, Integer> nodeInventory; 

    public ArrayDisjointSet() {
        pointers = new int[20];
        itemCount = 0;
        nodeInventory = new ChainedHashDictionary<>();
    }

    @Override
    public void makeSet(T item) {
        if (nodeInventory.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        nodeInventory.put(item, itemCount);
        pointers[itemCount] = -1;
        itemCount++;
        if (itemCount >= pointers.length) {
            resize();
        }
    }

    @Override
    public int findSet(T item) {
        if (!nodeInventory.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        int index = nodeInventory.get(item);
        int i = index;
        // IList<Integer> path = new DoubleLinkedList<>();
        while (pointers[index] >= 0) {
            // path.add(index);
            index = pointers[index];
        }
        /*for (Integer node : path) {
            pointers[node] = index;
        }*/
        
        while (pointers[i] >= 0) {
            int temp = pointers[i];
            pointers[i] = index;
            i = temp;
        }
        return index;
    }

    @Override
    public void union(T item1, T item2) {
        if (!nodeInventory.containsKey(item1) || !nodeInventory.containsKey(item2)) {
            throw new IllegalArgumentException();
        }
        int set1 = findSet(item1);
        int set2 = findSet(item2);
        if (set1 == set2) {
            throw new IllegalArgumentException();
        }
        if (pointers[set1] == pointers[set2]) {
            pointers[set1]--;
        }
        if (pointers[set1] > pointers[set2]) {
            pointers[set1] = set2;
        } else {
            pointers[set2] = set1;
        }
    }
    
    private void resize() {
        int[] newPointers = new int[2 * pointers.length];
        System.arraycopy(pointers, 0, newPointers, 0, pointers.length);
        this.pointers = newPointers;
    }
}
