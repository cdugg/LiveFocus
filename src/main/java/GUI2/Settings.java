package GUI2;

import ActionListeners.SettingsSaveDirectoryButtonActionListener;

import javax.swing.*;

public class Settings {
    JPanel p;
    private JPanel p1;
    private JPanel uiPanel;
    private JPanel linksPanel;
    private JButton onlineButton;
    private JButton recordButton;
    private JButton sessionsButton;
    private JButton settingsButton;
    private JButton saveDirectoryButton;

    public Settings(JFrame frame, int width, int height) {
        //refresh and display new settings panel
        frame.setSize(width, height);
        p = p1;
        frame.setContentPane(p);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        //set action listeners for all buttons on the panel
        new LinkButtonsInit(p, frame, onlineButton, recordButton, sessionsButton, settingsButton);
        saveDirectoryButton.addActionListener(new SettingsSaveDirectoryButtonActionListener());
    }
}
