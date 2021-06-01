package lib;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


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

    @Override
    public void run() {
        for (int i = this.start; i < this.end; i++) {
            // int indx = 0;
            ReadFile rFile = new ReadFile(this.Docs.get(i));
            String[] lines = rFile.file.split("\n");
            ArrayList<String> fileTerms = new ArrayList<>();
            for (int j = 0; j < lines.length; j++) {
                String[] terms = lines[j].split(" ");
                for (int k = 0; k < terms.length; k++) {
                    String ok = termOk(terms[k]);
                    if (ok != "") {
                        fileTerms.add(ok);
                    }
                    // db.insertWord(terms[k], i, indx++, 0);
                }
            }
            db.insertIndexedFile(fileTerms, i, 0);
        }
    }

    private String termOk(String term) {
        String finalTerm = new String(term);
        if (StopWord.isStopWord(finalTerm)) {
            return "";
        }
        String[] removedStrings = { "\\)", "\\(", "\\{", "\\}", ",", "\\]", "\\[", "\"", "%", "\\$", "#", "@", "!",
                "\\-", "_", "\\?", ">", "<", "'" };
        for (int i = 0; i < removedStrings.length; i++) {
            if (finalTerm.contains(removedStrings[i])) {
                finalTerm = finalTerm.replaceAll(removedStrings[i], "");
            }
        }
        return finalTerm;
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
