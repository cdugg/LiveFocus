package ActionListeners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SettingsSaveDirectoryButtonActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        //create a file chooser that only accepts directories for the new location that all created files will be stored
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("C://"));
        fc.setDialogTitle("Select new place to save your files.");
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        //when a file is chosen update the saveDirectory.txt file
        if (fc.showOpenDialog(fc.getParent()) == JFileChooser.APPROVE_OPTION) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Cormac\\IdeaProjects\\2021-ca400-duggac27-hammons2\\src\\saveDirectory.txt", false));
                writer.write(fc.getSelectedFile().getPath());
                writer.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
