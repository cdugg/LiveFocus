package Testing;

import ActionListeners.OnlineSearchButtonActionListener;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

//can only be tested properly if api responds
public class OnlineSearchButtonActionListenerTests {
    ArrayList<JSONObject> mockFiles = new ArrayList<>();
    JScrollPane mockScroll;
    JPanel mockPanel;
    JTextField mockText;
    ActionEvent mockAction;
    OnlineSearchButtonActionListener test;

    @Before
    public void init() {
        mockScroll = mock(JScrollPane.class);
        mockPanel = mock(JPanel.class);
        mockText = mock(JTextField.class);
        mockAction = mock(ActionEvent.class);
        test = new OnlineSearchButtonActionListener(mockFiles, mockScroll, mockPanel, mockText);
    }

    @Test
    public void APIResponding() {
        test.actionPerformed(mockAction);
        verify(mockScroll, times(1)).setViewportView(anyObject());
        verify(mockPanel, times(1)).updateUI();
    }

    @Test
    public void APINotResponding() {
        test.actionPerformed(mockAction);
    }
}
