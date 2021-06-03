package lib;

import java.io.BufferedReader;
import java.io.FileReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
// reads file and stores it in a string member
public class ReadFile {
    String path;
    // the whole file is stored here
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
    // read any html text inside tags
    // ! use this in the stemmer
    // ! I don't know how to make priorities using tags
    ReadFile(String path,boolean html){// # https://jsoup.org/cookbook/extracting-data/attributes-text-html
        String temp = new String();
        try {
            String currentLine = null;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            while((currentLine = bufferedReader.readLine())!=null){
                temp+=currentLine;
                temp+='\n';
            }
            bufferedReader.close();
            Document doc = Jsoup.parse(temp);
            //gives file content without tags
            file = doc.body().text();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        // ReadFile file = new ReadFile("Files/in1.txt");
        // System.out.println(file.file); 
        ReadFile file = new ReadFile(Constants.filesDir+"in5.html",true);
        System.out.println(file.file);
    }
}
