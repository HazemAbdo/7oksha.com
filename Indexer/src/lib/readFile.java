package lib;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.FileReader;

// reads file and stores it in a string member
public class readFile {
    String path;
    // the whole file is stored here
    public String text;
    //Reads the stemmed files
    public readFile(String path) {
        text = new String();
        try {
            String currentLine = null;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            while((currentLine = bufferedReader.readLine())!=null){
                text+=currentLine;
                text+='\n';
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("path "+path+ " not found");
        }
    }
    // read any html text inside tags
    // ! use this in the stemmer
    // ! I don't know how to make priorities using tags
    public readFile(String path, boolean html){// # https://jsoup.org/cookbook/extracting-data/attributes-text-html
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
            text = doc.body().text();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /*public static void main(String[] args) {
        // ReadFile file = new ReadFile("Files/in1.txt");
        // System.out.println(file.file); 
        ReadFile file = new ReadFile(Constants.filesDir+"in5.html",true);
        System.out.println(file.file);
    }*/
}
