package ActionListeners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OnlineButtonActionListener implements ActionListener {
    private final JPanel loginPanel;
    private final JTextField username;
    private final JPasswordField password;
    private final JButton enter;
    private final JLabel invalidAccount;
    JFrame frame;
    JPanel p;
    JFrame loginFrame;

    public OnlineButtonActionListener(JPanel panel, JFrame parentFrame, JPanel loginP, JTextField user, JPasswordField pass, JButton enter1, JLabel invalid, JFrame loginF) {
        frame = parentFrame;
        p = panel;
        loginPanel = loginP;
        username = user;
        password = pass;
        enter = enter1;
        invalidAccount = invalid;
        loginFrame = loginF;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //create a new frame to take in login details
        loginFrame.setSize(450, 150);
        loginFrame.setContentPane(loginPanel);

        //add action listener for when details are submitted to server
        enter.addActionListener(new OnlineEntryFormActionListener(p, frame, username, password, invalidAccount, loginFrame, loginPanel));

        //make frame visible
        loginFrame.setVisible(true);
    }
}
