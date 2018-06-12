package analysis.experiments;

import analysis.utils.AnalysisUtils;
import analysis.utils.CsvWriter;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;

public class Experiment4 {
    // Note: we're measuring memory usage, which is deterministic
    // (assuming we constructed our DoubleLinkedList correctly)
    // So, there's no need to conduct trials
    public static final long MAX_LIST_SIZE = 20000;
    public static final long STEP = 100;

    public static void main(String[] args) {
        IList<Long> listSizes = AnalysisUtils.makeDoubleLinkedList(0L, MAX_LIST_SIZE, STEP);

        System.out.println("Starting experiment 4, test 1");
        IList<Long> test1Results = AnalysisUtils.runSingleTrial(listSizes, Experiment4::test1);

        System.out.println("Starting experiment 4, test 2");
        IList<Long> test2Results = AnalysisUtils.runSingleTrial(listSizes, Experiment4::test2);

        System.out.println("Saving results to file");
        CsvWriter writer = new CsvWriter();
        writer.addColumn("InputListSize", listSizes);
        writer.addColumn("ResultsTest1", test1Results);
        writer.addColumn("ResultsTest2", test2Results);
        writer.writeToFile("experimentdata/experiment4.csv");

        System.out.println("All done!");
    }

    public static long test1(long size) {
        IList<Long> list = AnalysisUtils.makeDoubleLinkedList(0L, size, 1L);
        return AnalysisUtils.getApproximateMemoryUsed(list);
    }

    public static long test2(long size) {
        IDictionary<Long, Long> dictionary = AnalysisUtils.makeArrayDictionary(size);
        return AnalysisUtils.getApproximateMemoryUsed(dictionary);
    }
}
