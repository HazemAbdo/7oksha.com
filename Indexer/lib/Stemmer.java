package lib;

import java.util.ArrayList;

// This class uses PorterStemmer class to stem whole files
// TODO implement stem(String word)
// TODO take html files content as input and stem them instead of ReadFile in PorterStemmer
// TODO read files from Crawler Directory instead of Files/ I don't know how
// TODO identify each word whether it's h1, h2, h3, p, div, etc. and put its priority according to this (Sooo hard)
public class Stemmer implements Runnable {
    // array of file paths
    ArrayList<String> Docs;
    int start, end;

    public Stemmer(ArrayList<String> Docs, int start, int end) {
        this.Docs = Docs;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = this.start; i < this.end; i++) {
            PorterStemmer st = new PorterStemmer();
            // take file dir and stem it
            String temp = st.stem(Constants.filesDir + this.Docs.get(i));
            // output stemmed result
            new OutputFile(Constants.stemmedDir + this.Docs.get(i), temp);
        }
    }

    public static void main(String[] args) {
        ArrayList<String> files = new ArrayList<>();
        files.add("Files/in3.txt");
        Thread t1 = new Thread(new Stemmer(files, 0, 1));
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
    }
}
