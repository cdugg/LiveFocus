package Testing;

import DeviceAPI.IORunnerSwingBuilder;
import DeviceAPI.MuseIORunner;
import DeviceAPI.MusePlayerRunner;
import DeviceAPI.PlayerRunnerSwingBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.mockito.Mockito;

import javax.swing.*;
import java.io.IOException;

public class MusePlayerRunnerTests {
    PlayerRunnerSwingBuilder playerRunnerSwingBuilder;
    SwingWorker swingWorker;

    @Before
    public void init(){
        playerRunnerSwingBuilder = Mockito.mock(PlayerRunnerSwingBuilder.class);
        swingWorker = Mockito.mock(SwingWorker.class);

        Mockito.when(playerRunnerSwingBuilder.SwingWorkerBuilder(Mockito.anyString())).thenReturn(swingWorker);
    }
    @Test
    public void MusePlayerRunnerTestSwingWorkerBuilder(){
        MusePlayerRunner musePlayerRunner = new MusePlayerRunner("testfile", "testgroup", "testVideoPath", playerRunnerSwingBuilder);

        Mockito.verify(playerRunnerSwingBuilder, Mockito.times(1)).SwingWorkerBuilder(Mockito.anyString());
    }

    @Test
    public void MusePlayerRunnerTestStartRecording(){
        MusePlayerRunner musePlayerRunner = new MusePlayerRunner("testfile", "testgroup", "testVideoPath", playerRunnerSwingBuilder);
        musePlayerRunner.StartRecording();

        Mockito.verify(playerRunnerSwingBuilder, Mockito.times(1)).setCaptureData(true);
    }

    @Test
    public void MusePlayerRunnerTestPauseRecording(){
        MusePlayerRunner musePlayerRunner = new MusePlayerRunner("testfile", "testgroup", "testVideoPath", playerRunnerSwingBuilder);
        musePlayerRunner.PauseRecording();

        Mockito.verify(playerRunnerSwingBuilder, Mockito.times(1)).setCaptureData(false);
    }

    @Test
    public void MusePlayerRunnerTestEndRecording() throws IOException{
        MusePlayerRunner musePlayerRunner = new MusePlayerRunner("testfile", "testgroup", "testVideoPath", playerRunnerSwingBuilder);
        musePlayerRunner.EndRecording();

        Mockito.verify(playerRunnerSwingBuilder, Mockito.times(1)).setCaptureData(false);
        Mockito.verify(playerRunnerSwingBuilder, Mockito.times(1)).EndRecording(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString());
    }
}
