package ActionListeners;

import GUI2.RecordInfo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecordNewRecordingButtonActionListener implements ActionListener {
    JFrame frame;

    public RecordNewRecordingButtonActionListener(JFrame parentFrame) {
        frame = parentFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //create jFrame for new recording form
        JFrame formWindow = new JFrame();
        new RecordInfo(frame, formWindow, 700, 500);
    }
}
