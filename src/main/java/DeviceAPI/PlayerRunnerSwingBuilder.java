package DeviceAPI;

import FileAccess.*;
import Maths.Processing;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.StrictMath.abs;

public class PlayerRunnerSwingBuilder {
    private int dataTypeIndex = 0;
    final double granularity = 0.250;
    private final ArrayList<String> dataTypes;
    private final int variance;
    private String dataTypeCurrent;
    private boolean captureData = false;
    private final List<Double> eogReadingsList = new ArrayList<>();
    private final List<Double> eog2ReadingsList = new ArrayList<>();
    private final List<Double> accReadingsList = new ArrayList<>();
    private final GUI2.Graph graph;

    public PlayerRunnerSwingBuilder(ArrayList<String> dataTypes, int variance, GUI2.Graph graph){
        this.dataTypes = dataTypes;
        this.variance = variance;
        this.graph = graph;
    }

    public SwingWorker SwingWorkerBuilder(String command){
        //Builds SwingWorker for receiving and capturing data from device
        SwingWorker Swing = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                final Process process = Runtime.getRuntime().exec(command);
                BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                long start = System.nanoTime();
                long start2 = System.nanoTime();
                //data types are stored one at a time, granularity is divided by the number of data types to determine number of times data should be captured per second
                double relativeGranularity = granularity / dataTypes.size();
                List<Double> previousEOG = new ArrayList<Double>();
                List<Double> previousEOG2 = new ArrayList<Double>();
                //Add all possible data types to hashmap to translate from type name to ID used by device API
                HashMap<String, String> dataTypesMap = new HashMap<String, String>();
                dataTypesMap.put("Rear EOG","eeg");
                dataTypesMap.put("Frontal EOG","eeg");
                dataTypesMap.put("Accelerometer","acc");
                //To calculate variance correctly, list is filled with 0s equal to size of selected variance window during initialisation
                for (int i = 0; i < variance; i++) {
                    previousEOG.add(0.0);
                    previousEOG2.add(0.0);
                }
                try {
                    while ((line = input.readLine()) != null) {
                        dataTypeCurrent = dataTypesMap.get(dataTypes.get(dataTypeIndex));
                        //If line that was read in contains the ID of the data type currently being captured, do processing
                        if (line.contains("/" + dataTypeCurrent) && captureData) {
                            //get time between now and last time data was taken in
                            long finish = System.nanoTime();
                            long elapsedTime = finish - start;
                            double elapsedTimeSeconds = (double) elapsedTime / 1000000000;
                            //Check if elapsed time is greater than relative granularity, if so, continue processing captured data
                            if (elapsedTimeSeconds > relativeGranularity) {
                                String[] lineSplit = line.split(" ");
                                double capturedData = Processing.DataProcess(lineSplit, dataTypes.get(dataTypeIndex));
                                long finish2 = System.nanoTime();
                                long elapsedTime2 = finish2 - start2;
                                double elapsedTimeSeconds2 = (double) elapsedTime2 / 1000000000;
                                //if elapsed time is greater than full granularity,
                                // all data types have been checked and processed once and so the graph can be incremented
                                if (elapsedTimeSeconds2 > granularity){
                                    graph.incrementTimeOne();
                                    graph.cp.repaint();
                                    graph.cp.revalidate();
                                    start2 = System.nanoTime();
                                }
                                //Check if current data type is "Rear EOG", process accordingly if so
                                if (dataTypes.get(dataTypeIndex).equals("Rear EOG")) {
                                    //Pop off the first item in the list and append most recent to calculate variance
                                    previousEOG.remove(0);
                                    previousEOG.add(capturedData);
                                    //calculate variance
                                    double currentVariance = Processing.CalcVariance(previousEOG) / 10000;
                                    if (currentVariance >= 120)
                                        currentVariance = 120;
                                    else if (currentVariance > 100)
                                        currentVariance = 100;
                                    //add calculated variance to graph and list of readings to be written to file
                                    eogReadingsList.add(currentVariance);
                                    graph.eogMoveOne(currentVariance);
                                }
                                //Check if current data type is "Frontal EOG", process accordingly if so
                                else if (dataTypes.get(dataTypeIndex).equals("Frontal EOG")) {
                                    //Pop off the first item in the list and append most recent to calculate variance
                                    previousEOG2.remove(0);
                                    previousEOG2.add(capturedData);
                                    //calculate variance
                                    double currentVariance = Processing.CalcVariance(previousEOG2) / 10000;
                                    if (currentVariance >= 120)
                                        currentVariance = 120;
                                    else if (currentVariance > 100)
                                        currentVariance = 100;
                                    //add calculated variance to graph and list of readings to be written to file
                                    eog2ReadingsList.add(currentVariance);
                                    graph.eog2MoveOne(currentVariance);
                                //Check if current data type is "Frontal EOG", process accordingly if so
                                } else if (dataTypeCurrent.equals("acc")) {
                                    //Additional processing on accelerometer data to fit with graph appropriately
                                    double currentAcc = abs(capturedData) / 5;
                                    if (currentAcc >= 120)
                                        currentAcc = 120;
                                    else if (currentAcc > 100)
                                        currentAcc = 100;
                                    //add accelerometer data to readings list and graph
                                    accReadingsList.add(currentAcc);
                                    graph.accMoveOne(currentAcc);
                                }
                                DataTypeAlternate(dataTypes);
                                start = System.nanoTime();
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //kill processes completely once stopped
                process.waitFor();
                process.destroyForcibly();
                return null;
            }
        };
        return Swing;
    }

    public void setCaptureData(boolean captureData) {
        //Setter for captureData
        this.captureData = captureData;
    }

    public boolean getCaptureData(){
        //Getter for captureData
        return captureData;
    }

    private void DataTypeAlternate(ArrayList<String> dataTypes){
        //Alternates between which data type is currently being viewed, if end of the list is reached, the pointer is put back to the start
        if (dataTypeIndex < dataTypes.size() - 1){
            dataTypeIndex += 1;
        }
        else{
            dataTypeIndex = 0;
        }
    }

    public void EndRecording(String fileName, String group, String videoPath) throws IOException {
        //Takes all captured data and file information and passes it to method to write file
        SessionPath sessionPath = new SessionPath();
        String path = sessionPath.getSessionPath();
        String fullFileName = path + "/" + fileName + ".fd";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fullFileName, true));
        FileAccess fileAccess = new FileAccess();
        FilesBuilder filesBuilder = new FilesBuilder();
        FileBuilder fileBuilder = new FileBuilder();
        fileAccess.generateFile(writer, filesBuilder, fileBuilder, fullFileName, path, group, videoPath, eogReadingsList, accReadingsList, eog2ReadingsList, dataTypes);
    }
}
