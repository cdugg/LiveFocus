package Testing;

import ActionListeners.OtherLinksButtonActionListener;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.mockito.Mockito.*;

public class OtherLinksButtonActionListenerTests {
    int mockPage = 2;
    JPanel mockPanel;
    JFrame mockFrame;
    ActionEvent mockAction;
    OtherLinksButtonActionListener test;

    @Before
    public void init() {
        mockPanel = mock(JPanel.class);
        mockFrame = mock(JFrame.class);
        mockAction = mock(ActionEvent.class);
        test = new OtherLinksButtonActionListener(mockPage, mockPanel, mockFrame);
    }

    @Test
    public void OtherLinksButtonActionListenerTests1() {
        test.actionPerformed(mockAction);
        verify(mockPanel, times(1)).removeAll();
    }
}
