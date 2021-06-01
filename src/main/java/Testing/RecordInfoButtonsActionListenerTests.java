package Testing;

import ActionListeners.RecordInfoCancelButtonActionListener;
import ActionListeners.RecordInfoOkButtonActionListener;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.mockito.Mockito.*;

public class RecordInfoButtonsActionListenerTests {
    JTextField mockText;
    JCheckBox mockCheck;
    JFrame mockFrame1;
    JFrame mockFrame2;
    JButton mockButton;
    ActionEvent mockAction;
    RecordInfoOkButtonActionListener test1;
    RecordInfoCancelButtonActionListener test2;

    @Before
    public void init() {
        mockText = mock(JTextField.class);
        mockCheck = mock(JCheckBox.class);
        mockFrame1 = mock(JFrame.class);
        mockFrame2 = mock(JFrame.class);
        mockButton = mock(JButton.class);
        mockAction = mock(ActionEvent.class);
        test1 = new RecordInfoOkButtonActionListener(mockText, mockText, mockCheck, mockCheck, mockCheck, mockFrame1, mockFrame2, mockButton, mockText);
        test2 = new RecordInfoCancelButtonActionListener(mockFrame1);
    }

    @Test
    public void RecordInfoOkButtonActionListenerTests1() {
        test1.actionPerformed(mockAction);
        verify(mockFrame2, times(1)).dispose();
    }

    @Test
    public void RecordInfoCancelButtonActionListenerTests1() {
        test2.actionPerformed(mockAction);
        verify(mockFrame1, times(1)).dispose();
    }
}
