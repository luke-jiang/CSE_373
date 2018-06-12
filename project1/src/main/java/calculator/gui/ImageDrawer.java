package calculator.gui;

import datastructures.interfaces.IList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.util.Iterator;

/**
 * This class contains some useful utility methods for drawing to the plotting window.
 *
 * You do not need to understand how this class works: you should just stick with
 * reading the method header comments.
 *
 * However, you may freely modify this class when working on your extra credit.
 * You may want to study how to use the JFreeChart library if you want to add
 * additional plotting functions.
 */
public class ImageDrawer implements ImageObserver {
    private static final int UNKNOWN_DIMENSION = -1;

    private Graphics graphics;
    private int width = UNKNOWN_DIMENSION;
    private int height = UNKNOWN_DIMENSION;

    /**
     * Creates a new ImageDrawer object based on the given panel.
     */
    public ImageDrawer(Image image) {
        this.graphics = image.getGraphics();
        this.width = image.getWidth(this);
        this.height = image.getHeight(this);
    }

    /**
     * Creates a new ImageDrawer object based on the given panel.
     */
    public ImageDrawer(Graphics graphics, int width, int height) {
        this.graphics = graphics;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns a Graphics object that can draw on the panel.
     * For more details on how to use the Graphics object, try reviewing
     * the material from CSE 142.
     *
     * This project does not use CSE 142's 'DrawingPanel' object, but we
     * *do* use the same 'Graphics' object.
     */
    public Graphics getGraphics() {
        return this.graphics;
    }

    /**
     * Returns the width of the window, in pixels.
     *
     * Note: the width may change if the user resizes the window, so be sure to
     * recheck what this value is every time you need to use it again.
     *
     * @throws IllegalStateException   if the image width is somehow unknown
     */
    public int getWidth() {
        if (this.width == UNKNOWN_DIMENSION) {
            throw new IllegalStateException("Unexpected fatal error: Image width unknown");
        }
        return this.width;
    }

    /**
     * Returns the height of the window, in pixels.
     *
     * Note: the height may change if the user resizes the window, so be sure to
     * recheck what this value is every time you need to use it again.
     *
     * @throws IllegalStateException   if the image width is somehow unknown
     */
    public int getHeight() {
        if (this.height == UNKNOWN_DIMENSION) {
            throw new IllegalStateException("Unexpected fatal error: Image height unknown");
        }
        return this.height;
    }

    /**
     * Draws a scatter plot that occupies the entire window.
     *
     * Note: the xValues and the yValues represent the x coordinates and the y coordinates
     * you want to plot. The two lists will be "paired up" element-by-element.
     *
     * For example, if xValues contains [0, 1, 2, 3, 4] and yValues contains [10, 20, 30, 40, 50],
     * we will end up plotting the points (0, 10), (1, 20), (2, 30), (3, 40), and (4, 50).
     *
     * @param title       The title of the scatter plot. To hide the title, pass in the empty string.
     * @param xAxisLabel  The label for the x axis
     * @param yAxisLabel  The label for the y axis
     * @param xValues     The x coordinate values to plot
     * @param yValues     The y coordinate values to plot
     */
    public void drawScatterPlot(String title, String xAxisLabel, String yAxisLabel,
                                IList<Double> xValues, IList<Double> yValues) {
        if (this.width == UNKNOWN_DIMENSION || this.height == UNKNOWN_DIMENSION) {
            throw new IllegalStateException("Unexpected fatal error: Image width or height unknown");
        }
        this.drawScatterPlot(
                title, xAxisLabel, yAxisLabel, xValues, yValues,
                new Rectangle2D.Double(0, 0, this.width, this.height));
    }

    /**
     * Draws a scatter plot that occupies the given portion of the window.
     *
     * Note: the xValues and the yValues represent the x coordinates and the y coordinates
     * you want to plot. The two lists will be "paired up" element-by-element.
     *
     * For example, if xValues contains [0, 1, 2, 3, 4] and yValues contains [10, 20, 30, 40, 50],
     * we will end up plotting the points (0, 10), (1, 20), (2, 30), (3, 40), and (4, 50).
     *
     * @param title       The title of the scatter plot. To hide the title, pass in the empty string.
     * @param xAxisLabel  The label for the x axis
     * @param yAxisLabel  The label for the y axis
     * @param xValues     The x coordinate values to plot
     * @param yValues     The y coordinate values to plot
     * @param drawRegion  The region on the image to draw the chart on
     */
    public void drawScatterPlot(String title, String xAxisLabel, String yAxisLabel,
                                IList<Double> xValues, IList<Double> yValues,
                                Rectangle2D drawRegion) {
        if (xValues.size() != yValues.size()) {
            throw new IllegalArgumentException("Number of 'x' values and 'y' values are not the same.");
        }

        Iterator<Double> xIter = xValues.iterator();
        Iterator<Double> yIter = yValues.iterator();

        // We pair the elements together and add them to a series.
        // We pick an arbitrary name for this series -- the user will never
        // see it because we hide the legend anyways.
        XYSeries series = new XYSeries("Series 1");
        while (xIter.hasNext()) {
            series.add(xIter.next(), yIter.next());
        }

        // We add our series to the series collection. A SeriesCollection
        // may contain multiple series in case we want to plot multiple
        // datasets on the same chart. We don't do this, however.
        XYSeriesCollection seriesCollection = new XYSeriesCollection();
        seriesCollection.addSeries(series);

        // Finally, we create our chart. We hide the legend mostly because
        // it's sort of pointless if we only ever plot one series at a time.
        JFreeChart chart = ChartFactory.createScatterPlot(
                title,
                xAxisLabel,
                yAxisLabel,
                seriesCollection);
        chart.removeLegend();

        // We then draw this chart using the underlying Graphics object.
        Graphics2D g2 = (Graphics2D) this.getGraphics();
        chart.draw(g2, drawRegion);
    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int newWidth, int newHeight) {
        boolean widthReady = (infoflags & ImageObserver.WIDTH) != 0;
        boolean heightReady = (infoflags & ImageObserver.HEIGHT) != 0;

        if (widthReady && heightReady) {
            this.width = newWidth;
            this.height = newHeight;
            return false;
        } else {
            return true;
        }
    }
}
