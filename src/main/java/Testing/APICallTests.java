package Testing;

import DeviceAPI.IORunnerSwingBuilder;
import DeviceAPI.MuseIORunner;
import FileAccess.*;
import RestfulAPI.APICall;
import RestfulAPI.CallMaker;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class APICallTests {
    CallMaker callMaker;
    FileAccess fileAccess;
    WriterBuilder writerBuilder;
    FilesBuilder filesBuilder;
    FileBuilder fileBuilder;

    @Before
    public void init() throws IOException {
        callMaker = Mockito.mock(CallMaker.class);
        fileAccess = Mockito.mock(FileAccess.class);
        writerBuilder = Mockito.mock(WriterBuilder.class);
        fileBuilder = Mockito.mock(FileBuilder.class);
        filesBuilder = Mockito.mock(FilesBuilder.class);

        Mockito.when(callMaker.MakeCall(Mockito.any(), Mockito.any())).thenReturn("Authorised");
        Mockito.when(fileAccess.readFile(Mockito.anyString(), Mockito.any())).thenReturn("response");
        Mockito.when(fileAccess.GetFullPath(Mockito.anyString(), Mockito.any())).thenReturn("response");
    }
    @Test
    public void APICallTestsSearchTest() throws IOException {
        APICall.search("search", callMaker);
        Mockito.verify(callMaker, Mockito.times(1)).MakeCall(Mockito.eq("search"), Mockito.anyString());
    }
    @Test
    public void APICallTestsLoginTest() throws IOException {
        Assert.assertTrue(APICall.login("username", "password", callMaker));
        Mockito.verify(callMaker, Mockito.times(1)).MakeCall(Mockito.eq("login"), Mockito.anyString());
    }
    @Test
    public void APICallTestsDownloadFileTest() throws IOException {
        APICall.DownloadFile("id", "filename", callMaker, fileAccess, writerBuilder, fileBuilder, filesBuilder);
        Mockito.verify(callMaker, Mockito.times(1)).MakeCall(Mockito.eq("downloadfile"), Mockito.anyString());
    }
    @Test
    public void APICallTestsUploadFileTest() throws IOException {
        File file = new File("name");
        APICall.UploadFile(file, "filename", "username", "date", "group", callMaker, fileAccess);
        Mockito.verify(callMaker, Mockito.times(1)).MakeCall(Mockito.eq("addfile"), Mockito.anyString());
    }
    @Test
    public void APICallTestsJsonSplit(){
        String jsonToSplit = "{\"test\" : \"test\"}{\"test2\" : \"test2\"}";
        ArrayList<JSONObject> resultArray = APICall.JsonSplit(jsonToSplit);

        Assert.assertTrue(resultArray.get(0).toString().equals("{\"test\":\"test\"}"));
        Assert.assertTrue(resultArray.get(1).toString().equals("{\"test2\":\"test2\"}"));

    }
    @Test
    public void APICallTestsJsonSplitEmpty(){
        String jsonToSplit = "";

        ArrayList<JSONObject> resultArray = APICall.JsonSplit(jsonToSplit);
        Assert.assertEquals(resultArray.size(), 0);

    }
}
