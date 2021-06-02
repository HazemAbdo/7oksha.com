package lib;

import java.util.ArrayList;

// This class takes the output of the stemmer and stores each word in the database with its docNum and index
// Threads of it are created in the main function
public class Indexer implements Runnable {
    // array of file paths
    ArrayList<String> Docs;
    theDataBase db;
    int start, end;

    public Indexer(ArrayList<String> Docs, int start, int end, theDataBase db) {
        this.Docs = Docs;
        this.start = start;
        this.end = end;
        this.db = db;
    }

    // TODO| identify each word whether it's h1, h2, h3, p, div, etc. and put its
    // TODO| priority according to this (Sooo hard)
    @Override
    public void run() {
        for (int i = this.start; i < this.end; i++) {
            // read the output of the stemmer
            ReadFile rFile = new ReadFile(Constants.stemmedDir + this.Docs.get(i));

            // array of lines
            String[] lines = rFile.file.split("\n");
            ArrayList<String> fileTerms = new ArrayList<>();

            // array of words
            for (int j = 0; j < lines.length; j++) {
                String[] terms = lines[j].split(" ");
                for (int k = 0; k < terms.length; k++) {
                    String ok = FilterString.termOk(terms[k]);
                    if (ok != "") {
                        fileTerms.add(ok);
                    }
                }
            }

            // insert array of words in the database
            db.insertIndexedFile(fileTerms, i, 0);
        }
    }

    public static void main(String[] args) {
        ArrayList<String> files = new ArrayList<>();
        files.add("Files/in2.txt");
        theDataBase db = new theDataBase();
        Thread t1 = new Thread(new Indexer(files, 0, 1, db));
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
    }
}
