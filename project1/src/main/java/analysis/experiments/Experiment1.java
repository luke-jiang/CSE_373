package analysis.experiments;

import analysis.utils.AnalysisUtils;
import analysis.utils.CsvWriter;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;

public class Experiment1 {
    // Note: please do not change these constants (or the constants in any of the other experiments)
    // while working on your writeup

    public static final int NUM_TRIALS = 5;
    public static final long MAX_DICTIONARY_SIZE = 20000;
    public static final long STEP = 100;

    public static void main(String[] args) {
        IList<Long> dictionarySizes = AnalysisUtils.makeDoubleLinkedList(0L, MAX_DICTIONARY_SIZE, STEP);

        // Note: You may be wondering what doing 'Experiment1::test1' do?
        // Basically, what's happening here is that we're telling Java to:
        //
        // a. Take the Experiment1.test1 static method and construct an object representing that method
        // b. Pass that "function object" to the runTrials method
        //
        // Then, what 'runTrials' can do is use that wrapper object to directly call our test1 method
        // whenever it wants.
        //
        // This may seem a little weird at first: we're basically treating a method as a "value"
        // so we can pass it around. On the other hand, why not? We can pass all kinds of things
        // ranging from ints and Strings and objects into methods -- why can't we also pass methods
        // themselves?
        //
        // For more information on how AnalysisUtils.runTrials uses the function object, see its
        // method header comment.

        System.out.println("Starting experiment 1, test 1");
        IList<Long> test1Results = AnalysisUtils.runTrials(dictionarySizes, Experiment1::test1, NUM_TRIALS);

        System.out.println("Starting experiment 1, test 2");
        IList<Long> test2Results = AnalysisUtils.runTrials(dictionarySizes, Experiment1::test2, NUM_TRIALS);

        System.out.println("Saving experiment 1 results to file");
        CsvWriter writer = new CsvWriter();
        writer.addColumn("InputDictionarySize", dictionarySizes);
        writer.addColumn("Test1Results", test1Results);
        writer.addColumn("Test2Results", test2Results);
        writer.writeToFile("experimentdata/experiment1.csv");

        System.out.println("All done!");
    }

    public static long test1(long dictionarySize) {
        // We don't include the cost of constructing the dictionary when running this test
        IDictionary<Long, Long> dictionary = AnalysisUtils.makeArrayDictionary(dictionarySize);

        long start = System.currentTimeMillis();
        for (long i = 0L; i < dictionarySize; i++) {
            dictionary.remove(i);
        }
        // Returns time elapsed
        return System.currentTimeMillis() - start;
    }

    public static long test2(long dictionarySize) {
        IDictionary<Long, Long> dictionary = AnalysisUtils.makeArrayDictionary(dictionarySize);

        long start = System.currentTimeMillis();
        for (long i = dictionarySize - 1; i >= 0; i--) {
            dictionary.remove(i);
        }
        // Returns time elapsed
        return System.currentTimeMillis() - start;
    }
}
