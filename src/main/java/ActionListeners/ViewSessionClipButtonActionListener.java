package ActionListeners;

import FileAccess.FileAccess;
import FileAccess.SessionPath;
import GUI2.ViewClip;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ViewSessionClipButtonActionListener implements ActionListener {
    JTextField startTimeField;
    JTextField endTimeField;
    String videoPath;

    public ViewSessionClipButtonActionListener(JTextField start, JTextField end, String path) {
        startTimeField = start;
        endTimeField = end;
        videoPath = path;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //get and parse the times for the clipping
        String startTime = startTimeField.getText();
        String endTime = endTimeField.getText();
        int startTimeInt = Integer.parseInt(startTime);
        int endTimeInt = Integer.parseInt(endTime);
        String timestamp2 = Integer.toString(endTimeInt - startTimeInt);

        //get the file location of the video
        SessionPath sessionPath = new SessionPath();
        String path = sessionPath.getSessionPath();
        Path newPath = Paths.get(path + "\\Videos");
        if (!Files.exists(newPath)) {
            try {
                Files.createDirectory(newPath);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        //create clip of the timeframe
        try {
            Runtime runtime = Runtime.getRuntime();
            FileAccess fileAccess = new FileAccess();
            Process process = fileAccess.cutVideo(startTime, timestamp2, videoPath, newPath.toString(), runtime);
            process.waitFor();
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        }

        //display clip
        new ViewClip(path + "\\Videos\\clippedvideo.mp4");
    }
}
