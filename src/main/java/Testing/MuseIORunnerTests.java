package Testing;

import DeviceAPI.IORunnerSwingBuilder;
import DeviceAPI.MuseIORunner;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.swing.*;
import java.io.IOException;

public class MuseIORunnerTests {
    JButton jButton;
    IORunnerSwingBuilder ioRunnerSwingBuilder;
    Process process;
    SwingWorker swingWorker;

    @Before
    public void init() throws IOException {
        jButton = Mockito.mock(JButton.class);
        ioRunnerSwingBuilder = Mockito.mock(IORunnerSwingBuilder.class);
        process = Mockito.mock(Process.class);
        swingWorker = Mockito.mock(SwingWorker.class);

        Mockito.when(ioRunnerSwingBuilder.ProcessBuilder(Mockito.anyString(), Mockito.any(Runtime.class))).thenReturn(process);
        Mockito.when(ioRunnerSwingBuilder.SwingBuilder(process)).thenReturn(swingWorker);
    }
    @Test
    public void MuseIORunnerTestMuseIORun() throws IOException, InterruptedException {

        MuseIORunner museIORunner = new MuseIORunner(ioRunnerSwingBuilder);
        museIORunner.MuseIORun();

        Mockito.verify(ioRunnerSwingBuilder, Mockito.times(1)).ProcessBuilder(Mockito.anyString(), Mockito.any());
        Mockito.verify(ioRunnerSwingBuilder, Mockito.times(1)).SwingBuilder(Mockito.any(Process.class));

    }
}
