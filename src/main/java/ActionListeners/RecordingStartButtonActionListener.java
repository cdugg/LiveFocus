package ActionListeners;

import DeviceAPI.MusePlayerRunner;
import GUI2.Graph;
import GUI2.VideoRecording;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RecordingStartButtonActionListener implements ActionListener {
    private final Graph graph;
    private final MusePlayerRunner musePlayerRunner;
    private final String videoPath;
    private final ArrayList<String> dataTypes;

    public RecordingStartButtonActionListener(MusePlayerRunner musePlayerRunner, String videoPath, Graph graph, ArrayList<String> dataTypes) {
        this.musePlayerRunner = musePlayerRunner;
        this.videoPath = videoPath;
        this.graph = graph;
        this.dataTypes = dataTypes;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //create a new video recording window
        new VideoRecording(musePlayerRunner, videoPath, graph, dataTypes);
    }
}
