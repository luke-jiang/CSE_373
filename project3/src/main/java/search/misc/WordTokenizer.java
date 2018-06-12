package search.misc;

import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;

public class WordTokenizer {
    public static IList<String> extract(String input) {
        IList<String> out = new DoubleLinkedList<>();
        for (String word : input.toLowerCase().split("\\s+")) {
            word = word.replaceAll("\\p{Punct}", "");
            if (!word.isEmpty()) {
                out.add(word);
            }
        }
        return out;
    }
}
