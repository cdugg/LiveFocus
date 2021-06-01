package ActionListeners;

import GUI2.Online;
import RestfulAPI.APICall;
import RestfulAPI.CallMaker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class OnlineEntryFormActionListener implements ActionListener {
    JPanel p;
    JFrame frame;
    JTextField username;
    JPasswordField password;
    JLabel invalidAccount;
    JFrame loginFrame;
    JPanel loginPanel;
    CallMaker callMaker = new CallMaker();

    public OnlineEntryFormActionListener(JPanel panel, JFrame parentFrame, JTextField user, JPasswordField pass, JLabel invalid, JFrame loginF, JPanel loginP) {
        p = panel;
        frame = parentFrame;
        username = user;
        password = pass;
        invalidAccount = invalid;
        loginFrame = loginF;
        loginPanel = loginP;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //request entry to online from server with given credentials
        try {
            String pass = Arrays.toString(password.getPassword()).substring(1, 3 * password.getPassword().length - 1).replaceAll(", ", "");
            if (APICall.login(username.getText(), pass, callMaker)) {
                loginFrame.dispose();
                p.removeAll();
                int h = frame.getHeight();
                int w = frame.getWidth();
                new Online(frame, w, h, username.getText());
            }
        } catch (Exception except) {
            //if the credentials don't match any on server or the server times out display text update for user
            invalidAccount.setVisible(true);
            loginPanel.updateUI();
        }
    }
}
