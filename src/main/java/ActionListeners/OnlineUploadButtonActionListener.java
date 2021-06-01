package ActionListeners;

import FileAccess.FileAccess;
import FileAccess.SessionPath;
import GUI2.RecordingFile;
import RestfulAPI.APICall;
import RestfulAPI.CallMaker;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class OnlineUploadButtonActionListener implements ActionListener {
    String username;
    CallMaker callMaker = new CallMaker();
    FileAccess fileAccess = new FileAccess();

    public OnlineUploadButtonActionListener(String user) {
        username = user;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //create a file chooser that only accepts files with the .fd extension
        JFileChooser fc = new JFileChooser();
        SessionPath sessionPath = new SessionPath();
        fc.setCurrentDirectory(new File(sessionPath.getSessionPath()));
        fc.setDialogTitle("Select a .fd file to upload");
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("fd Files", "fd");
        fc.setFileFilter(filter);
        fc.setApproveButtonText("Upload");

        //once a file is chosen if user presses upload button, attempt to upload file to server
        if (fc.showOpenDialog(fc.getParent()) == JFileChooser.APPROVE_OPTION) {
            RecordingFile recordingFile = new RecordingFile(fc.getSelectedFile());
            try {
                Boolean response = APICall.UploadFile(fc.getSelectedFile(), fc.getSelectedFile().getName(), username, recordingFile.date, recordingFile.group, callMaker, fileAccess);
                if(response){
                    JOptionPane.showMessageDialog(null, "File successfully uploaded.");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Error: File could not be uploaded.");
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
