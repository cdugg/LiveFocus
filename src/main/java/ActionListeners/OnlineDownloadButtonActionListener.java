package ActionListeners;

import FileAccess.*;
import RestfulAPI.APICall;
import RestfulAPI.CallMaker;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class OnlineDownloadButtonActionListener implements ActionListener {
    JSONObject item;
    CallMaker callMaker = new CallMaker();
    FileAccess fileAccess = new FileAccess();
    WriterBuilder writerBuilder = new WriterBuilder();
    FileBuilder fileBuilder = new FileBuilder();
    FilesBuilder filesBuilder = new FilesBuilder();

    public OnlineDownloadButtonActionListener(JSONObject i) {
        item = i;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //request a file to download from server
        try {
            APICall.DownloadFile((String) item.get("UID"), (String) item.get("filename"), callMaker, fileAccess, writerBuilder, fileBuilder, filesBuilder);
            JOptionPane.showMessageDialog(null, "File successfully downloaded.");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
