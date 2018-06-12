package analysis.experiments;

import analysis.utils.AnalysisUtils;
import analysis.utils.CsvWriter;
import datastructures.interfaces.IList;

import java.util.Iterator;

public class Experiment2 {
    public static final int NUM_TRIALS = 5;
    public static final long MAX_LIST_SIZE = 20000;
    public static final long STEP = 100;

    public static void main(String[] args) {
        IList<Long> listSizes = AnalysisUtils.makeDoubleLinkedList(0L, MAX_LIST_SIZE, STEP);

        System.out.println("Starting experiment 2, test 1");
        IList<Long> test1Results = AnalysisUtils.runTrials(listSizes, Experiment2::test1, NUM_TRIALS);

        System.out.println("Starting experiment 2, test 2");
        IList<Long> test2Results = AnalysisUtils.runTrials(listSizes, Experiment2::test2, NUM_TRIALS);

        System.out.println("Starting experiment 2, test 3");
        IList<Long> test3Results = AnalysisUtils.runTrials(listSizes, Experiment2::test3, NUM_TRIALS);

        System.out.println("Saving experiment 2 results to file");
        CsvWriter writer = new CsvWriter();
        writer.addColumn("InputListSize", listSizes);
        writer.addColumn("Test1Results", test1Results);
        writer.addColumn("Test2Results", test2Results);
        writer.addColumn("Test3Results", test3Results);
        writer.writeToFile("experimentdata/experiment2.csv");

        System.out.println("All done!");
    }

    public static Long test1(long listSize) {
        // We don't include the cost of constructing the list when running this test
        IList<Long> list = AnalysisUtils.makeDoubleLinkedList(0L, listSize, 1L);

        long start = System.currentTimeMillis();
        long temp = 0L;
        for (int i = 0; i < listSize; i++) {
            temp += list.get(i);
        }

        // Returns time elapsed
        return System.currentTimeMillis() - start;
    }

    public static Long test2(long listSize) {
        IList<Long> list = AnalysisUtils.makeDoubleLinkedList(0L, listSize, 1L);

        long start = System.currentTimeMillis();
        long temp = 0L;
        Iterator<Long> iter = list.iterator();
        while (iter.hasNext()) {
            temp += iter.next();
        }

        // Returns time elapsed
        return System.currentTimeMillis() - start;
    }

    public static Long test3(Long listSize) {
        IList<Long> list = AnalysisUtils.makeDoubleLinkedList(0L, listSize, 1L);

        long start = System.currentTimeMillis();
        long temp = 0L;
        for (long item : list) {
            temp += item;
        }

        // Returns time elapsed
        return System.currentTimeMillis() - start;
    }
}
