package datastructures.concrete.dictionaries;

import datastructures.concrete.KVPair;
import datastructures.interfaces.IDictionary;
//import misc.exceptions.NotYetImplementedException;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * See the spec and IDictionary for more details on what each method should do
 */
public class ChainedHashDictionary<K, V> implements IDictionary<K, V> {
    // You may not change or rename this field: we will be inspecting
    // it using our private tests.
    private IDictionary<K, V>[] chains;
    private int tableSize;
    private int elements;

    // You're encouraged to add extra fields (and helper methods) though!

    public ChainedHashDictionary() {
        this.tableSize = 31;
        chains = makeArrayOfChains(this.tableSize);
        elements = 0;
        for (int i = 0; i < this.tableSize; i++) {
            chains[i] = new ArrayDictionary<K, V>();
        }
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain IDictionary<K, V> objects.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private IDictionary<K, V>[] makeArrayOfChains(int size) {
        // Note: You do not need to modify this method.
        // See ArrayDictionary's makeArrayOfPairs(...) method for
        // more background on why we need this method.
        return (IDictionary<K, V>[]) new IDictionary[size];
    }

    @Override
    public V get(K key) {
        IDictionary<K, V> whichDictionary = chains[hash(key)];
        return whichDictionary.get(key);
    }

    @Override
    public void put(K key, V value) {
        IDictionary<K, V> whichDictionary = chains[hash(key)];
        int sizeBefore = whichDictionary.size();
        
        //if (!whichDictionary.containsKey(key)) {
        //    elements++;
        //}
        whichDictionary.put(key, value);
        if (sizeBefore != whichDictionary.size()) {
            elements++;
        }
        double lambda = (this.elements * 1.0) / this.tableSize;
        if (lambda > 2) {
            rehash();
        }
    }

    @Override
    public V remove(K key) {
        IDictionary<K, V> whichDictionary = chains[hash(key)];
        V result = whichDictionary.remove(key);
        elements--;
        return result;
    }

    @Override
    public boolean containsKey(K key) {
        IDictionary<K, V> whichDictionary = chains[hash(key)];
        return whichDictionary.containsKey(key);
    }

    @Override
    public int size() {
        return this.elements;
    }

    @Override
    public Iterator<KVPair<K, V>> iterator() {
        // Note: you do not need to change this method
        return new ChainedIterator<>(this.chains);
    }
    
    private int hash(K key) {
        if (key == null) {
            return 0;
        }
        int hash = key.hashCode() % this.tableSize;
        if (hash < 0) {
            return this.tableSize + hash;
        }
        return hash;
    }
    
    private void rehash() {
        IDictionary<K, V>[] oldChains = this.chains;
        this.tableSize *= 2;
        this.elements = 0;
        this.chains = makeArrayOfChains(this.tableSize);
        for (int i = 0; i < this.tableSize; i++) {
            this.chains[i] = new ArrayDictionary<K, V>();
        }
        for (int i = 0; i < oldChains.length; i++) {
            for (KVPair<K, V> pair : oldChains[i]) {
                put(pair.getKey(), pair.getValue());
            }
        }
    }

    /**
     * Hints:
     *
     * 1. You should add extra fields to keep track of your iteration
     *    state. You can add as many fields as you want. If it helps,
     *    our reference implementation uses three (including the one we
     *    gave you).
     *
     * 2. Before you try and write code, try designing an algorithm
     *    using pencil and paper and run through a few examples by hand.
     *
     *    We STRONGLY recommend you spend some time doing this before
     *    coding. Getting the invariants correct can be tricky, and
     *    running through your proposed algorithm using pencil and
     *    paper is a good way of helping you iron them out.
     *
     * 3. Think about what exactly your *invariants* are. As a
     *    reminder, an *invariant* is something that must *always* be 
     *    true once the constructor is done setting up the class AND 
     *    must *always* be true both before and after you call any 
     *    method in your class.
     *
     *    Once you've decided, write them down in a comment somewhere to
     *    help you remember.
     *
     *    You may also find it useful to write a helper method that checks
     *    your invariants and throws an exception if they're violated.
     *    You can then call this helper method at the start and end of each
     *    method if you're running into issues while debugging.
     *
     *    (Be sure to delete this method once your iterator is fully working.)
     *
     * Implementation restrictions:
     *
     * 1. You **MAY NOT** create any new data structures. Iterators
     *    are meant to be lightweight and so should not be copying
     *    the data contained in your dictionary to some other data
     *    structure.
     *
     * 2. You **MAY** call the `.iterator()` method on each IDictionary
     *    instance inside your 'chains' array, however.
     */
    private static class ChainedIterator<K, V> implements Iterator<KVPair<K, V>> {
        private IDictionary<K, V>[] chains;
        private int row;
        private int endRow;
        private Iterator<KVPair<K, V>> rowItr;

        public ChainedIterator(IDictionary<K, V>[] chains) {
            this.chains = chains;
            this.row = 0;
            this.endRow = this.chains.length - 1;
            this.rowItr = this.chains[0].iterator();
            while (!rowItr.hasNext() && row < endRow) {
                row++;
                rowItr = chains[row].iterator();
            }
        }

        @Override
        public boolean hasNext() {
            return this.rowItr.hasNext() || row != endRow; 
        }

        @Override
        public KVPair<K, V> next() {
            if (!rowItr.hasNext() && row == endRow) {
                throw new NoSuchElementException();
            }
            KVPair<K, V> ans = rowItr.next();
            while (!rowItr.hasNext() && row < endRow) {
                row++;
                rowItr = chains[row].iterator();
            }
            return ans;
        }
    }
}
