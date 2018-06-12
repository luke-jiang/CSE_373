package calculator;

import calculator.errors.EvaluationError;
import calculator.gui.ImageDrawer;
import calculator.interpreter.Calculator;
import datastructures.interfaces.IList;
import misc.BaseTest;
import org.junit.ComparisonFailure;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.awt.geom.Rectangle2D;
import java.util.Arrays;

import static org.junit.Assert.fail;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestCalculator extends BaseTest {
    /**
     * The exact output of your calculator can vary if you decided to go
     * above-and-beyond and add extra functionality.
     *
     * So, we need some way of checking your output against multiple
     * potentially correct values.
     *
     * This function succeeds if the actual value matches any one of the
     * given options.
     */

    protected <T> void assertEqualsOneOf(T[] options, T actual) {
        for (T option : options) {
            if (actual.equals(option)) {
                // Success
                return;
            }
        }
        throw new ComparisonFailure(
                "Actual value did not match any of the expected options",
                Arrays.toString(options),
                actual.toString());
    }

    @Test(timeout=SECOND)
    public void basicTestToDouble() {
        Calculator calc = new Calculator();
        assertEquals("5", calc.evaluate("3 + 2"));
    }
    
    
    @Test(timeout=SECOND)
    public void basicTestToSimplify() {
        Calculator calc = new Calculator();
        assertEquals("a + b", calc.evaluate("a + b"));
    }

    @Test(timeout=SECOND)
    public void basicTestSimplifyNewNode() {
        Calculator calc = new Calculator();
        calc.evaluate("y := x + 5"); // x+5
        assertEquals("1", calc.evaluate("x := 1")); 
        assertEquals("2", calc.evaluate("z := x + 1"));
        assertEquals("6", calc.evaluate("y"));
        
        assertEquals("5", calc.evaluate("x := 5"));
        assertEquals("2", calc.evaluate("z"));
        assertEquals("10", calc.evaluate("y"));
    }

    @Test(timeout=SECOND)
    public void testExample1BasicMath() {
        Calculator calc = new Calculator();
        assertEquals("17", calc.evaluate("3 + 2 * 7"));
        assertEquals("x + y", calc.evaluate("x + y"));
        assertEquals("1", calc.evaluate("x := 1"));
        assertEquals("1 + y", calc.evaluate("1 + y"));
        assertEquals("2", calc.evaluate("y := 2"));
        assertEquals("3", calc.evaluate("x + y"));
    }
   
    @Test(timeout=SECOND)
    public void testExample2VariableRedefinition() {
        Calculator calc = new Calculator();
        assertEquals("x + 3", calc.evaluate("y := x + 3"));
        assertEquals("x + 3", calc.evaluate("y"));
        assertEquals("4", calc.evaluate("x := 4"));
        assertEquals("7", calc.evaluate("y"));
        assertEquals("8", calc.evaluate("x := 8"));
        assertEquals("11", calc.evaluate("y"));
    }


    @Test(timeout=SECOND)
    public void testExample3SymbolicEvaluation() {
        Calculator calc = new Calculator();

        assertEquals(
                "x + 2 + 3 * sin(x) + 50 / 70",
                calc.evaluate("y := x + 2 + 3 * sin(x) + (20 + 30)/70"));
        assertEquals(
                "x + 2 + 3 * sin(x) + 50 / 70",
                calc.evaluate("y"));
        assertEquals(
                "4",
                calc.evaluate("x := 4"));
        assertEquals(
                "6 + 3 * sin(4) + 50 / 70",
                calc.evaluate("y"));
        assertEquals(
                "4.44387822836193",
                calc.evaluate("toDouble(y)"));
    }
    
    @Test(timeout=SECOND)
    public void testBasicOperators() {
        Calculator calc = new Calculator();
        assertEquals("7", calc.evaluate("3 + 4"));
        assertEquals("-1", calc.evaluate("3 - 4"));
        assertEquals("12", calc.evaluate("3 * 4"));
        assertEquals("3 / 4", calc.evaluate("3 / 4"));
        assertEquals("0.75", calc.evaluate("toDouble(3 / 4)"));
        assertEqualsOneOf(
                new String[] {"3 ^ 4", "81"},
                calc.evaluate("3 ^ 4"));
        assertEquals("81", calc.evaluate("toDouble(3 ^ 4)"));
        assertEquals("-7", calc.evaluate("-(3 + 4)"));
        assertEqualsOneOf(
                new String[] {"3 * -4", "-12"},
                calc.evaluate("3 * -4"));
    }

    @Test(timeout=SECOND)
    public void testBasicFunctions() {
        Calculator calc = new Calculator();
        assertEquals("sin(42)", calc.evaluate("sin(42)"));
        assertEquals("cos(42)", calc.evaluate("cos(42)"));
        assertEquals("-0.9165215479156338", calc.evaluate("toDouble(sin(42))"));
        assertEquals("-0.39998531498835127", calc.evaluate("toDouble(cos(42))"));
        assertEquals("sin(3 - x)", calc.evaluate("sin(3 - x)"));
        calc.evaluate("x := 3");
        assertEquals("sin(0)", calc.evaluate("sin(3 - x)"));
        assertEquals("0", calc.evaluate("toDouble(sin(3 - x))"));
    }

    @Test(timeout=SECOND)
    public void testToDoubleFailsOnUnknown() {
        Calculator calc = new Calculator();
        try {
            calc.evaluate("toDouble(3 + a)");
            fail("Expected EvaluationError");
        } catch (EvaluationError err) {
            // Do nothing
        }

        try {
            calc.evaluate("toDouble(mystery(3))");
            fail("Expected EvaluationError");
        } catch (EvaluationError err) {
            // Do nothing
        }
    }

    @Test(timeout=SECOND)
    public void testSimplification() {
        Calculator calc = new Calculator();
        assertEqualsOneOf(
                new String[] {"2 + a + 8", "a + 10", "10 + a"},
                calc.evaluate("1 + 1 + a + 2 * 4"));
        assertEqualsOneOf(
                new String[] {"2 * (a + 4)", "2 * a + 8"},
                calc.evaluate("(1 + 1) * (a + 2 * (1 + 1))"));
    }

    @Test(timeout=SECOND)
    public void testPlotInputs() {
        FakeImageDrawer drawer = new FakeImageDrawer();

        Calculator calc = new Calculator();
        calc.setImageDrawer(drawer);

        calc.evaluate("plot(3 * x, x, 0, 10, 1)");
        IList<Double> xs = drawer.lastXValues;
        IList<Double> ys = drawer.lastYValues;
        for (int i = 0; i <= 10; i += 1) {
            assertEquals((double) i, xs.get(i));
            assertEquals(3.0 * i, ys.get(i));
        }

        calc.evaluate("c := 4");
        calc.evaluate("step := 0.25");
        calc.evaluate("plot(a^2 + c*a + c, a, -10, 10, step)");
        xs = drawer.lastXValues;
        ys = drawer.lastYValues;
        int index = 0;
        for (double i = -10; i <= 10; i += 0.25) {
            assertEquals(i, xs.get(index));
            assertEquals(i * i + 4 * i + 4, ys.get(index));
            index += 1;
        }
    }

    @Test(timeout=SECOND)
    public void testPlotCleansUpVariable() {
        Calculator calc = new Calculator();
        calc.setImageDrawer(new FakeImageDrawer());

        calc.evaluate("plot(3 * x, x, 0, 10, 1)");
        assertEquals("x", calc.evaluate("x"));
    }

    @Test(timeout=SECOND)
    public void testPlotFailsOnUnknown() {
        Calculator calc = new Calculator();
        calc.setImageDrawer(new FakeImageDrawer());

        try {
            calc.evaluate("plot(3 * x + a, x, 0, 10, 1)");
            fail("Expected EvaluationError");
        } catch (EvaluationError err) {
            // Do nothing
        }

        try {
            calc.evaluate("plot(3 * mystery(x), x, 0, 10, 1)");
            fail("Expected EvaluationError");
        } catch (EvaluationError err) {
            // Do nothing
        }

        try {
            calc.evaluate("plot(3 * x, x, 0, a, 1)");
            fail("Expected EvaluationError");
        } catch (EvaluationError err) {
            // Do nothing
        }
    }

    @Test(timeout=SECOND)
    public void testPlotFailsOnBadInput() {
        Calculator calc = new Calculator();
        calc.setImageDrawer(new FakeImageDrawer());

        try {
            calc.evaluate("plot(3 * x, x, 10, 0, 1)");
            fail("Expected EvaluationError");
        } catch (EvaluationError err) {
            // Do nothing
        }

        try {
            calc.evaluate("plot(3 * x, x, 0, 10, -11)");
            fail("Expected EvaluationError");
        } catch (EvaluationError err) {
            // Do nothing
        }

        calc.evaluate("x := 3");
        try {
            calc.evaluate("plot(3 * x, x, 0, 10, -11)");
            fail("Expected EvaluationError");
        } catch (EvaluationError err) {
            // Do nothing
        }
    }

    private static class FakeImageDrawer extends ImageDrawer {
        public IList<Double> lastXValues;
        public IList<Double> lastYValues;

        public FakeImageDrawer() {
            super(null, 800, 800);
        }

        @Override
        public void drawScatterPlot(String title, String xAxisLabel, String yAxisLabel,
                                    IList<Double> xValues, IList<Double> yValues,
                                    Rectangle2D drawReagion) {
            this.lastXValues = xValues;
            this.lastYValues = yValues;
        }
    }

}
