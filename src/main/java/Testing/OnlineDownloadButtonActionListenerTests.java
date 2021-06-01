package Testing;

import ActionListeners.OnlineDownloadButtonActionListener;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.awt.event.ActionEvent;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class OnlineDownloadButtonActionListenerTests {
    JSONObject mockObject;
    ActionEvent mockAction;
    OnlineDownloadButtonActionListener test;

    @Before
    public void init() {
        mockObject = mock(JSONObject.class);
        mockAction = mock(ActionEvent.class);
        test = new OnlineDownloadButtonActionListener(mockObject);
    }

    @Test
    public void APIResponding() {
        when(mockObject.get(anyString())).thenReturn("");
        test.actionPerformed(mockAction);
        verify(mockObject, times(2)).get(anyString());
    }
}
