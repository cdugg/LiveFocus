package GUI2;

import ActionListeners.SessionsViewSelectedFilesButtonActionListener;
import FileAccess.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Sessions {
    JPanel p;
    List<RecordingFile> recordings = new ArrayList<RecordingFile>();
    private JPanel p1;
    private JPanel uiPanel;
    private JPanel linksPanel;
    private JButton onlineButton;
    private JButton recordButton;
    private JButton sessionsButton;
    private JButton settingsButton;
    private JButton viewSelectedFilesButton;
    private final JPanel listPanel;
    private JScrollPane scrollPane;

    public Sessions(JFrame frame, int width, int height) {
        frame.setSize(width, height);

        //set up panel for files to choose from, rows = number of files, columns = 2
        listPanel = new JPanel();
        java.awt.Color[] backgrounds = {new java.awt.Color(61, 63, 65), new java.awt.Color(51, 53, 55)};

        //get all files in the directory where they are saved -> sessionsFiles
        File[] sessionsFiles = {};
        SessionPath sessionPath = new SessionPath();
        File directory = new File(sessionPath.getSessionPath());
        sessionsFiles = directory.listFiles();

        //for the files found in the directory (filter out any that dont have .fd extension) -> recordings
        //each file that is found is an instance of RecordingFile
        for (File f : sessionsFiles) {
            RecordingFile r = new RecordingFile(f);
            if (!r.name.equals(""))
                recordings.add(r);
        }
        listPanel.setLayout(new GridLayout(recordings.size(), 2));

        //Add all found .fd files in recordings to the scroll panel with appropriate information and checkboxes to go with
        int i = 0;
        for (RecordingFile rcf : recordings) {
            JPanel lItem = new JPanel();
            lItem.setLayout(new GridLayout(3, 1));
            lItem.setBackground(backgrounds[i % 2]);
            JLabel l = new JLabel(rcf.name);
            l.setForeground(new java.awt.Color(187, 187, 187));
            lItem.add(l);
            JLabel l3 = new JLabel(rcf.group);
            l3.setForeground(new java.awt.Color(187, 187, 187));
            lItem.add(l3);
            JLabel l4 = new JLabel(rcf.date);
            l4.setForeground(new java.awt.Color(187, 187, 187));
            lItem.add(l4);
            listPanel.add(lItem);
            JCheckBox l2 = new JCheckBox("Check");
            l2.setHorizontalAlignment(SwingConstants.RIGHT);
            l2.setBackground(backgrounds[i % 2]);
            l2.setForeground(new java.awt.Color(187, 187, 187));
            listPanel.add(l2);
            i++;
        }
        listPanel.setBackground(backgrounds[0]);
        scrollPane.setViewportView(listPanel);

        //finish creating frame
        p = p1;
        frame.setContentPane(p);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        //add action listeners to all link buttons on right side of ui
        new LinkButtonsInit(p, frame, onlineButton, recordButton, sessionsButton, settingsButton);

        //add action listener and tool tip for the view selected files button
        viewSelectedFilesButton.setToolTipText("Open selected files in graph view. Must choose at least 1 file.");
        viewSelectedFilesButton.addActionListener(new SessionsViewSelectedFilesButtonActionListener(p, frame, recordings, listPanel));
    }
}
