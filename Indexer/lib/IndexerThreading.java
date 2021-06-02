package lib;

// TODO should you use one db and syncronize or use several db's one for each thread?
// TODO Both [one db without syncronize] and [several db's one for each thread] seem to be working
import java.util.ArrayList;

public class IndexerThreading {
    // * if #files< #threads, #threads is modified to be = #files (each thread works on a file in this case)
    static int NThreads = Constants.NThreads;

    public static void goIndexer() {
        // getting files names from database
        theDataBase db = new theDataBase();
        ArrayList<String> files = db.getFileNames();
        // initializing threads
        if (files.size() < NThreads) {
            NThreads = files.size();
        }
        ArrayList<Thread> T = new ArrayList<>();

        // Stemmer and indexer
        String[] modes = { "Stemmer", "Indexer" };
        for (String mode : modes) {
            // make a new array of threads
            T = new ArrayList<>();

            // create threads of stemmers THEN indexers
            for (int i = 0; i < NThreads; i++) {
                int start, end;
                start = files.size() / NThreads * i;
                end = start + files.size() / NThreads;
                if (i == NThreads - 1) {
                    end = files.size();
                }

                // First loop, Stemmer
                if (mode == "Stemmer") {
                    T.add(new Thread(new Stemmer(files, start, end)));
                }

                // Then second loop:, Indexer
                else if (mode == "Indexer") {
                    // ? should we delete this line and / or use syncronize()?
                    db = new theDataBase();
                    T.add(new Thread(new Indexer(files, start, end, db)));
                }
            }

            // Start threads
            for (int i = 0; i < T.size(); i++) {
                T.get(i).start();
            }

            // join threads
            try {
                for (int i = 0; i < T.size(); i++) {
                    T.get(i).join();
                }
            } catch (InterruptedException e) {
                System.out.println(e.toString());
            }
        }
    }
}
