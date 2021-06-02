package src;

import java.util.ArrayList;
import lib.*;

public class Main {
    // * if #files< #threads, #threads is modified to be = #files (each thread works
    // on a file in this case)
    static int NThreads = Constants.NThreads;

    // TODO seperate this function's logic in a serpate class and file
    // TODO should you use one db and syncronize or use several db's one for each thread?
    // TODO Both [one db without syncronize] and [several db's one for each thread] seem to be working
    public static void main(String[] args) {
        theDataBase db = new theDataBase();
        ArrayList<String> files = db.getFileNames();
        if (files.size() < NThreads) {
            NThreads = files.size();
        }
        ArrayList<Thread> T = new ArrayList<>();

        // Stemmer
        System.out.println("Stemmer");
        for (int i = 0; i < NThreads; i++) {
            int start, end;
            start = files.size() / NThreads * i;
            end = start + files.size() / NThreads;
            if (i == NThreads - 1) {
                end = files.size();
            }
            System.out.print(start + " ");
            System.out.println(end);
            T.add(new Thread(new Stemmer(files, start, end)));
        }
        for (int i = 0; i < T.size(); i++) {
            T.get(i).start();
        }
        try {
            for (int i = 0; i < T.size(); i++) {
                T.get(i).join();
            }
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
        T = new ArrayList<>();
        // Indexer
        System.out.println("Indexer");
        for (int i = 0; i < NThreads; i++) {
            int start, end;
            start = files.size() / NThreads * i;
            end = start + files.size() / NThreads;
            if (i == NThreads - 1) {
                end = files.size();
            }
            // ? should we delete this line and / or use syncronize()?
            db = new theDataBase();

            System.out.print(start + " ");
            System.out.println(end);
            T.add(new Thread(new Indexer(files, start, end, db)));
        }
        for (int i = 0; i < T.size(); i++) {
            T.get(i).start();
        }
        try {
            for (int i = 0; i < T.size(); i++) {
                T.get(i).join();
            }
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
    }
}