package Testing;

import ActionListeners.RecordingConnectDeviceButtonActionListener;
import DeviceAPI.IORunnerSwingBuilder;
import DeviceAPI.MuseIORunner;
import DeviceAPI.MusePlayerRunner;
import DeviceAPI.PlayerRunnerSwingBuilder;
import FileAccess.*;
import RestfulAPI.APICall;
import RestfulAPI.CallMaker;
import org.junit.Test;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.IOException;

public class IntegationTest {
    //download file -> write file
    //player cancel -> write file
    @Test
    public void IntegrationTestDownloadFile() throws IOException {
        CallMaker callMaker = Mockito.mock(CallMaker.class);
        WriterBuilder writerBuilder = Mockito.mock(WriterBuilder.class);
        FilesBuilder filesBuilder = Mockito.mock(FilesBuilder.class);
        FileBuilder fileBuilder = Mockito.mock(FileBuilder.class);
        BufferedWriter bufferedWriter = Mockito.mock(BufferedWriter.class);

        Mockito.when(callMaker.MakeCall(Mockito.anyString(), Mockito.any())).thenReturn("response");
        Mockito.when(writerBuilder.BufferedWriterBuilder(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(bufferedWriter);

        FileAccess fileAccess = new FileAccess();

        APICall.DownloadFile("0000", "filename", callMaker, fileAccess, writerBuilder, fileBuilder, filesBuilder);

        Mockito.verify(filesBuilder, Mockito.times(1)).exists(Mockito.any());
        Mockito.verify(fileBuilder, Mockito.times(1)).exists(Mockito.any());
        Mockito.verify(bufferedWriter, Mockito.times(1)).close();
    }

    @Test
    public void IntegrationTestConnectDevice(){
        PlayerRunnerSwingBuilder playerRunnerSwingBuilder = Mockito.mock(PlayerRunnerSwingBuilder.class);
        IORunnerSwingBuilder ioRunnerSwingBuilder = Mockito.mock(IORunnerSwingBuilder.class);
        JButton jButton = Mockito.mock(JButton.class);
        ActionEvent actionEvent = Mockito.mock(ActionEvent.class);
        SwingWorker swingWorker = Mockito.mock(SwingWorker.class);

        Mockito.when(ioRunnerSwingBuilder.SwingBuilder(Mockito.any())).thenReturn(swingWorker);
        Mockito.when(playerRunnerSwingBuilder.SwingWorkerBuilder(Mockito.any())).thenReturn(swingWorker);

        MusePlayerRunner musePlayerRunner = new MusePlayerRunner("filename", "group", "videopath", playerRunnerSwingBuilder);
        MuseIORunner museIORunner = new MuseIORunner(ioRunnerSwingBuilder);
        RecordingConnectDeviceButtonActionListener actionListener = new RecordingConnectDeviceButtonActionListener(museIORunner, musePlayerRunner, jButton);
        actionListener.actionPerformed(actionEvent);

        Mockito.verify(playerRunnerSwingBuilder, Mockito.times(1)).SwingWorkerBuilder(Mockito.anyString());
    }
}
