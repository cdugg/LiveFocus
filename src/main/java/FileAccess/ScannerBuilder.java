package FileAccess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ScannerBuilder {
    public Scanner BuildScanner(File file) throws FileNotFoundException {
        //Return a Scanner class for dependency injection
        Scanner scanner = new Scanner(file);
        return scanner;
    }
}
