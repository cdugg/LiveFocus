package DeviceAPI;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IORunnerSwingBuilder {
    JButton connectDeviceButton;
    public IORunnerSwingBuilder(JButton connectDeviceButton){
        //Initialises class with connect button as class variable to allow for changes in button text to display state of connection with device
        this.connectDeviceButton = connectDeviceButton;
    }

    public SwingWorker SwingBuilder(Process process){
        //Build the SwingWorker that runs the process for outputting data from the device.
        SwingWorker<Void, Void> Swing = new SwingWorker<Void, Void>(){
            @Override
            protected Void doInBackground() throws Exception {
                BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
                try {
                    //while connecting, change connect nutton to display "Connecting..."
                    connectDeviceButton.setText("Connecting...");
                    while (input.readLine() != null) {
                        if(input.readLine().contains("Output")){
                            //Once connected, change connect button to display "disconnect"
                            MuseIORunner.connected = true;
                            connectDeviceButton.setText("Disconnect");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        return Swing;
    }

    public Process ProcessBuilder(String command, Runtime runtime) throws IOException {
        //Builds and returns the process that is ran by the SwingWorker builder
        Process process;
        System.out.println(command);
        process = runtime.exec(command);
        return process;
    }
}
