package src;

import java.util.ArrayList;
import lib.*;

class Main {
    public static void main(String[] args) {
        theDataBase db = new theDataBase();
        ArrayList<String> files = db.getFileNames();
        Thread t1 = new Thread(new Stemmer(files, 0, 3));
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
        // t1 = new Thread(new Indexer(files, 0, 3, db));
        // t1.start();
        // try {
        //     t1.join();
        // } catch (InterruptedException e) {
        //     System.out.println(e.toString());
        // }
    }
}