package ActionListeners;

import GUI2.RecordingFile;
import GUI2.ViewSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SessionsViewSelectedFilesButtonActionListener implements ActionListener {
    JPanel p;
    JFrame frame;
    List<RecordingFile> recordings;
    JPanel listPanel;

    public SessionsViewSelectedFilesButtonActionListener(JPanel panel, JFrame f, List<RecordingFile> recordings1, JPanel lPanel) {
        p = panel;
        frame = f;
        recordings = recordings1;
        listPanel = lPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean[] checked = new boolean[listPanel.getComponentCount() / 2];

        //which files are selected -> checked
        int i = 0;
        for (Component check : listPanel.getComponents()) {
            if (i % 2 == 1 && ((JCheckBox) check).isSelected())
                checked[(i - 1) / 2] = true;
            else if (i % 2 == 1)
                checked[(i - 1) / 2] = false;
            i++;
        }
        List<RecordingFile> selected = new ArrayList<RecordingFile>();

        //add checked files to selected
        int j = 0;
        for (boolean b : checked) {
            if (b) {
                selected.add(recordings.get(j));
            }
            j++;
        }

        //build viewing frame for list of files selected
        if (selected.size() > 0) {
            p.removeAll();
            int h = frame.getHeight();
            int w = frame.getWidth();
            try {
                new ViewSession(frame, w, h, selected);
            } catch (Exception exception) {
                //Couldn't create new Panel
            }
        }
    }
}
