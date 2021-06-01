package GUI2;

import ActionListeners.OnlineSearchButtonActionListener;
import ActionListeners.OnlineUploadButtonActionListener;
import org.json.JSONObject;

import javax.swing.*;
import java.util.ArrayList;

public class Online {
    JPanel p;
    private JPanel p1;
    private JPanel uiPanel;
    private JPanel linksPanel;
    private JButton recordButton;
    private JButton settingsButton;
    private JButton onlineButton;
    private JButton sessionsButton;
    public ArrayList<JSONObject> files = new ArrayList<>();
    private JTextField searchText;
    private JButton searchButton;
    private JScrollPane scrollPane;
    private JButton uploadFileButton;

    public Online(JFrame frame, int width, int height, String username) {
        //create frame and make visible
        frame.setSize(width, height);
        p = p1;
        frame.setContentPane(p);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        //add action listeners to all link buttons on right side of ui
        new LinkButtonsInit(p, frame, null, recordButton, sessionsButton, settingsButton);

        //add remaining action listeners and tool tips
        searchButton.addActionListener(new OnlineSearchButtonActionListener(files, scrollPane, p, searchText));
        uploadFileButton.addActionListener(new OnlineUploadButtonActionListener(username));
        uploadFileButton.setToolTipText("Upload a recording file to the server.");
    }
}
