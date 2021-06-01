package Maths;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import java.util.List;

public class Processing{
    //process the returned values of the muse api for acc signals
    public static double AccProcessing(Double x, Double y, Double z){
        return 1020 - Math.sqrt(
                Math.pow(x, 2)+
                Math.pow(y, 2)+
                Math.pow(z, 2));
    }


    //process the returned values of the muse api for eog signals
    public static double EOGProcessing(Double x, Double y){
        return Math.sqrt(
                Math.pow(x, 2)+ Math.pow(y, 2));
    }

    //calculate the variance of a list
    public static double CalcVariance(List<Double> readings){
        double total = 0;
        for (Double reading : readings) {
            total = total + reading;
        }
        double mean = total / readings.size();
        total = 0;
        for (Double reading : readings) {
            total = total + Math.pow(reading - mean, 2);
        }
        return total/(readings.size()-1);
    }

    //parse returned data from muse api
    public static double DataProcess(String[] lineSplit, String dataTypeCurrent) {
        switch (dataTypeCurrent) {
            case "Rear EOG":
                return Processing.EOGProcessing(Double.parseDouble(lineSplit[4]),
                        Double.parseDouble(lineSplit[7]));
            case "Frontal EOG":
                return Processing.EOGProcessing(Double.parseDouble(lineSplit[5]),
                        Double.parseDouble(lineSplit[6]));
            case "Accelerometer":
                return Processing.AccProcessing(Double.parseDouble(lineSplit[4]),
                        Double.parseDouble(lineSplit[5]),
                        Double.parseDouble(lineSplit[6]));
        }
        System.out.println("Invalid data type");
        return 0;
    }

    //from a set of arrays get the average value at each point and return the array of averages
    //ie [[1,2,3],[4,2,0],[1,1,1]] will give [2,1.66,1.33]
    public static double[] average(double[][] items) {
        double[] averages = new double[items[0].length];

        for (int i = 0; i < items[0].length; i++) {
            double sum = 0;
            for (double[] item : items) {
                sum += item[i];
            }
            averages[i] = sum / items.length;
        }

        return averages;
    }

    //calculate the correlation coefficient of any 2 arrays
    public static double correlation(double[] x, double[] y){
        return new PearsonsCorrelation().correlation(x, y);
    }
}