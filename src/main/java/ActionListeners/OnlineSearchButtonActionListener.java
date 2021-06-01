package ActionListeners;

import RestfulAPI.APICall;
import RestfulAPI.CallMaker;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class OnlineSearchButtonActionListener implements ActionListener {
    public Color[] colors = new Color[]{new java.awt.Color(61, 63, 65), new java.awt.Color(51, 53, 55)};
    ArrayList<JSONObject> files;
    JScrollPane scrollPane;
    JPanel p;
    JTextField searchText;
    CallMaker callMaker = new CallMaker();

    public OnlineSearchButtonActionListener(ArrayList<JSONObject> f, JScrollPane scroll, JPanel panel, JTextField searchField) {
        files = f;
        scrollPane = scroll;
        p = panel;
        searchText = searchField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            //send sever request for any files matching the search string
            JPanel resultsPanel = new JPanel();
            files = APICall.search(searchText.getText(), callMaker);
            resultsPanel.setLayout(new GridLayout(files.size(), 1));
            int colorAlternation = 0;

            //for any item that is returned by the server, add it to the display panel with the file info and a download button
            //add an action listener for the download button
            for (JSONObject item : files) {
                JPanel temp = new JPanel();
                temp.setLayout(new GridLayout(1, 2));
                JLabel tempLabel = new JLabel((String) item.get("filename"));
                tempLabel.setForeground(new java.awt.Color(187, 187, 187));
                temp.add(tempLabel);
                JButton download = new JButton("Download");
                download.setHorizontalAlignment(SwingConstants.RIGHT);
                download.addActionListener(new OnlineDownloadButtonActionListener(item));

                //background color of each item alternates for visibility
                temp.add(download);
                temp.setBackground(colors[colorAlternation]);
                if (colorAlternation == 0) {
                    colorAlternation++;
                } else {
                    colorAlternation--;
                }
                resultsPanel.add(temp);
            }
            //update the ui so everything is displayed
            scrollPane.setViewportView(resultsPanel);
            p.updateUI();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
