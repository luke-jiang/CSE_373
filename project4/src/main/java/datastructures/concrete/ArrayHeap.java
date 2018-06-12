package datastructures.concrete;

import datastructures.interfaces.IPriorityQueue;
import misc.exceptions.EmptyContainerException;

/**
 * See IPriorityQueue for details on what each method must do.
 */
public class ArrayHeap<T extends Comparable<T>> implements IPriorityQueue<T> {
    // See spec: you must implement a implement a 4-heap.
    private static final int NUM_CHILDREN = 4;

    // You MUST use this field to store the contents of your heap.
    // You may NOT rename this field: we will be inspecting it within
    // our private tests.
    private T[] heap;

    // Feel free to add more fields and constants.
    private int initial = 20;
    private int size;

    public ArrayHeap() {
        this.heap = makeArrayOfT(initial);
        this.size = 0;
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain elements of type T.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private T[] makeArrayOfT(int heapsize) {
        // This helper method is basically the same one we gave you
        // in ArrayDictionary and ChainedHashDictionary.
        //
        // As before, you do not need to understand how this method
        // works, and should not modify it in any way.
        return (T[]) (new Comparable[heapsize]);
    }

    @Override
    public T removeMin() {
        if (this.size == 0) {
            throw new EmptyContainerException();
        }
        T result = this.heap[0];
        int hole = percolateDown(0, this.heap[this.size - 1]);
        this.heap[hole] = this.heap[this.size - 1];
        this.size--;
        return result;
    }

    @Override
    public T peekMin() {
        if (this.size == 0) {
            throw new EmptyContainerException();
        }
        return this.heap[0];
    }

    @Override
    public void insert(T item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (this.size == heap.length) {
            resize();
        }
        this.size++;
        int hole = percolateUp(this.size - 1, item);
        this.heap[hole] = item;
    }

    @Override
    public int size() {
        return this.size;
    }
    
    private void resize() {
        T[] newHeap = makeArrayOfT(2 * this.heap.length);
        for (int i = 0; i < this.heap.length; i++) {
            newHeap[i] = this.heap[i];
        }
        this.heap = newHeap;
    }
    
    private int percolateDown(int hole, T val) {
        while (NUM_CHILDREN * hole + 1 <= this.size - 1) {
            int target = NUM_CHILDREN * hole + 1;
            for (int i = 2; i <= NUM_CHILDREN; i++) {
                int nextIndex = NUM_CHILDREN * hole + i;
                if (nextIndex < this.size && lessThan(heap[nextIndex], heap[target])) {
                    target = nextIndex;
                }
            }
            if (lessThan(heap[target], val)) {
                heap[hole] = heap[target];
                hole = target;
            } else {
                break;
            }
        }
        return hole;
    }
    
    private int percolateUp(int hole, T val) {
        while (hole > 0 && lessThan(val, this.heap[(hole - 1) / NUM_CHILDREN])) {
            this.heap[hole] = this.heap[(hole - 1) / NUM_CHILDREN];
            hole = (hole - 1) / NUM_CHILDREN;
        }
        return hole;
    }
    
    private boolean lessThan(T a, T b) {
        return a.compareTo(b) < 0;
    }
}
