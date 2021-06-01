package ActionListeners;

import DeviceAPI.MuseIORunner;
import DeviceAPI.MusePlayerRunner;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RecordingConnectDeviceButtonActionListener implements ActionListener {
    MuseIORunner museIORunner;
    MusePlayerRunner musePlayerRunner;
    JButton connectDeviceButton;

    public RecordingConnectDeviceButtonActionListener(MuseIORunner io, MusePlayerRunner player, JButton button) {
        museIORunner = io;
        musePlayerRunner = player;
        connectDeviceButton = button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //wait for the muse to pair with the device and update this button based on its current status
        if (!MuseIORunner.connected) {
            try {
                museIORunner.MuseIORun();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            musePlayerRunner.MusePlayerRun();
        } else {
            museIORunner.MuseIOCancel(connectDeviceButton);
            try {
                musePlayerRunner.MusePlayerCancel();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
