package FileAccess;

import Maths.Processing;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileAccess {

    public void generateFile(BufferedWriter writer, FilesBuilder filesBuilder, FileBuilder fileBuilder, String fullFileName, String path, String group, String videoPath, List<Double> eogReadingsList, List<Double> accReadingsList, List<Double> eog2ReadingsList, ArrayList<String> dataTypes) throws IOException {

        Path newPath = Paths.get(path);
        //Checks if given directory exists, if not, creates it
        if (!filesBuilder.exists(newPath)) {
            filesBuilder.createDirectory(newPath);
        }
        //Checks if given file already exists, if not, creates it
        if (!fileBuilder.exists(fullFileName)) {
            if (fileBuilder.createNewFile(fullFileName)) {
                System.out.println("Created new file");
            }
        }

        //formatting for current time and date to be written to file
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy/HH:mm:ss");
        LocalDateTime timeAndDate = LocalDateTime.now();

        //string of data types captured, separated by "/" and deleting the trailing "/"
        StringBuilder stringBuilder = new StringBuilder();
        for (String data : dataTypes) {
            stringBuilder.append(data);
            stringBuilder.append("/");
        }
        if(stringBuilder.length() < 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        //Appends all info on data to the file
        writer.append("Created: ").append(dtf.format(timeAndDate)).append(", ");
        writer.append("Group: ").append(group).append(", ");
        writer.append(videoPath).append(", ");
        writer.append(String.valueOf(Processing.CalcVariance(eogReadingsList))).append("/");
        writer.append(String.valueOf(Processing.CalcVariance(eog2ReadingsList))).append("/");
        writer.append(String.valueOf(Processing.CalcVariance(accReadingsList))).append(", ");
        writer.append(stringBuilder.toString()).append("\n");

        //appends all captured data to file, data types separated by ","
        for (int i = 0; i < Math.max(Math.max(eogReadingsList.size() - 1, accReadingsList.size() - 1), eog2ReadingsList.size() - 1); i++) {
            if (i + 1 < eogReadingsList.size()) {
                writer.append(eogReadingsList.get(i).toString()).append(", ");
            } else {
                writer.append("0.0, ");
            }
            if (i + 1 < eog2ReadingsList.size()) {
                writer.append(eog2ReadingsList.get(i).toString()).append(", ");
            }
            else {
                writer.append("0.0, ");
            }
            if (i + 1 < accReadingsList.size()) {
                writer.append(accReadingsList.get(i).toString()).append(", ");
            }
            else {
                writer.append("0.0, ");
            }
            writer.append("\n");
        }
        writer.close();
    }

    public String GetFullPath(String filename, SessionPath sessionPath){
        //Calls method to read in desired file location
        String path = sessionPath.getSessionPath();
        return path + "/" + filename;
    }

    public void WriteFile(String filename, String contents, FileBuilder fileBuilder, FilesBuilder filesBuilder, BufferedWriter writer, SessionPath sessionPath) throws IOException {
        //Given a String contents, creates a new file
        contents = contents.replaceAll("\\\\n", "\n");
        String path = sessionPath.getSessionPath();
        //Checks if given directory exists, if not, creates it
        Path newPath = Paths.get(path);
        if (!filesBuilder.exists(newPath)) {
            filesBuilder.createDirectory(newPath);
        }
        String fileFullPath = GetFullPath(filename, sessionPath);
        //Checks if given file exists, if not, creates it
        if (!fileBuilder.exists(fileFullPath)) {
            if (fileBuilder.createNewFile(fileFullPath)) {
                System.out.println("Created new file");
            }
        }
        //appends String contents to file
        writer.append(contents);
        writer.close();
    }


    public String readFile(String filename, ScannerBuilder scannerBuilder) {
        //Reads in text from a given file and returns it as a String
        StringBuilder contents = new StringBuilder();
        try {
            File file = new File(filename);

            Scanner reader = scannerBuilder.BuildScanner(file);
            while(reader.hasNextLine()){
                contents.append(reader.nextLine()).append("\n");
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return contents.toString();
    }


    public Process cutVideo(String startTime, String endTime, String videoPath, String newPath, Runtime runtime) throws IOException {
        //Runs FFMPEG command to cut and split given video for viewing of clip
        return runtime.exec("ffmpeg -ss " + startTime + " -i " + videoPath + " -c copy -t " + endTime + " " + newPath + "\\clippedvideo.mp4 -y");
    }
}
