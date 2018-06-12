package datastructures.concrete.dictionaries;


import java.util.Iterator;
import java.util.NoSuchElementException;

import datastructures.concrete.KVPair;
import datastructures.interfaces.IDictionary;
import misc.exceptions.NoSuchKeyException;
//import misc.exceptions.NotYetImplementedException;

/**
 * See IDictionary for more details on what this class should do
 */
public class ArrayDictionary<K, V> implements IDictionary<K, V> {
    // You may not change or rename this field: we will be inspecting
    // it using our private tests.
    private Pair<K, V>[] pairs;
    private int size = 0;
    private int capacity = 0;
    private static final int INIT_CAPACITY = 10;
    // You're encouraged to add extra fields (and helper methods) though!

    public ArrayDictionary() {
        this.capacity = INIT_CAPACITY;
        pairs = makeArrayOfPairs(this.capacity);  
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain Pair<K, V> objects.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private Pair<K, V>[] makeArrayOfPairs(int arraySize) {
        // It turns out that creating arrays of generic objects in Java
        // is complicated due to something known as 'type erasure'.
        //
        // We've given you this helper method to help simplify this part of
        // your assignment. Use this helper method as appropriate when
        // implementing the rest of this class.
        //
        // You are not required to understand how this method works, what
        // type erasure is, or how arrays and generics interact. Do not
        // modify this method in any way.
        return (Pair<K, V>[]) (new Pair[arraySize]);
    }
    
    @Override
    public V get(K key) {
        int index = getKeyIndex(key);
        if (index == -1) {
            throw new NoSuchKeyException();
        }
        return pairs[index].value;
    }

    @Override
    public void put(K key, V value) {
        int index = getKeyIndex(key);
        if (index != -1) {
            pairs[index].value = value;
        } else {
            pairs[this.size] = new Pair<K, V>(key, value);
            this.size++;
            if (this.size == capacity) {
                capacity = 2 * capacity;
                this.pairs = newCap(capacity);
            }
        }     
    }
    
    @Override
    public V remove(K key) {
        int index = getKeyIndex(key);
        if (index == -1) {
            throw new NoSuchKeyException();
        }
        int removedIndex = index;
        V removedValue = pairs[removedIndex].value;
        pairs[removedIndex] = pairs[size - 1];
        size--;
        return removedValue;
    }

    @Override
    public boolean containsKey(K key) {
        return getKeyIndex(key) != -1;
    }
    
    @Override
    public int size() {
        return size;
    }
    
    // helper method 
    private Pair<K, V>[] newCap(int capacityNew) {
        Pair<K, V>[]pairsNew = makeArrayOfPairs(capacityNew);
        System.arraycopy(this.pairs, 0, pairsNew, 0, this.size);
        return pairsNew;
    }
    
    // helper method
    private int getKeyIndex(K key) {
        int repeat = this.size;
        for (int i = 0; i < repeat; i++) {
            if ((pairs[i].key == null && key == null) || (pairs[i].key != null && pairs[i].key.equals(key))) {
                return i;
            }
        }
        return -1;
    }
    
    public Iterator<KVPair<K, V>> iterator() {
        return new ArrayDictionaryIterator<K, V>(this.size, this.pairs);
    }
    
    private static class ArrayDictionaryIterator<K, V> implements Iterator<KVPair<K, V>> {
        private int current;
        private int size;
        private Pair<K, V>[] pairs;
        
        public ArrayDictionaryIterator(int size, Pair<K, V>[] pairs) {
            this.current = 0;
            this.size = size;
            this.pairs = pairs;
        }
        
        public boolean hasNext() {
            return this.current < size;
        }
        
        public KVPair<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            
            Pair<K, V> result = pairs[current];
            current++;
            return new KVPair<K, V>(result.key, result.value);
            /*
            KVPair<K, V> result = new KVPair<K, V>(pairs[current].key, pairs[current].value);
            current++;
            return result;
            */
        }
        
    }

    private static class Pair<K, V> {
        public K key;
        public V value;

        // You may add constructors and methods to this class as necessary.
        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return this.key + "=" + this.value;
        }
    }
}