package Testing;

import FileAccess.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessTests {
    BufferedWriter bufferedWriter;
    FilesBuilder filesBuilder;
    FileAccess fileAccess = new FileAccess();
    FileBuilder fileBuilder;
    SessionPath sessionPath;
    Runtime runtime;

    @Before
    public void init() throws IOException {
        bufferedWriter = Mockito.mock(BufferedWriter.class);
        filesBuilder = Mockito.mock(FilesBuilder.class);
        fileBuilder = Mockito.mock(FileBuilder.class);
        sessionPath = Mockito.mock(SessionPath.class);
        runtime = Mockito.mock(Runtime.class);


        Mockito.when(bufferedWriter.append(Mockito.any())).thenReturn(bufferedWriter);
        Mockito.when(sessionPath.getSessionPath()).thenReturn("directory");
    }

    @Test
    public void FileAccessTestsGenerateFileTest() throws IOException {
        List<Double> eogReadings = Arrays.asList(0.123, 1.234);
        List<Double> accReadings = Arrays.asList(0.123, 1.234);
        List<Double> eog2Readings = Arrays.asList(0.123, 1.234);
        ArrayList<String> dataTypes = new ArrayList<>();
        dataTypes.add("Frontal EEG");
        fileAccess.generateFile(bufferedWriter, filesBuilder, fileBuilder, "filefullname", "path", "group", "videopath", eogReadings, accReadings, eog2Readings, dataTypes);
        Mockito.verify(bufferedWriter, Mockito.times(23)).append(Mockito.any());
        Mockito.verify(bufferedWriter, Mockito.times(1)).close();

        Mockito.verify(filesBuilder, Mockito.times(1)).exists(Mockito.any());
        Mockito.verify(filesBuilder, Mockito.times(1)).createDirectory(Mockito.any());

        Mockito.verify(fileBuilder, Mockito.times(1)).exists(Mockito.eq("filefullname"));
        Mockito.verify(fileBuilder, Mockito.times(1)).createNewFile(Mockito.eq("filefullname"));
    }

    @Test
    public void FileAccessTestsGetFullPath(){
        Assert.assertEquals(fileAccess.GetFullPath("filename", sessionPath), "directory/filename");

        Mockito.verify(sessionPath, Mockito.times(1)).getSessionPath();
    }

    @Test
    public void FileAccessTestsWriteFileDirectoryExistsFileExists() throws IOException {
        Mockito.when(filesBuilder.exists(Mockito.any())).thenReturn(true);
        Mockito.when(fileBuilder.exists(Mockito.any())).thenReturn(true);
        fileAccess.WriteFile("filename", "contents", fileBuilder, filesBuilder, bufferedWriter, sessionPath);

        Mockito.verify(filesBuilder, Mockito.times(0)).createDirectory(Mockito.any());
        Mockito.verify(fileBuilder, Mockito.times(0)).createNewFile(Mockito.any());
    }

    @Test
    public void FileAccessTestsWriteFileDirectoryExistsFileNotExists() throws IOException {
        Mockito.when(filesBuilder.exists(Mockito.any())).thenReturn(true);
        Mockito.when(fileBuilder.exists(Mockito.any())).thenReturn(false);
        fileAccess.WriteFile("filename", "contents", fileBuilder, filesBuilder, bufferedWriter, sessionPath);

        Mockito.verify(filesBuilder, Mockito.times(0)).createDirectory(Mockito.any());
        Mockito.verify(fileBuilder, Mockito.times(1)).createNewFile(Mockito.any());
    }

    @Test
    public void FileAccessTestsWriteFileDirectoryNotExistsFileExists() throws IOException {
        Mockito.when(filesBuilder.exists(Mockito.any())).thenReturn(false);
        Mockito.when(fileBuilder.exists(Mockito.any())).thenReturn(true);
        fileAccess.WriteFile("filename", "contents", fileBuilder, filesBuilder, bufferedWriter, sessionPath);

        Mockito.verify(filesBuilder, Mockito.times(1)).createDirectory(Mockito.any());
        Mockito.verify(fileBuilder, Mockito.times(0)).createNewFile(Mockito.any());
    }

    @Test
    public void FileAccessTestsWriteFileDirectoryNotExistsFileNotExists() throws IOException {
        Mockito.when(filesBuilder.exists(Mockito.any())).thenReturn(false);
        Mockito.when(fileBuilder.exists(Mockito.any())).thenReturn(false);
        fileAccess.WriteFile("filename", "contents", fileBuilder, filesBuilder, bufferedWriter, sessionPath);

        Mockito.verify(filesBuilder, Mockito.times(1)).createDirectory(Mockito.any());
        Mockito.verify(fileBuilder, Mockito.times(1)).createNewFile(Mockito.any());
    }

    @Test
    public void FileAccessTestsWriteFileWriterTests() throws IOException {
        Mockito.when(filesBuilder.exists(Mockito.any())).thenReturn(true);
        Mockito.when(fileBuilder.exists(Mockito.any())).thenReturn(true);
        fileAccess.WriteFile("filename", "contents", fileBuilder, filesBuilder, bufferedWriter, sessionPath);
        Mockito.verify(bufferedWriter, Mockito.times(1)).append(Mockito.anyString());
        Mockito.verify(bufferedWriter, Mockito.times(1)).close();

    }

    @Test
    public void FileAccessTestsCutVideo() throws IOException {
        fileAccess.cutVideo("start", "end", "path", "newpath", runtime);
        Mockito.verify(runtime, Mockito.times(1)).exec("ffmpeg -ss start -i path -c copy -t end newpath\\clippedvideo.mp4 -y");
    }
}
