package ActionListeners;

import GUI2.Graph;
import GUI2.RecordingFile;
import GUI2.ViewSession;
import Maths.Processing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewSessionAverageSelectorActionListener implements ActionListener {
    ViewSession parent;
    JCheckBox item1;
    String[] names;
    JComboBox signalSelector;
    Graph g;
    java.util.List<RecordingFile> files;

    public ViewSessionAverageSelectorActionListener(ViewSession parent1, JCheckBox i, String[] ns, JComboBox selector, Graph graph, java.util.List<RecordingFile> fs) {
        parent = parent1;
        item1 = i;
        names = ns;
        signalSelector = selector;
        g = graph;
        files = fs;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //if the average is toggled on update all the values in the average data list and display the average line
        //otherwise hide the average line
        if (item1.isSelected()) {
            double[][] signals = new double[names.length - 1][];
            String signalType = signalSelector.getSelectedItem().toString();
            int counter = 0;
            for (RecordingFile f : files) {
                signals[counter] = f.getSubList(parent.startDisplacement, parent.currentDisplacement, signalType);
                counter++;
            }
            double[] averages = Processing.average(signals);
            g.updateAverage(averages);
            g.showAverage();
        } else {
            g.hideAverage();
        }
    }
}
