package Testing;

import ActionListeners.SessionsViewSelectedFilesButtonActionListener;
import GUI2.RecordingFile;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class SessionsViewSelectedFilesButtonActionListenerTests {
    JPanel mockPanel;
    JFrame mockFrame;
    List<RecordingFile> mockRecordings = new ArrayList<RecordingFile>();
    RecordingFile mockFile;
    JPanel mockPanel2;
    JPanel samplePanel = new JPanel();
    JCheckBox testCheck = new JCheckBox();
    ActionEvent mockAction;
    SessionsViewSelectedFilesButtonActionListener test;

    @Before
    public void init() {
        mockPanel = mock(JPanel.class);
        mockFrame = mock(JFrame.class);
        mockFile = mock(RecordingFile.class);
        mockRecordings.add(mockFile);
        mockRecordings.add(mockFile);
        mockPanel2 = mock(JPanel.class);
        testCheck.setSelected(true);
        samplePanel.add(new JLabel(""));
        samplePanel.add(testCheck);
        mockAction = mock(ActionEvent.class);

        test = new SessionsViewSelectedFilesButtonActionListener(mockPanel, mockFrame, mockRecordings, mockPanel2);
    }

    @Test
    public void SessionsViewSelectedFilesButtonActionListenerTests1() {
        when(mockPanel2.getComponentCount()).thenReturn(samplePanel.getComponentCount());
        when(mockPanel2.getComponents()).thenReturn(samplePanel.getComponents());
        test.actionPerformed(mockAction);
        verify(mockPanel, times(1)).removeAll();
        verify(mockFrame, times(1)).getHeight();
        verify(mockFrame, times(1)).getWidth();
    }
}
