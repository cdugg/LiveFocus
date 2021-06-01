package DeviceAPI;

import FileAccess.FileAccess;
import Maths.Processing;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.StrictMath.abs;

public class MusePlayerRunner {
    private final SwingWorker Swing;
    private final String fileName;
    private final String group;
    private final String videoPath;
    private final PlayerRunnerSwingBuilder playerRunnerSwingBuilder;
    private final String command = "C:\\Program Files (x86)\\Muse\\muse-player -l 5000";

    public MusePlayerRunner(String fileName, String group, String videoPath, PlayerRunnerSwingBuilder playerRunnerSwingBuilder) {
        this.fileName = fileName;
        this.group = group;
        this.videoPath = videoPath;
        this.playerRunnerSwingBuilder = playerRunnerSwingBuilder;

        //runs the builder of the Swing Worker that runs the API that captures data from the device, and then processes and stores that data
        Swing = playerRunnerSwingBuilder.SwingWorkerBuilder(command);
    }

    public void StartRecording(){
        //Set SwingBuilder boolean flag to start data capture
        playerRunnerSwingBuilder.setCaptureData(true);
    }

    public void PauseRecording(){
        //Set SwingBuilder boolean flag to stop data capture
        playerRunnerSwingBuilder.setCaptureData(false);
    }


    public void MusePlayerRun() {
        //Runs the Swing Worker
        Swing.execute();
    }

    public void MusePlayerCancel() throws IOException {
        //Kills Swing Worker and process
        Swing.cancel(true);
        Runtime.getRuntime().exec("taskkill /F /IM muse-player.exe");
        System.out.println("Player Task Killed.");
    }

    public void EndRecording() throws IOException {
        //End data capture and calls method for data to be written to file
        playerRunnerSwingBuilder.setCaptureData(false);
        playerRunnerSwingBuilder.EndRecording(fileName, group, videoPath);
    }

}

