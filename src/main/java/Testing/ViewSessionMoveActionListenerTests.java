package Testing;

import ActionListeners.ViewSessionMoveLeftActionListener;
import ActionListeners.ViewSessionMoveRightActionListener;
import GUI2.Graph;
import GUI2.RecordingFile;
import GUI2.ViewSession;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class ViewSessionMoveActionListenerTests {
    ViewSession mockView;
    String[] mockNames = {"A", "B", "C"};
    int mockMin = 300;
    JComboBox mockCombo;
    Graph mockGraph;
    JPanel mockPanel;
    java.util.List<RecordingFile> mockFiles = new ArrayList<RecordingFile>();
    RecordingFile mockFile;
    ActionEvent action;
    ViewSessionMoveRightActionListener test1;
    ViewSessionMoveLeftActionListener test2;
    JPanel testPanel;
    JCheckBox testCheck;

    @Before
    public void init() {
        mockView = mock(ViewSession.class);
        mockCombo = mock(JComboBox.class);
        mockGraph = mock(Graph.class);
        action = mock(ActionEvent.class);
        mockFile = mock(RecordingFile.class);
        mockPanel = mock(JPanel.class);
        testPanel = new JPanel();
        testCheck = new JCheckBox();
        testCheck.setSelected(true);
        testPanel.add(testCheck);
        mockFiles.add(mockFile);
        mockFiles.add(mockFile);
        test1 = new ViewSessionMoveRightActionListener(mockView, mockNames, mockMin, mockCombo, mockGraph, mockPanel, mockFiles);
        test2 = new ViewSessionMoveLeftActionListener(mockView, mockNames, mockMin, mockCombo, mockGraph, mockPanel, mockFiles);
    }

    @Test
    public void ViewSessionMoveRightActionListenerTests1() {
        when(mockCombo.getSelectedItem()).thenReturn("EOG");
        when(mockFile.getSubList(anyInt(), anyInt(), anyString())).thenReturn(new double[100]);
        when(mockPanel.getComponents()).thenReturn(new Component[]{testPanel});
        test1.actionPerformed(action);
        verify(mockGraph, times(1)).moveCountRight(anyObject(), anyObject(), anyInt());
        verify(mockGraph, times(1)).updateAverage(anyObject());
    }

    @Test
    public void ViewSessionMoveLeftActionListenerTests1() {
        when(mockView.getCurrentDisplacement()).thenReturn(101);
        when(mockCombo.getSelectedItem()).thenReturn("EOG");
        when(mockFile.getSubList(anyInt(), anyInt(), anyString())).thenReturn(new double[100]);
        when(mockPanel.getComponents()).thenReturn(new Component[]{testPanel});
        test2.actionPerformed(action);
        verify(mockGraph, times(1)).moveCountLeft(anyObject(), anyObject(), anyInt());
        verify(mockGraph, times(1)).updateAverage(anyObject());
    }
}
