package ActionListeners;

import GUI2.Record;
import GUI2.Sessions;
import GUI2.Settings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OtherLinksButtonActionListener implements ActionListener {
    int type;
    JPanel p;
    JFrame frame;

    public OtherLinksButtonActionListener(int pageType, JPanel panel, JFrame parentFrame) {
        type = pageType;
        p = panel;
        frame = parentFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //based on this classes pageType: clear contents of current jFrame and initialize matching new frame
        p.removeAll();
        int h = frame.getHeight();
        int w = frame.getWidth();
        if (type == 0) {
            new Record(frame, w, h);
        } else if (type == 1) {
            new Sessions(frame, w, h);
        } else {
            new Settings(frame, w, h);
        }
    }
}
