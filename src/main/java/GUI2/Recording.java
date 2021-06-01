package GUI2;

import ActionListeners.RecordingConnectDeviceButtonActionListener;
import ActionListeners.RecordingStartButtonActionListener;
import DeviceAPI.IORunnerSwingBuilder;
import DeviceAPI.MuseIORunner;
import DeviceAPI.MusePlayerRunner;
import DeviceAPI.PlayerRunnerSwingBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;


public class Recording {
    private JPanel p1;
    private JPanel uiPanel;
    private JPanel linksPanel;
    private JButton recordButton;
    private JButton sessionsButton;
    private JButton settingsButton;
    private JButton onlineButton;
    private JButton startRecordingButton;
    private JButton connectDeviceButton;
    Graph g;


    public Recording(JFrame frame, int width, int height, String name, String group, String videoPath, ArrayList<String> dataTypes, int variance) throws IOException, InterruptedException {
        //initialize frame
        frame.setSize(width, height);
        frame.setContentPane(p1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        //create graph and add chosen lines to be displayed
        XYChart chart = new XYChartBuilder().width(0).height(0).title("EOG Front Readings").xAxisTitle("Time (seconds)").yAxisTitle("").build();
        g = new Graph("Active data", new XChartPanel<XYChart>(chart), chart);
        g.chart.setTitle("Readings");
        for (String data : dataTypes) {
            g.addLine(data);
        }

        //create necessary muse connection and recording instances
        IORunnerSwingBuilder ioRunnerSwingBuilder = new IORunnerSwingBuilder(connectDeviceButton);
        MuseIORunner museIORunner = new MuseIORunner(ioRunnerSwingBuilder);
        PlayerRunnerSwingBuilder playerRunnerSwingBuilder = new PlayerRunnerSwingBuilder(dataTypes, variance, g);
        MusePlayerRunner musePlayerRunner = new MusePlayerRunner(name, group, videoPath, playerRunnerSwingBuilder);

        //add action listeners to all link buttons on right side of ui
        new LinkButtonsInit(p1, frame, onlineButton, recordButton, sessionsButton, settingsButton);

        //add remaining action listeners and tool tips
        connectDeviceButton.addActionListener(new RecordingConnectDeviceButtonActionListener(museIORunner, musePlayerRunner, connectDeviceButton));
        startRecordingButton.addActionListener(new RecordingStartButtonActionListener(musePlayerRunner, videoPath, g, dataTypes));
        startRecordingButton.setToolTipText("Open the selected video file and begin recording data.");
    }
}
