package GUI2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RecordingFile {
    public String filePath;
    public String name = "";
    public String date;
    public String group;
    public String videoPath;
    public double eogFrontVariance;
    public double eogRearVariance;
    public double accVariance;
    public ArrayList<Double> eogFrontData = new ArrayList<Double>();
    public ArrayList<Double> eogRearData = new ArrayList<Double>();
    public ArrayList<Double> accData = new ArrayList<Double>();

    public RecordingFile(File f) {
        //get file name from the path
        String path = f.toString();
        String[] pathInfo = path.split("[\"'\\\\/]+", -1);
        String tmpName = pathInfo[pathInfo.length - 1];
        String extension = tmpName.substring(tmpName.length() - 3);

        //if file has correct extension type parse the meta data of the file from first line into variables
        if (extension.equals(".fd")) {
            name = "Name: " + tmpName;
            try {
                Scanner sc = new Scanner(f);
                String info = sc.nextLine();
                String[] info2 = info.split("[,][ ]*", -1);
                filePath = f.toString();
                date = info2[0];
                group = info2[1];
                videoPath = info2[2];
                String[] varianceItems = info2[3].split("[/]", -1);
                eogFrontVariance = Double.parseDouble(varianceItems[0]);
                eogRearVariance = Double.parseDouble(varianceItems[1]);
                accVariance = Double.parseDouble(varianceItems[2]);
                readData();
                sc.close();
            } catch (Exception e) { //in case a .fd file that is incompatible shows up
                //System.out.println(".fd File has been edited, excluding file");
                name = "";
            }
        }
    }

    //read all the lines of data in the file and write them into correct ArrayLists
    public void readData() {
        ArrayList<String> readingsStringArray = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(filePath));
            sc.nextLine(); //skip first line (meta data)
            while (sc.hasNextLine()) {
                readingsStringArray.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (String s : readingsStringArray) {
            String[] readings = s.split("[,][ ]*", -1);
            eogFrontData.add(Double.parseDouble(readings[0]));
            eogRearData.add(Double.parseDouble(readings[1]));
            accData.add(Double.parseDouble(readings[2]));
        }
    }

    //input start position and end position (exclusive) and the signal type.
    //returns double array of data for correct reading type from [start:finish]
    public double[] getSubList(int start, int end, String signal) {
        Object[] sublist;
        ArrayList<Double> tmpData;
        if (signal.equals("EOG Front")) {
            tmpData = eogFrontData;
        } else if (signal.equals("EOG Rear")) {
            tmpData = eogRearData;
        } else {
            tmpData = accData;
        }
        sublist = new ArrayList<Double>(tmpData.subList(start, end)).toArray();
        double[] out = new double[end - start];
        int i = 0;
        for (Object item : sublist) {
            out[i] = Double.parseDouble(item.toString());
            i++;
        }
        return out;
    }
}
