package analysis.experiments;

import analysis.utils.AnalysisUtils;
import analysis.utils.CsvWriter;
import datastructures.interfaces.IList;

public class Experiment3 {
    public static final int NUM_TRIALS = 5;
    public static final int NUM_TIMES_TO_REPEAT = 1000;
    public static final long MAX_LIST_SIZE = 20000;
    public static final long STEP = 100;

    public static void main(String[] args) {
        IList<Long> indices = AnalysisUtils.makeDoubleLinkedList(0L, MAX_LIST_SIZE, STEP);

        System.out.println("Starting experiment 3");
        IList<Long> testResults = AnalysisUtils.runTrials(indices, Experiment3::test, NUM_TRIALS);

        System.out.println("Saving results to file");
        CsvWriter writer = new CsvWriter();
        writer.addColumn("InputIndices", indices);
        writer.addColumn("TestResults", testResults);
        writer.writeToFile("experimentdata/experiment3.csv");

        System.out.println("All done!");
    }

    public static long test(long index) {
        // We don't include the cost of constructing the list when running this test
        IList<Long> list = AnalysisUtils.makeDoubleLinkedList(0L, MAX_LIST_SIZE, 1L);

        long start = System.currentTimeMillis();

        // We try getting the same thing multiple times mainly because a single get, by itself,
        // is too fast to reliably measure.
        long temp = 0L;
        for (int i = 0; i < NUM_TIMES_TO_REPEAT; i++) {
            temp += list.get((int) index);
        }

        // Returns time elapsed
        return System.currentTimeMillis() - start;
    }
}
