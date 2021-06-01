package GUI2;

import ActionListeners.RecordNewRecordingButtonActionListener;

import javax.swing.*;

public class Record {
    private JPanel uiPanel;
    private JPanel linksPanel;
    private JPanel p1;
    private JButton recordButton;
    private JButton settingsButton;
    private JButton onlineButton;
    private JButton sessionsButton;
    private JButton newRecordingButton;

    public Record(JFrame frame, int width, int height) {
        //create frame
        frame.setSize(width, height);
        frame.setContentPane(p1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        //add action listeners to all link buttons on right side of ui
        new LinkButtonsInit(p1, frame, onlineButton, recordButton, sessionsButton, settingsButton);

        //add remaining necessary action listeners and tool tips
        newRecordingButton.addActionListener(new RecordNewRecordingButtonActionListener(frame));
        newRecordingButton.setToolTipText("Create a new recording.");
    }
}
