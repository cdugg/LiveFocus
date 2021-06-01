package GUI2;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;
import org.knowm.xchart.style.theme.GGPlot2Theme;

import javax.swing.*;

public class Graph {
    public String seriesName;
    public JPanel cp;
    public double[] times = new double[100];
    public double[] eogData = new double[100];
    public double[] eog2Data = new double[100];
    public double[] accData = new double[100];
    public double time = 0.0;
    XYChart chart;

    public Graph(String y, JPanel chartPanel, XYChart mainChart) {
        //initialize chart and chart panel
        cp = chartPanel;
        chart = mainChart;
        chart.setYAxisTitle(y);
        //cp = new XChartPanel<XYChart>(chart);

        //style graph
        //There is no support for adding specific number of tick marks on graph or size of each tick.
        chart.getStyler().setTheme(new GGPlot2Theme());
        chart.getStyler().setXAxisDecimalPattern("0");
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        //initialize times array (-100 data points to 0)
        int j = 0;
        for (int i = 99; i >= 1; i--) {
            times[j] = i * -0.25;
            j++;
        }
        times[99] = 0;
    }

    //add any line to the graph. initialize with current time values and 0s for y
    public void addLine(String series) {
        seriesName = series;
        chart.addSeries(series, times, new double[100]).setMarker(SeriesMarkers.NONE);
    }

    //move one accelerometer datapoint to the right. used for realtime updating
    public void accMoveOne(double newData) {
        for (int i = 1; i < times.length; i++) {
            accData[i - 1] = accData[i];
        }
        accData[99] = newData;
        chart.updateXYSeries("Accelerometer", times, accData, null);
    }

    //move one eog datapoint to the right. used for realtime updating
    public void eogMoveOne(double newData) {
        for (int i = 1; i < times.length; i++) {
            eogData[i - 1] = eogData[i];
        }
        eogData[99] = newData;
        chart.updateXYSeries("Rear EOG", times, eogData, null);
    }

    //move one eog datapoint to the right. used for realtime updating
    public void eog2MoveOne(double newData) {
        for (int i = 1; i < times.length; i++) {
            eog2Data[i - 1] = eog2Data[i];
        }
        eog2Data[99] = newData;
        chart.updateXYSeries("Frontal EOG", times, eog2Data, null);
    }

    //increments time array one point forward for realtime data
    //separate from MoveOnes because time will double or triple increment if it was included
    public void incrementTimeOne() {
        time += .25;
        for (int i = 1; i < times.length; i++) {
            times[i - 1] = times[i];
        }
        times[99] = time;
    }

    //move an existing graph multiple data points to the right, maximum 100 per movement. used for review sessions
    public void moveCountRight(double[][] items, String[] names, int displacement) {
        int j = 0;
        //adjust the times array based on how far it needs to move
        for (int i = displacement; i < 100; i++) {
            times[j] = times[i];
            j++;
        }
        for (int i = 0; i < displacement; i++) {
            time += 0.25;
            times[j] = time;
            j++;
        }
        //adjust the series data to match the new times
        for (int q = 0; q < items.length - 1; q++) {
            double[] tmp = new double[100];
            int k = 0;
            for (int i = 100 - items[q].length; i < 100; i++) {
                tmp[i] = items[q][k];
                k++;
            }
            chart.updateXYSeries(names[q], times, tmp, null);
        }
        //reset average to all zeroes. will be updated after all series have correct new values
        chart.updateXYSeries("Average", times, new double[100], null);
        //redisplay new chart
        cp.repaint();
        cp.revalidate();
    }

    //move existing graph multiple data points to the left, maximum 100 per movement. used for review sessions
    public void moveCountLeft(double[][] items, String[] names, int displacement) {
        //adjust the times array based on how far it needs to move
        for (int i = 0; i < times.length; i++) {
            times[i] = times[i] - (displacement * .25);
        }
        time -= displacement * 0.25;
        //adjust the series data to match the new times
        for (int q = 0; q < items.length - 1; q++) {
            double[] tmp = new double[100];
            for (int i = 0; i < items[q].length; i++) {
                tmp[i] = items[q][i];
            }
            chart.updateXYSeries(names[q], times, tmp, null);
        }
        //reset average to all zeroes. will be updated after all series have correct new values
        chart.updateXYSeries("Average", times, new double[100], null);
        //redisplay new chart
        cp.repaint();
        cp.revalidate();
    }

    //hide a line from view on the chart
    public void toggleSeriesOff(String name) {
        XYSeries xySeries = chart.getSeriesMap().get(name);
        xySeries.setLineStyle(SeriesLines.NONE);
        //redisplay new chart
        cp.repaint();
        cp.revalidate();
    }

    //show a line on the chart that was previously hidden
    public void toggleSeriesOn(String name) {
        XYSeries xySeries = chart.getSeriesMap().get(name);
        xySeries.setLineStyle(SeriesLines.SOLID);
        //redisplay new chart
        cp.repaint();
        cp.revalidate();
    }

    //switch the signal type that is being displayed on the chart
    public void switchReadingType(String readingType, double[][] values, String[] names) {
        //change the charts title
        String title = readingType + " Readings";
        chart.setTitle(title);

        //remove every series on the chart and replace them with the new signal types data
        //after all are added those that were previously toggled will be re-toggled on otherwise left off
        int i = 0;
        for (String name : names) {
            chart.removeSeries(name);
            double[] tempReplacement = new double[100];
            if (values[i].length < 100) {
                int o = values[i].length - 1;
                for (int w = 99; w > 0; w--) {
                    if (o >= 0)
                        tempReplacement[w] = values[i][o];
                    else
                        tempReplacement[w] = 0;
                    o--;
                }
            } else {
                tempReplacement = values[i];
            }
            chart.addSeries(name, times, tempReplacement, null).setMarker(SeriesMarkers.NONE);
            i++;
        }
        //redisplay new chart
        cp.repaint();
        cp.revalidate();
    }

    //initialize the average line to be hidden from view
    public void addAverage() {
        chart.addSeries("Average", times, new double[100]).setMarker(SeriesMarkers.NONE);
        XYSeries xySeries = chart.getSeriesMap().get("Average");
        xySeries.setLineStyle(SeriesLines.NONE);
        //redisplay new chart
        cp.repaint();
        cp.revalidate();
    }

    //hide average line from view
    public void hideAverage() {
        XYSeries xySeries = chart.getSeriesMap().get("Average");
        xySeries.setLineStyle(SeriesLines.NONE);
        //redisplay new chart
        cp.repaint();
        cp.revalidate();
    }

    //show previously hidden average line
    public void showAverage() {
        XYSeries xySeries = chart.getSeriesMap().get("Average");
        xySeries.setLineStyle(SeriesLines.SOLID);
        //redisplay new chart
        cp.repaint();
        cp.revalidate();
    }

    //takes list and updates the average line to match what the values should be
    public void updateAverage(double[] values) {
        if (values.length < 100) {
            double[] temp = new double[100];
            int k = 0;
            for (int i = 100 - values.length; i < 100; i++) {
                temp[i] = values[k];
                k++;
            }
            values = temp;
        }
        chart.updateXYSeries("Average", times, values, null);
        //redisplay new chart
        cp.repaint();
        cp.revalidate();
    }

    //from a given name return the list of y values that matches that series
    public double[] getSeries(String name) {
        XYSeries request = chart.getSeriesMap().get(name);
        return request.getYData();
    }
}
