package lib;

import java.io.BufferedReader;
import java.io.FileReader;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;

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
            file = doc.body().text();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        // ReadFile file = new ReadFile("Files/in1.txt");
        // System.out.println(file.file); 
        ReadFile file = new ReadFile("Files/Extract.html",true);
        System.out.println(file.file);
    }
}
