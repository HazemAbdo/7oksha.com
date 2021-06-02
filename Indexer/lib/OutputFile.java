package lib;

import java.io.FileWriter;
import java.io.IOException;

// This class takes path to file with its name, and also takes data String to be put in that file
// Just to make repeated tasks easier
// use new OutputFile(path,data)
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
