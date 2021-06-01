package RestfulAPI;

import FileAccess.*;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class APICall {

    public static ArrayList<JSONObject> search(String searchString, CallMaker callMaker) throws IOException {
        //create json body using given search string
        String searchJson = "{\"searchstring\" : \"" + searchString + "\"}";
        //Make a HTTP request to the the REST API search endpoint with generated json as body
        String response = callMaker.MakeCall("search", searchJson);

        return JsonSplit(response);
    }

    public static boolean login(String username, String password, CallMaker callMaker) throws IOException {
        //create json body using given login info and Make a HTTP request to the the REST API login endpoint with generated json
        String response = callMaker.MakeCall("login", "{\n" +
                "\"username\" : \"" + username + "\",\n" +
                "\"password\" : \"" + password + "\"\n" +
                "}");
        return response.equals("Authorised");
    }

    public static void DownloadFile(String id, String filename, CallMaker callMaker, FileAccess fileAccess, WriterBuilder writerBuilder, FileBuilder fileBuilder, FilesBuilder filesBuilder) throws IOException {
        //create json body using given file ID info
        String idJson = "{\"UID\" : \"" + id + "\"}";
        //Make a HTTP request to the the REST API downloadfile endpoint with generated json as body
        String response = callMaker.MakeCall("downloadfile", idJson);
        SessionPath sessionPath = new SessionPath();
        BufferedWriter writer = writerBuilder.BufferedWriterBuilder(fileAccess.GetFullPath(filename, sessionPath), true);
        //Generate new file with contents of response body
        fileAccess.WriteFile(filename, response, fileBuilder, filesBuilder, writer, sessionPath);
    }

    public static boolean UploadFile(File file, String filename, String username, String uploadDate, String groupname, CallMaker callMaker, FileAccess fileAccess) throws IOException {
        //read contents of given file and assign to a String variable
        ScannerBuilder scannerBuilder = new ScannerBuilder();
        String fileContent = fileAccess.readFile(file.getAbsolutePath(), scannerBuilder);
        //replace newline characters and backspaces with escaped values
        fileContent = fileContent.replace("\\", "\\\\");
        fileContent = fileContent.replaceAll("(\\r|\\n|\\r\\n)+", "\\\\n");

        //create json body using given local file info and file contents
        String json = "{\n" +
                "\"username\" : \"" + username + "\",\n" +
                "\"filename\" : \"" + filename + "\",\n" +
                "\"groupname\" : \"" + groupname.substring(7) + "\",\n" +
                "\"creationdate\" : \"" + uploadDate + "\",\n" +
                "\"fileContent\" : \"" + fileContent + "\"\n" +
                "}";
        //Make a HTTP request to the the REST API addfile endpoint with generated json as body
        String response = callMaker.MakeCall("addfile", json);
        return response.contains("Added file.");
    }


    public static ArrayList<JSONObject> JsonSplit(String jsonToSplit){
        //given a string of multiple json objects, split into an array list of JSONObject
        int start = 0;
        int end = 0;
        int i = 0;
        ArrayList<JSONObject> jsonArrayList = new ArrayList<>();
        for(String letter : jsonToSplit.split("")){
            if(letter.equals("{")){
                start = i;
            }
            else if(letter.equals("}")){
                end = i+1;
                jsonArrayList.add(new JSONObject(jsonToSplit.substring(start,end)));
            }
            i++;
        }
        return jsonArrayList;
    }
}
