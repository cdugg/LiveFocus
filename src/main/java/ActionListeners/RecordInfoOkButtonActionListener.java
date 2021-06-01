package ActionListeners;

import GUI2.Recording;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RecordInfoOkButtonActionListener implements ActionListener {
    private final ArrayList<String> dataTypes = new ArrayList<>();
    JTextField textFieldName;
    JTextField textFieldGroup;
    JCheckBox trackEEGDataCheckBox;
    JCheckBox trackAccelerometerDataCheckBox;
    JCheckBox trackEEG2DataCheckBox;
    JFrame frame;
    JFrame thisFrame;
    JButton videoPath;
    JTextField varianceTextField;

    public RecordInfoOkButtonActionListener(JTextField nameField, JTextField groupField, JCheckBox trackEEGDataCheckBox1, JCheckBox trackAccelerometerDataCheckBox1, JCheckBox trackEEG2DataCheckBox1, JFrame f1, JFrame f2, JButton videoP, JTextField varianceField) {
        textFieldName = nameField;
        textFieldGroup = groupField;
        trackEEGDataCheckBox = trackEEGDataCheckBox1;
        trackAccelerometerDataCheckBox = trackAccelerometerDataCheckBox1;
        trackEEG2DataCheckBox = trackEEG2DataCheckBox1;
        frame = f1;
        thisFrame = f2;
        videoPath = videoP;
        varianceTextField = varianceField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //get the name and group for the new file
        String name = textFieldName.getText();
        String group = textFieldGroup.getText();

        //add any selected reading types to the dataTypes list
        if (trackEEG2DataCheckBox.isSelected()) {
            dataTypes.add("Frontal EOG");
        }
        if (trackEEGDataCheckBox.isSelected()) {
            dataTypes.add("Rear EOG");
        }
        if (trackAccelerometerDataCheckBox.isSelected()) {
            dataTypes.add("Accelerometer");
        }

        //close the form frame
        thisFrame.dispose();

        //open a new recording window
        try {
            new Recording(frame, frame.getWidth(), frame.getHeight(), name, group, videoPath.getText(), dataTypes, Integer.parseInt(varianceTextField.getText()));
        } catch (Exception exception) {
        }
    }
}
