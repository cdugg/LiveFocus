package GUI2;
import DeviceAPI.MusePlayerRunner;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class ViewClip {
    public Media media;
    public MediaPlayer mediaPlayer;
    public MediaView mediaView;
    private final String videoPath;

    public ViewClip(String videoPath) {
        this.videoPath = videoPath;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initFrame();
            }
        });
    }

    private void initFX(JFXPanel fxPanel) {
        Scene scene = createScene();
        fxPanel.setScene(scene);
    }

    private void initFrame() {
        JFrame frame = new JFrame("Recording");
        final JFXPanel fxPanel = new JFXPanel();
        frame.add(fxPanel);
        frame.setSize(700, 700);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Platform.setImplicitExit(false);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
        });
    }

    private Scene createScene() {
        Group root = new Group();
        String path = videoPath;
        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView = new MediaView(mediaPlayer);
        mediaView.setFitHeight(500);
        mediaView.setFitWidth(500);
        mediaPlayer.setAutoPlay(true);
        root.getChildren().add(mediaView);
        Scene scene = new Scene(root, Color.WHITE);

        return (scene);
    }
}
