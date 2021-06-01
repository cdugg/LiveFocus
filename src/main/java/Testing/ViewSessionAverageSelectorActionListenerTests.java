package Testing;

import ActionListeners.ViewSessionAverageSelectorActionListener;
import GUI2.Graph;
import GUI2.RecordingFile;
import GUI2.ViewSession;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class ViewSessionAverageSelectorActionListenerTests {
    JCheckBox mockCheck;
    String[] mockNames = {"A", "B", "C"};
    JComboBox mockCombo;
    Graph mockGraph;
    RecordingFile mockFile;
    java.util.List<RecordingFile> mockFiles = new ArrayList<RecordingFile>();
    ViewSession mockView;
    ActionEvent mockAction;
    ViewSessionAverageSelectorActionListener test;

    @Before
    public void init() {
        mockCheck = new JCheckBox();
        mockCheck.setSelected(true);
        mockCombo = mock(JComboBox.class);
        mockGraph = mock(Graph.class);
        mockFile = mock(RecordingFile.class);
        mockView = mock(ViewSession.class);
        mockFiles.add(mockFile);
        mockFiles.add(mockFile);
        mockAction = mock(ActionEvent.class);
        test = new ViewSessionAverageSelectorActionListener(mockView, mockCheck, mockNames, mockCombo, mockGraph, mockFiles);
    }

    @Test
    public void ViewSessionAverageSelectorActionListenerTests1() {
        when(mockCombo.getSelectedItem()).thenReturn("EOG");
        when(mockFile.getSubList(anyInt(), anyInt(), anyString())).thenReturn(new double[100]);
        test.actionPerformed(mockAction);
        verify(mockGraph, times(1)).updateAverage(anyObject());
    }
}
