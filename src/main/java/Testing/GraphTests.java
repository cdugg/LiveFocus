package Testing;

import GUI2.Graph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.XYStyler;

import static org.mockito.Mockito.*;

public class GraphTests {
    Graph test;
    XChartPanel mockPanel;
    XYChart mockChart;
    XYStyler mockStyler;

    @Before
    public void init() {
        mockPanel = mock(XChartPanel.class);
        mockChart = new XYChartBuilder().width(0).height(0).title("EOG Front Readings").xAxisTitle("Time (seconds)").yAxisTitle("").build();
        mockStyler = mock(XYStyler.class);
        test = new Graph("Test", mockPanel, mockChart);
        Assert.assertEquals(test.times[99], 0.0, 0.0);
    }

    @Test
    public void AddLineTest() {
        test.addLine("Accelerometer");
        test.addLine("Rear EOG");
        test.addLine("Frontal EOG");
        Assert.assertEquals(mockChart.getSeriesMap().size(), 3);
    }

    @Test
    public void AccMoveOneTest() {
        test.addLine("Accelerometer");
        test.accMoveOne(2.0);
        Assert.assertEquals(test.accData[99], 2.0, 0.0);
    }

    @Test
    public void EOGMoveOneTest() {
        test.addLine("Rear EOG");
        test.eogMoveOne(2.0);
        Assert.assertEquals(test.eogData[99], 2.0, 0.0);
    }

    @Test
    public void EOG2MoveOneTest() {
        test.addLine("Frontal EOG");
        test.eog2MoveOne(2.0);
        Assert.assertEquals(test.eog2Data[99], 2.0, 0.0);
    }

    @Test
    public void IncrementTimeOneTest() {
        test.incrementTimeOne();
        Assert.assertEquals(test.time, 0.25, 0.0);
    }

    @Test
    public void MoveCountRightTest() {
        double[] a = new double[]{1.0, 2.0, 3.0};
        test.addLine("A");
        test.addLine("Average");
        test.moveCountRight(new double[][]{a, a}, new String[]{"A", "Average"}, 3);
        verify(mockPanel, times(1)).revalidate();
        Assert.assertEquals(test.times[99], 0.75, 0.0);
        Assert.assertEquals(test.getSeries("A")[99], 3.0, 0.0);
        Assert.assertEquals(test.getSeries("Average")[99], 0.0, 0.0);
    }

    @Test
    public void MoveCountLeftTest() {
        double[] a = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.0, 2.0, 3.0};
        double[] b = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0};
        test.addLine("A");
        test.addLine("Average");
        test.moveCountRight(new double[][]{b, b}, new String[]{"A", "Average"}, 6);
        test.moveCountLeft(new double[][]{a, a}, new String[]{"A", "Average"}, 3);
        verify(mockPanel, times(2)).revalidate();
        Assert.assertEquals(test.times[99], 0.75, 0.0);
        Assert.assertEquals(test.getSeries("A")[99], 3.0, 0.0);
        Assert.assertEquals(test.getSeries("Average")[99], 0.0, 0.0);
    }

    @Test
    public void ToggleSeriesOffTest() {
        test.addLine("A");
        test.toggleSeriesOff("A");
        verify(mockPanel, times(1)).revalidate();
    }

    @Test
    public void ToggleSeriesOnTest() {
        test.addLine("A");
        test.toggleSeriesOn("A");
        verify(mockPanel, times(1)).revalidate();
    }

    @Test
    public void SwitchReadingTypeTest() {
        test.addLine("A");
        String[] a = {"A"};
        double[][] newItems = new double[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.0, 2.0, 3.0}};
        test.switchReadingType("ACC", newItems, a);
        verify(mockPanel, times(1)).revalidate();
    }

    @Test
    public void AddAverageTest() {
        test.addAverage();
        verify(mockPanel, times(1)).revalidate();
    }

    @Test
    public void HideAverageTest() {
        test.addAverage();
        verify(mockPanel, times(1)).revalidate();
    }

    @Test
    public void ShowAverageTest() {
        test.addAverage();
        verify(mockPanel, times(1)).revalidate();
    }

    @Test
    public void UpdateAverageTest() {
        test.addAverage();
        double[] a = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.0, 2.0, 3.0};
        test.updateAverage(a);
        Assert.assertEquals(test.getSeries("Average")[99], 3.0, 0.0);
        double[] b = new double[]{4.0, 5.0};
        test.updateAverage(b);
        Assert.assertEquals(test.getSeries("Average")[99], 5.0, 0);
    }

    @Test
    public void GetSeriesTest() {
        test.addLine("A");
        double[] x = test.getSeries("A");
        Assert.assertArrayEquals(x, new double[100], 0);
    }
}
