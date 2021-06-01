package ActionListeners;

import GUI2.Graph;
import GUI2.RecordingFile;
import GUI2.ViewSession;
import Maths.Processing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class ViewSessionMoveRightActionListener implements ActionListener {
    ViewSession parent;
    String[] names;
    int minFileSize;
    JComboBox signalSelector;
    Graph g;
    JPanel options;
    java.util.List<RecordingFile> files;

    public ViewSessionMoveRightActionListener(ViewSession parent1, String[] ns, int minFile, JComboBox signal, Graph graph, JPanel os, java.util.List<RecordingFile> fs) {
        parent = parent1;
        names = ns;
        minFileSize = minFile;
        signalSelector = signal;
        g = graph;
        options = os;
        files = fs;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //if the current displacement is less than 100 the graph has less than 100 datapoint and cant move either direction
        if (parent.currentDisplacement != minFileSize) {
            //determine how far to the right we can move. no option to go into additional time values
            int add;
            if (parent.currentDisplacement + parent.displacementToAdd >= minFileSize) {
                add = minFileSize - parent.currentDisplacement;
            } else {
                add = parent.displacementToAdd;
            }

            //adjust values in parent class
            parent.currentDisplacement += add;
            parent.startDisplacement += add;

            //get all the updated series data sets
            double[][] series = new double[names.length][];
            int i = 0;
            String signalType = signalSelector.getSelectedItem().toString();
            for (RecordingFile f : files) {
                series[i] = f.getSubList(parent.startDisplacement, parent.currentDisplacement, signalType);
                i++;
            }

            //update graph with new series data
            g.moveCountRight(series, names, add);

            //make sure all series that were selected previously are still toggled on
            Component[] checks = options.getComponents();
            Component finalComp = checks[checks.length - 1];
            try {
                JPanel finalPanel = (JPanel) finalComp;
                Component[] finalPanelComponents = finalPanel.getComponents();
                if (((JCheckBox) finalPanelComponents[finalPanelComponents.length - 1]).isSelected()) {
                    double[][] series2 = Arrays.copyOf(series, series.length - 1);
                    double[] tempAverages = Processing.average(series2);
                    g.updateAverage(tempAverages);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
