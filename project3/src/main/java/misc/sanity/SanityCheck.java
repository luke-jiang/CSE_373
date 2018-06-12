package misc.sanity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

// Checkstyle will report an error with this line:

public class SanityCheck {
    public static void main(String[] args) {
        // The following two lines should compile only if you correctly installed Java 8 or higher.
        Function<String, String> test = (a) -> a;
        System.out.println(test.apply("Java 8 or above is correctly installed!"));

        // The following line should compile only if you correctly imported this project as a Gradle project.
        //System.out.println(org.jfree.chart.ChartFactory.getChartTheme());

        // The following four lines should run, but checkstyle should complain about style errors in both lines.
        List<String> a = new ArrayList<String>();
        a.add("test"); 
        System.out.println(a.isEmpty());

        System.out.println("Sanity check complete: everything seems to have been configured correctly!");
    }
}
