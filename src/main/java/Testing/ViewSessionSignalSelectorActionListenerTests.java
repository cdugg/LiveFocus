package Testing;

import ActionListeners.ViewSessionSignalSelectorActionListener;
import GUI2.Graph;
import GUI2.RecordingFile;
import GUI2.ViewSession;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class ViewSessionSignalSelectorActionListenerTests {
    ActionEvent mockAction;
    ViewSession mockView;
    String[] mockStrings = {"A", "B", "C"};
    JComboBox mockCombo;
    Graph mockGraph;
    java.util.List<RecordingFile> mockFiles = new ArrayList<RecordingFile>();
    JPanel mockPanel;
    RecordingFile mockRecordingFile;
    ViewSessionSignalSelectorActionListener test;

    @Before
    public void init() {
        mockView = mock(ViewSession.class);
        mockCombo = mock(JComboBox.class);
        mockGraph = mock(Graph.class);
        mockRecordingFile = mock(RecordingFile.class);
        mockFiles.add(mockRecordingFile);
        mockFiles.add(mockRecordingFile);
        mockAction = mock(ActionEvent.class);
        mockPanel = new JPanel();
        test = new ViewSessionSignalSelectorActionListener(mockView, mockStrings, mockCombo, mockGraph, mockFiles, mockPanel);
    }

    @Test
    public void ViewSessionSignalSelectorActionListenerTests1() {
        //validate comboBoxAction
        when(mockRecordingFile.getSubList(0, 100, "EOG")).thenReturn(new double[100]);
        when(mockCombo.getSelectedItem()).thenReturn("EOG");
        test.actionPerformed(mockAction);
        verify(mockGraph, times(1)).switchReadingType(anyString(), anyObject(), anyObject());
        verify(mockGraph, times(1)).hideAverage();
        verify(mockView, times(1)).UpdateCorrelation(anyInt());
    }
}
