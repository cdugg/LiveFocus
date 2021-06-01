package DeviceAPI;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



public class MuseIORunner {
    private Process process;
    private SwingWorker swing;
    public static Boolean connected = false;
    private final IORunnerSwingBuilder ioRunnerSwingBuilder;
    //command to be run to start muse-io program
    private final String command = "C:\\Program Files (x86)\\Muse\\muse-io --preset 14 --device Muse-13C9 --osc osc.tcp://localhost:5000";
    public MuseIORunner(IORunnerSwingBuilder ioRunnerSwingBuilder){
        //dependency injection on initialisation
        this.ioRunnerSwingBuilder = ioRunnerSwingBuilder;

    }

    public void MuseIORun() throws IOException {
        //on run, initialise method in SwingBuilder to run the process for sending data from Muse API
        Runtime runtime = Runtime.getRuntime();
        process = ioRunnerSwingBuilder.ProcessBuilder(command, runtime);
        swing = ioRunnerSwingBuilder.SwingBuilder(process);
        swing.execute();
    }

    public void MuseIOCancel(JButton connectDeviceButton) {
        //Ends process and kills it
        process.destroy();
        swing.cancel(true);
        connectDeviceButton.setText("Connect Device");
        connected = false;
    }

}
