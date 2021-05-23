package lib;

import java.io.BufferedReader;
import java.io.FileReader;

public class ReadFile {
    String path;
    public String file;

    ReadFile(String path) {
        file = new String();
        try {
            String currentLine = null;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            while((currentLine = bufferedReader.readLine())!=null){
                file+=currentLine;
                file+='\n';
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args) {
        ReadFile file = new ReadFile("Files/in1.txt");
        System.out.println(file.file); 
    }
}
