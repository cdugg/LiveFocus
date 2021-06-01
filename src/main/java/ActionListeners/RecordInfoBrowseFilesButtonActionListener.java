package ActionListeners;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class RecordInfoBrowseFilesButtonActionListener implements ActionListener {
    public String group = "N/A";
    public String videoPath = "N/A";
    JButton browseFilesButton;

    public RecordInfoBrowseFilesButtonActionListener(JButton browse) {
        browseFilesButton = browse;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //create file chooser that only accepts .mp4 and .mpeg formats (formats supported by ffmpeg)
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("C://"));
        fc.setDialogTitle("Select a video file to attach");
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Video Files", "mp4", "mpeg");
        fc.setFileFilter(filter);

        //when appropriate file is selected get the path of the file and save it as the buttons name
        if (fc.showOpenDialog(fc.getParent()) == JFileChooser.APPROVE_OPTION) {
            videoPath = fc.getSelectedFile().getPath();
            browseFilesButton.setText(fc.getSelectedFile().toString());
        }
    }
}
