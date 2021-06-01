package ActionListeners;

import GUI2.Graph;
import GUI2.RecordingFile;
import GUI2.ViewSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewSessionSignalSelectorActionListener implements ActionListener {
    ViewSession parent;
    String[] names;
    JComboBox signalSelector;
    Graph g;
    java.util.List<RecordingFile> files;
    JPanel options;

    public ViewSessionSignalSelectorActionListener(ViewSession parent1, String[] n, JComboBox combo, Graph graph, java.util.List<RecordingFile> fs, JPanel os) {
        parent = parent1;
        names = n;
        signalSelector = combo;
        g = graph;
        files = fs;
        options = os;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //get the new signal y values that match the current signal type selected in the JComboBox
        double[][] newSignals = new double[names.length][];
        String signalType = signalSelector.getSelectedItem().toString();
        int counter = 0;
        for (RecordingFile f : files) {
            newSignals[counter] = f.getSubList(parent.startDisplacement, parent.currentDisplacement, signalType);
            counter++;
        }
        newSignals[counter] = new double[100];

        //update the chart with the new values
        g.switchReadingType(signalType, newSignals, names);

        //reset graph so all lines are toggled on except for the average line
        for (Component c : options.getComponents()) {
            try {
                JCheckBox tempC = (JCheckBox) ((JPanel) c).getComponents()[1];
                tempC.setSelected(!tempC.getName().equals("Average"));
            } catch (Exception exception) {
            }
        }
        g.hideAverage();

        //update the selected count and the correlation coefficient
        parent.selectedCountInt = names.length - 1;
        parent.UpdateCorrelation(parent.selectedCountInt);
    }
}
