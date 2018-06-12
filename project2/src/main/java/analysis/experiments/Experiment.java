package analysis.experiments;

import analysis.utils.AnalysisUtils;
import analysis.utils.CsvWriter;
import datastructures.concrete.dictionaries.ArrayDictionary;
import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;

/**
 * Note: we have provided you with the same experiments and analysis infrastructure you used
 * in project 1.
 *
 * You do not need to conduct any experiments for project 2. However, we're providing the
 * code anyways in case you want to run some experiments yourselves.
 *
 * Feel free to edit this class in any way you wish.
 */
public class Experiment {
    public static final int NUM_TRIALS = 5;
    public static final long MAX_DICTIONARY_SIZE = 20000;
    public static final long STEP = 100;

    public static void main(String[] args) {
        IList<Long> dictionarySizes = AnalysisUtils.makeList(0L, MAX_DICTIONARY_SIZE, STEP);

        System.out.println("Testing adding many elements to an ArrayDictionary");
        IList<Long> test1Results = AnalysisUtils.runTrials(
                dictionarySizes, Experiment::testBuildingArrayDictionary, NUM_TRIALS);

        System.out.println("Testing adding many elements to a ChainedHashDictionary");
        IList<Long> test2Results = AnalysisUtils.runTrials(
                dictionarySizes, Experiment::testBuildingChainedHashDictionary, NUM_TRIALS);

        System.out.println("Saving experiment results to file");
        CsvWriter writer = new CsvWriter();
        writer.addColumn("FinalDictionarySize", dictionarySizes);
        writer.addColumn("ArrayDictionaryPut", test1Results);
        writer.addColumn("ChainedHashDictionaryPut", test2Results);
        writer.writeToFile("experimentdata/experiment-array-vs-chained-put.csv");

        System.out.println("All done!");
    }

    public static long testBuildingArrayDictionary(long dictionarySize) {
        long start = System.currentTimeMillis();
        IDictionary<Long, Long> dictionary = new ArrayDictionary<>();
        for (long i = 0L; i < dictionarySize; i++) {
            dictionary.put(i, 0L);
        }
        // Returns time elapsed
        return System.currentTimeMillis() - start;
    }

    public static long testBuildingChainedHashDictionary(long dictionarySize) {
        long start = System.currentTimeMillis();
        IDictionary<Long, Long> dictionary = new ChainedHashDictionary<>();
        for (long i = 0L; i < dictionarySize; i++) {
            dictionary.put(i, 0L);
        }
        // Returns time elapsed
        return System.currentTimeMillis() - start;
    }
}
