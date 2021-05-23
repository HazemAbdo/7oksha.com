package lib;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Stemmer implements Runnable{
    //array of file paths
    ArrayList<String> Docs;
    int start,end;
    public Stemmer(ArrayList<String>Docs,int start,int end){
        this.Docs = Docs;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = this.start; i < this.end; i++) {
            PorterStemmer st = new PorterStemmer();
            
            try {
                String temp  = st.stem(this.Docs.get(i));
                FileWriter ff = new FileWriter(this.Docs.get(i));
                ff.append(temp);
                ff.close();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
    }

    public static void main(String[] args) {
        ArrayList<String> files = new ArrayList<>();
        files.add("Files/in1.txt");
        Thread t1 = new Thread(new Stemmer(files,0,1));
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
    }
}
