package GUI2;

import ActionListeners.*;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewSession {
    JPanel p;
    private final String videoPath;
    public int currentDisplacement;
    int minFileSize;
    Graph g;
    String[] names;
    public int displacementToAdd = 50;
    private JPanel p1;
    private JPanel uiPanel;
    private JPanel linksPanel;
    private JButton onlineButton;
    private JButton recordButton;
    private JButton sessionsButton;
    private JButton settingsButton;
    private JScrollPane scrollPane;
    public int startDisplacement;
    private JButton moveRightButton;
    private JTextField startTimeField;
    private JButton viewClipButton;
    private JTextField endTimeField;
    private JButton moveLeftButton;
    private JScrollPane selectionsPane;
    private JScrollPane graphInfo;
    private JSlider slider1;
    private JPanel clipPanel1;
    private JPanel graphMovePanel;
    private JLabel clipText;
    private JPanel clipPanel2;
    private JPanel clipPanel;
    private JLabel correlationCoefficientText;
    public Media media;
    public MediaPlayer mediaPlayer;
    public MediaView mediaView;
    public JPanel options;
    public int selectedCountInt;

    public ViewSession(JFrame frame, int width, int height, List<RecordingFile> files) {
        videoPath = files.get(0).videoPath;
        frame.setSize(width, height);

        final JFXPanel fxPanel = new JFXPanel();

        //create graph and add a line for each of the selected files and one for the average, save the names of the lines
        //initialize page with all series except average toggled on and save number toggled on, add chart to panel
        XYChart chart = new XYChartBuilder().width(0).height(0).title("EOG Front Readings").xAxisTitle("Time (seconds)").yAxisTitle("").build();
        g = new Graph("EOG Front", new XChartPanel<XYChart>(chart), chart);
        minFileSize = -1;
        names = new String[files.size() + 1];
        int i = 0;
        for (RecordingFile f : files) {
            if (minFileSize == -1 || f.eogFrontData.size() < minFileSize) {
                minFileSize = f.eogFrontData.size();
            }
            g.addLine(f.name.substring(6));
            names[i] = f.name.substring(6);
            i++;
        }
        g.addAverage();
        names[i] = "Average";
        scrollPane.setViewportView(g.cp);
        selectedCountInt = names.length - 1;

        //update graph -> if the smallest file size is < 100 data points fill preceeding times with 0
        //otherwise update graph with first 100 datapoints of all selected series
        currentDisplacement = 0;
        currentDisplacement = Math.min(minFileSize, 100);
        double[][] series = new double[names.length][currentDisplacement];
        i = 0;
        for (RecordingFile f : files) {
            series[i] = f.getSubList(startDisplacement, currentDisplacement, "EOG Front");
            i++;
        }
        g.moveCountRight(series, names, currentDisplacement);


        //create options panel
        options = new JPanel();
        options.setLayout(new GridLayout(names.length + 1, 1));

        //add combo box to allow graph to swap between signals
        //will still display selected series if all values are 0
        String[] signalTypes = {"EOG Front", "EOG Rear", "ACC"};
        JComboBox signalSelector = new JComboBox(signalTypes);
        signalSelector.setToolTipText("Choose which signal the graph should display.");
        signalSelector.addActionListener(new ViewSessionSignalSelectorActionListener(this, names, signalSelector, g, files, options));
        options.add(signalSelector);

        //add an info line and toggle check box for each series on the graph including average
        //color behind each line alternates for better visibility
        Color[] alternatingColors = new Color[]{new java.awt.Color(60, 63, 65), new java.awt.Color(51, 53, 55)};
        Color fontColor = new java.awt.Color(183, 183, 183);
        int u = 0;
        GridLayout l = new GridLayout(1, 2);
        for (String name : names) {
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(l);
            itemPanel.setBackground(alternatingColors[u]);

            JLabel lineInfo = new JLabel(name);
            lineInfo.setForeground(fontColor);
            itemPanel.add(lineInfo);

            JCheckBox item1 = new JCheckBox("Toggle");
            item1.setHorizontalAlignment(SwingConstants.RIGHT);
            item1.setName(name);
            item1.setBackground(alternatingColors[u]);
            item1.setForeground(fontColor);

            //add appropriate action listeners to each checkbox in the menu
            if (!name.equals("Average")) {
                item1.setSelected(true);
                item1.addActionListener(new ViewSessionSelectSeriesActionListener(this, item1, g));
            } else {
                item1.addActionListener(new ViewSessionAverageSelectorActionListener(this, item1, names, signalSelector, g, files));
            }
            itemPanel.add(item1);
            options.add(itemPanel);
            u = (u + 1) % 2;
        }
        selectionsPane.setViewportView(options);
        UpdateCorrelation(selectedCountInt);

        //refresh and display view session panel
        p = p1;
        frame.setContentPane(p);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        //adjust sizes of panels so they retain format and window can be resized without issues
        Dimension selectMax = new java.awt.Dimension(selectionsPane.getWidth(), (height / 2) - 46);
        selectionsPane.setPreferredSize(selectMax);
        selectionsPane.setMaximumSize(selectMax);
        selectionsPane.setSize(selectMax);
        clipText.setBounds(clipText.getX(), selectionsPane.getY() + selectionsPane.getHeight() + 5, clipText.getWidth(), clipText.getHeight());
        clipPanel.setBounds(0, clipText.getY() + clipText.getHeight() + 5, clipPanel.getWidth(), clipPanel.getHeight());
        graphMovePanel.setBounds(0, clipPanel.getY() + clipPanel.getHeight() + 5, graphMovePanel.getWidth(), graphMovePanel.getHeight());
        int startY = graphMovePanel.getY() + graphMovePanel.getHeight() + 5;
        Dimension chartMax = new java.awt.Dimension(scrollPane.getWidth(), height - startY);
        scrollPane.setPreferredSize(chartMax);
        scrollPane.setMaximumSize(chartMax);
        scrollPane.setBounds(0, startY, scrollPane.getWidth(), height - startY - 40);

        //add action listener to graph movement slider
        slider1.addChangeListener(new ViewSessionSliderChangeListener(this, slider1));

        //add action listeners to all link buttons on right side of ui
        new LinkButtonsInit(p, frame, onlineButton, recordButton, sessionsButton, settingsButton);

        //add action listeners and tool tips to movement and clip buttons
        moveLeftButton.addActionListener(new ViewSessionMoveLeftActionListener(this, names, minFileSize, signalSelector, g, options, files));
        moveLeftButton.setToolTipText("Move the current graph to the left. Use the slider to select how much the graph will move.");
        moveRightButton.addActionListener(new ViewSessionMoveRightActionListener(this, names, minFileSize, signalSelector, g, options, files));
        moveRightButton.setToolTipText("Move the current graph to the right. Use the slider to select how much the graph will move.");
        viewClipButton.addActionListener(new ViewSessionClipButtonActionListener(startTimeField, endTimeField, videoPath));
        viewClipButton.setToolTipText("View a part of the video for this session based on the start and finish times to the left and right of this button.");
    }

    //If 2 series are selected in the upper menu, show the correlation coefficient of the two series
    //If more than 2 are selected do not calculate and say unavailable
    public void UpdateCorrelation(int selectedCount) {
        if (selectedCount == 2) {
            ArrayList<String> correlationNames = new ArrayList<>();
            for (Component p : options.getComponents()) {
                if (p instanceof JPanel) {
                    try {
                        JCheckBox selected = (JCheckBox) ((JPanel) p).getComponents()[1];
                        if (selected.isSelected()) {
                            correlationNames.add(selected.getName());
                        }
                    } catch (Exception exception) {
                        System.out.println("uh expect an error soon");
                    }
                }
            }
            double[] correlationA = g.getSeries(correlationNames.get(0));
            double[] correlationB = g.getSeries(correlationNames.get(1));
            if (g.times[0] < 0) {
                int start = (int) (-g.times[0] / .25);
                correlationA = Arrays.copyOfRange(correlationA, start, correlationA.length);
                correlationB = Arrays.copyOfRange(correlationB, start, correlationB.length);
            }
            correlationCoefficientText.setText("Correlation Coefficient: " + Maths.Processing.correlation(correlationA, correlationB));
        } else {
            correlationCoefficientText.setText("Correlation Coefficient: Unavailable");
        }
    }

    public int getCurrentDisplacement() {
        return currentDisplacement;
    }
}
