package ActionListeners;

import GUI2.ViewSession;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ViewSessionSliderChangeListener implements ChangeListener {
    ViewSession parent;
    JSlider slider1;

    public ViewSessionSliderChangeListener(ViewSession parent1, JSlider slider) {
        parent = parent1;
        slider1 = slider;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        //update the displacement value in the current viewSession class
        parent.displacementToAdd = slider1.getValue();
    }
}
