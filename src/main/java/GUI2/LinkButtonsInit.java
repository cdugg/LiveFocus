package GUI2;

import ActionListeners.OnlineButtonActionListener;
import ActionListeners.OtherLinksButtonActionListener;

import javax.swing.*;

public class LinkButtonsInit {
    private JPanel loginPanel;
    private JTextField username;
    private JPasswordField password;
    private JButton enter;
    private JLabel invalidAccount;

    //Adds common buttons on right side of UI to each new UI page instance
    public LinkButtonsInit(JPanel p, JFrame frame, JButton onlineButton, JButton recordButton, JButton sessionsButton, JButton settingsButton) {
        //if already on the online page onlineButton will be null and will not get an action listener or tool tip
        //otherwise add all action listeners for each of the right side link buttons and their tool tips
        if (onlineButton != null) {
            onlineButton.addActionListener(new OnlineButtonActionListener(p, frame, loginPanel, username, password, enter, invalidAccount, new JFrame()));
            onlineButton.setToolTipText("Upload and Download sessions online.");
        }
        recordButton.addActionListener(new OtherLinksButtonActionListener(0, p, frame));
        recordButton.setToolTipText("Go to recording page.");
        sessionsButton.addActionListener(new OtherLinksButtonActionListener(1, p, frame));
        sessionsButton.setToolTipText("Go to sessions page.");
        settingsButton.addActionListener(new OtherLinksButtonActionListener(2, p, frame));
        settingsButton.setToolTipText("Go to settings page.");
    }
}
