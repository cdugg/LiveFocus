package FileAccess;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilesBuilder {
    //Emulates Files class functionality in an instantiatable class for dependency injection
    public boolean exists(Path path){
        return Files.exists(path);
    }

    public void createDirectory(Path path) throws IOException {
        Files.createDirectory(path);
    }
}
