package lib;

import java.io.FileWriter;
import java.io.IOException;

public class OutputFile {
    OutputFile(String path, String data) {
        FileWriter fw;
        try {
            fw = new FileWriter(path);
            fw.append(data);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        new OutputFile("Files/testingFile.txt", "This is a text to write to some file");
    }
}
