package Testing;

import ActionListeners.OnlineEntryFormActionListener;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.mockito.Mockito.*;

//can only be tested properly if api responds
public class OnlineEntryFormActionListenerTests {
    JPanel mockPanel;
    JFrame mockFrame;
    JTextField mockText;
    JPasswordField mockPass;
    JLabel mockLabel;
    JPanel mockPanel2;
    JFrame mockFrame2;
    ActionEvent mockAction;
    OnlineEntryFormActionListener test;

    @Before
    public void init() {
        mockPanel = mock(JPanel.class);
        mockPanel2 = mock(JPanel.class);
        mockFrame = mock(JFrame.class);
        mockFrame2 = mock(JFrame.class);
        mockText = mock(JTextField.class);
        mockPass = mock(JPasswordField.class);
        mockLabel = mock(JLabel.class);
        mockAction = mock(ActionEvent.class);
        test = new OnlineEntryFormActionListener(mockPanel, mockFrame, mockText, mockPass, mockLabel, mockFrame2, mockPanel2);
    }

    @Test
    public void APIResponding() {
        test.actionPerformed(mockAction);
        //If api does respond
        verify(mockFrame2, times(1)).dispose();
    }

    @Test
    public void APINotResponding() {
        test.actionPerformed(mockAction);
        //If api doesn't respond should run a setVisible
        verify(mockLabel, times(1)).setVisible(true);
    }
}
