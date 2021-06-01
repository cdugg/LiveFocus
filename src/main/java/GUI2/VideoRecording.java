package GUI2;

import DeviceAPI.MusePlayerRunner;
import DeviceAPI.PlayerRunnerSwingBuilder;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class VideoRecording {
    public Media media;
    public MediaPlayer mediaPlayer;
    public MediaView mediaView;
    private final MusePlayerRunner musePlayerRunner;
    private final String videoPath;
    public boolean isRecording = false;
    public boolean videoExists;
    public boolean isFinished = false;
    ArrayList<String> dataTypes;
    Graph g;


    public VideoRecording(MusePlayerRunner musePlayerRunner, String videoPath, Graph g, ArrayList<String> dataTypes) {
        this.g = g;
        this.videoPath = videoPath;
        videoExists = new File(videoPath).exists();
        System.out.println(videoPath);
        System.out.println(videoExists);
        this.musePlayerRunner = musePlayerRunner;
        this.dataTypes = dataTypes;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initFrame();
            }
        });
    }

    private void initFrame() {
        JFrame frame = new JFrame("Recording");
        final JFXPanel fxPanel = new JFXPanel();

        g.cp.setBounds(50, 600, 600, 200);
        frame.add(g.cp);

        JButton start = new JButton("Start Recording");
        start.setBounds(50, 800, 150, 25);
        start.addActionListener(e -> {
            if (!isRecording) {
                if (videoExists) {
                    mediaPlayer.play();
                }
                musePlayerRunner.StartRecording();
                isRecording = true;
                start.setText("Pause Recording");
            }
            else{

                if(videoExists) {
                    mediaPlayer.pause();
                }
                musePlayerRunner.PauseRecording();
                isRecording = false;
                start.setText("Continue Recording");
            }
        });
        JButton finish = new JButton("Finish Recording");
        finish.setBounds(500, 800, 150, 25);
        finish.addActionListener(e -> {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            if(!isFinished) {
                try {
                    musePlayerRunner.EndRecording();
                    mediaPlayer.stop();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        int yChange = 0;

        for(String data : dataTypes) {
            JCheckBox checkBox = new JCheckBox(data);
            checkBox.setName(data);
            checkBox.setSelected(true);
            checkBox.setBounds(700, 600 + yChange, 150, 30);
            checkBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (checkBox.isSelected()) { //toggle graph line on
                        g.toggleSeriesOn(checkBox.getName());
                    } else {
                        g.toggleSeriesOff(checkBox.getName());

                    }
                }
            });
            frame.add(checkBox);
            yChange += 40;
        }

        frame.add(start);
        frame.add(finish);
        frame.add(fxPanel);
        frame.setSize(900, 1000);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Platform.setImplicitExit(false);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(videoExists) {
                    initFX(fxPanel);
                }
            }
        });
    }

    private void initFX(JFXPanel fxPanel) {
        Scene scene = createScene();
        fxPanel.setScene(scene);
    }

    private Scene createScene() {
        Group root = new Group();
        String path = videoPath;
        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                try {
                    if(!isFinished) {
                        musePlayerRunner.EndRecording();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        mediaView = new MediaView(mediaPlayer);
        mediaView.setFitHeight(700);
        mediaView.setFitWidth(700);
        mediaPlayer.setAutoPlay(false);
        root.getChildren().add(mediaView);
        Scene scene = new Scene(root, Color.WHITE);

        return (scene);
    }
}