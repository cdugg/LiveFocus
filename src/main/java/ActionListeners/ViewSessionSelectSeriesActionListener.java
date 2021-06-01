package ActionListeners;

import GUI2.Graph;
import GUI2.ViewSession;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewSessionSelectSeriesActionListener implements ActionListener {
    ViewSession parent;
    JCheckBox item1;
    Graph g;

    public ViewSessionSelectSeriesActionListener(ViewSession parent1, JCheckBox item, Graph graph) {
        parent = parent1;
        item1 = item;
        g = graph;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //when a checkbox is clicked if it is on toggle the matching series off and update the selected count
        //if it is off toggle it on and update selected count
        if (!item1.isSelected()) {
            g.toggleSeriesOff(item1.getName());
            parent.selectedCountInt--;
        } else {
            g.toggleSeriesOn(item1.getName());
            parent.selectedCountInt++;
        }
        parent.UpdateCorrelation(parent.selectedCountInt);
    }
}
