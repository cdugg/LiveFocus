package ActionListeners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecordInfoCancelButtonActionListener implements ActionListener {
    JFrame frame;

    public RecordInfoCancelButtonActionListener(JFrame f) {
        frame = f;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //close the window
        frame.dispose();
    }
}
