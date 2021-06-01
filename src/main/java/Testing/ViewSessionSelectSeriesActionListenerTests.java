package Testing;

import ActionListeners.ViewSessionSelectSeriesActionListener;
import GUI2.Graph;
import GUI2.ViewSession;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.mockito.Mockito.*;

public class ViewSessionSelectSeriesActionListenerTests {
    ViewSession mockSession;
    JCheckBox mockCheckBox;
    Graph mockGraph;
    ViewSessionSelectSeriesActionListener test;
    ActionEvent action;

    @Before
    public void init() {
        mockSession = mock(ViewSession.class);
        mockCheckBox = new JCheckBox();
        mockGraph = mock(Graph.class);
        test = new ViewSessionSelectSeriesActionListener(mockSession, mockCheckBox, mockGraph);
        action = mock(ActionEvent.class);
    }

    @Test
    public void ViewSessionSelectSeriesActionListenerTests1() {
        mockCheckBox.setSelected(false);
        test.actionPerformed(action);
        verify(mockGraph, times(1)).toggleSeriesOff(anyString());
        mockCheckBox.setSelected(true);
        test.actionPerformed(action);
        verify(mockGraph, times(1)).toggleSeriesOn(anyString());
        verify(mockSession, times(2)).UpdateCorrelation(anyInt());
    }
}
