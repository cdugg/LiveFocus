package Testing;

import ActionListeners.RecordingConnectDeviceButtonActionListener;
import DeviceAPI.MuseIORunner;
import DeviceAPI.MusePlayerRunner;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class RecordingConnectDeviceButtonActionListenerTests {
    MuseIORunner mockRunner;
    MusePlayerRunner mockPlayer;
    JButton mockButton;
    ActionEvent mockAction;
    RecordingConnectDeviceButtonActionListener test;

    @Before
    public void init() {
        mockRunner = mock(MuseIORunner.class);
        mockPlayer = mock(MusePlayerRunner.class);
        mockButton = mock(JButton.class);
        mockAction = mock(ActionEvent.class);
        test = new RecordingConnectDeviceButtonActionListener(mockRunner, mockPlayer, mockButton);
    }

    @Test
    public void RecordingConnectDeviceButtonActionListenerTests1() throws IOException {
        test.actionPerformed(mockAction);
        verify(mockRunner, times(1)).MuseIORun();
    }
}
