package FileAccess;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import FileAccess.*;

public class WriterBuilder {
    public BufferedWriter BufferedWriterBuilder(String path, boolean append) throws IOException {
        //Return a BufferedWriter class for dependency injection
        FileAccess fileAccess = new FileAccess();
        return new BufferedWriter(new FileWriter(path, append));
    }
}
