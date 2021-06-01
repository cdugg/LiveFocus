package Testing;

import ActionListeners.OnlineButtonActionListener;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.mockito.Mockito.*;

public class OnlineButtonActionListenerTests {
    JPanel mockPanel;
    JFrame mockFrame;
    JTextField mockText;
    JPasswordField mockPass;
    JButton mockButton;
    JLabel mockLabel;
    ActionEvent mockAction;
    OnlineButtonActionListener test;

    @Before
    public void init() {
        mockPanel = mock(JPanel.class);
        mockFrame = mock(JFrame.class);
        mockText = mock(JTextField.class);
        mockPass = mock(JPasswordField.class);
        mockButton = mock(JButton.class);
        mockLabel = mock(JLabel.class);
        mockAction = mock(ActionEvent.class);
        test = new OnlineButtonActionListener(mockPanel, mockFrame, mockPanel, mockText, mockPass, mockButton, mockLabel, mockFrame);
    }

    @Test
    public void OnlineButtonActionListenerTests1() {
        test.actionPerformed(mockAction);
        verify(mockFrame, times(1)).setSize(anyInt(), anyInt());
        verify(mockFrame, times(1)).setVisible(true);
    }
}
