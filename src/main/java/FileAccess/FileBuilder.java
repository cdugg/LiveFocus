package FileAccess;

import java.io.File;
import java.io.IOException;

public class FileBuilder {
    //Emulates File class functionality in an instantiatable class for dependency injection
    public boolean createNewFile(String file) throws IOException {
        File newFile = new File(file);
        return newFile.createNewFile();
    }

    public boolean exists(String file){
        File newFile = new File(file);
        return newFile.exists();
    }
}
