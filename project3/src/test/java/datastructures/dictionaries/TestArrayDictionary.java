package datastructures.dictionaries;

import datastructures.concrete.dictionaries.ArrayDictionary;
import datastructures.interfaces.IDictionary;

public class TestArrayDictionary extends TestDictionary {
    protected <K, V> IDictionary<K, V> newDictionary() {
        return new ArrayDictionary<>();
    }
}
