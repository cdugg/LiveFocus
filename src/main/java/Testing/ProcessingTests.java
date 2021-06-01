package Testing;

import org.junit.Assert;
import org.junit.Test;
import Maths.Processing;

import java.util.Arrays;
import java.util.List;

public class ProcessingTests {

    @Test
    public void ProcessingTestsAccProcessing(){
        Assert.assertEquals(Processing.AccProcessing(1.0,2.0,3.0), 1016.25834, 5);
        Assert.assertEquals(Processing.AccProcessing(0.0,0.0,0.0), 1020.0, 10);
        Assert.assertEquals(Processing.AccProcessing(-4.0,087.0,34.0), 926.50668, 5);
        Assert.assertEquals(Processing.AccProcessing(3445543.0,40577.0,45624.0), -3445063.95242, 5);
    }
    @Test
    public void ProcessingTestsEogProcessing() {
        Assert.assertEquals(Processing.EOGProcessing(2.0, 2.0), 4.0, 10);
        Assert.assertEquals(Processing.EOGProcessing(-59.0, -91.0), 108.45275, 5);
        Assert.assertEquals(Processing.EOGProcessing(840.58, 1263.34), 1517.43292, 5);
    }

    @Test
    public void ProcessingTestsCalcVariance() {
        List<Double> testReadings = Arrays.asList(0.0, 0.0, 0.0, 0.0);
        Assert.assertEquals(Processing.CalcVariance(testReadings), 0.0, 5);

        testReadings = Arrays.asList(1020.0, 546.87, 12.0, 453.1435);
        Assert.assertEquals(Processing.CalcVariance(testReadings), 170893.37081, 5);

        testReadings = Arrays.asList(-1020.0, -546.87, -12.0, -453.1435);
        Assert.assertEquals(Processing.CalcVariance(testReadings), 170893.37081, 5);
    }

    @Test
    public void ProcessingTestsDataProcessAcc() {
        String accInput = "1620073517.2 /muse/acc fff  0 0 0";
        Assert.assertEquals(Processing.DataProcess(accInput.split(" "), "Accelerometer"), 1020.0, 5);

        accInput = "1620073517.2 /muse/acc fff  1002.4 45744.124 0.456";
        Assert.assertEquals(Processing.DataProcess(accInput.split(" "), "Accelerometer"), -44735.10557, 5);

    }

    @Test
    public void ProcessingTestsDataProcessEog() {
        String eogInput = "1620073517.2 /muse/eeg ffff  842.23 840.58 1263.34 842.23";
        Assert.assertEquals(Processing.DataProcess(eogInput.split(" "), "Frontal EOG"), 1517.43292, 5);

        eogInput = "1620073517.2 /muse/eeg ffff  -842.23 -840.58 -1263.34 -842.23";
        Assert.assertEquals(Processing.DataProcess(eogInput.split(" "), "Frontal EOG"), 1517.43292, 5);

    }

    @Test
    public void ProcessingTestsDataProcessEog2() {
        String eogInput = "1620073517.2 /muse/eeg ffff  842.23 840.58 1263.34 842.23";
        Assert.assertEquals(Processing.DataProcess(eogInput.split(" "), "Rear EOG"), 1191.09308, 5);

        eogInput = "1620073517.2 /muse/eeg ffff  -842.23 -840.58 -1263.34 -842.23";
        Assert.assertEquals(Processing.DataProcess(eogInput.split(" "), "Rear EOG"), 1191.09308, 5);

    }

    @Test
    public void ProcessingTestsDataProcessNull() {
        String input = "";
        Assert.assertEquals(Processing.DataProcess(input.split(" "), "invalid"), 0.0, 5);
    }

    @Test
    public void ProcessingTestsAverage1() {
        double[] array1 = {1.0, 2.0, 3.0};
        double[] array2 = {3.0, 4.0, 5.0};
        double[] resultArray = {2.0, 3.0, 4.0};
        double[][] doubleArrayArray = {array1, array2};
        Assert.assertArrayEquals(Processing.average(doubleArrayArray), resultArray, 5);
    }

    @Test
    public void ProcessingTestsAverage2() {
        double[] array1 = {0.0, 0.0, 0.0};
        double[] array2 = {0.0, 0.0, 0.0};
        double[] resultArray = {0.0, 0.0, 0.0};
        double[][] doubleArrayArray = {array1, array2};
        Assert.assertArrayEquals(Processing.average(doubleArrayArray), resultArray, 5);
    }
    @Test
    public void ProcessingTestsAverage3() {
        double[] array1 = {235235.456, 346342.3436, 976574.234551};
        double[] array2 = {0.0, 0.0, 0.0};
        double[] resultArray = {117617.728, 173171.1718, 488287.117275};
        double[][] doubleArrayArray = {array1, array2};
        Assert.assertArrayEquals(Processing.average(doubleArrayArray), resultArray, 5);
    }



}
