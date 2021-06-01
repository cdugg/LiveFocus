package GUI2;

import ActionListeners.RecordInfoBrowseFilesButtonActionListener;
import ActionListeners.RecordInfoCancelButtonActionListener;
import ActionListeners.RecordInfoOkButtonActionListener;

import javax.swing.*;
import java.awt.event.ActionListener;

public class RecordInfo {
    private JFrame frame;
    private JButton okButton;
    private JButton cancelButton;
    private JPanel p1;
    private JTextField textFieldName;
    private JTextField textFieldGroup;
    private JButton browseFilesButton;
    private JCheckBox trackEEGDataCheckBox;
    private JCheckBox trackAccelerometerDataCheckBox;
    private JCheckBox trackEEG2DataCheckBox;
    private JTextField varianceTextField;

    public RecordInfo(JFrame frame, JFrame thisFrame, int width, int height) {
        //create frame to display recording form options
        thisFrame.setSize(width, height);
        thisFrame.setContentPane(p1);
        varianceTextField.setText("12"); //how big do you want the variance sliding window measurement to be? preset = 3 seconds
        thisFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        thisFrame.setVisible(true);

        //create and add necessary action listeners for the form
        ActionListener browseFilesAction = new RecordInfoBrowseFilesButtonActionListener(browseFilesButton);
        browseFilesButton.addActionListener(browseFilesAction);
        okButton.addActionListener(new RecordInfoOkButtonActionListener(textFieldName, textFieldGroup, trackEEGDataCheckBox, trackAccelerometerDataCheckBox, trackEEG2DataCheckBox, frame, thisFrame, browseFilesButton, varianceTextField));
        cancelButton.addActionListener(new RecordInfoCancelButtonActionListener(thisFrame));
    }
}
