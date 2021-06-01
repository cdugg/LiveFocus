package FileAccess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SessionPath {
    public String getSessionPath() {
        //reads saveDirectory file and returns contents
        String directory = null;
        try {
            File file = new File("src/saveDirectory.txt");
            Scanner reader = new Scanner(file);
            directory = reader.nextLine();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return directory;
    }
}
