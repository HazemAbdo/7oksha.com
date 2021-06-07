package lib;
//import java.sql.SQLException;

import java.util.Map;

// This class takes the output of the stemmer and stores each word in the database with its docNum and index
// Threads of it are created in the main function
public class IndexerThread implements Runnable {
    // array of file paths
    theDataBase db;
    Map<Integer, Map<String, Integer>> DocTree;

    public IndexerThread(final Map<Integer, Map<String, Integer>> docTree) {
        DocTree = docTree;
        db = new theDataBase();
    }

    @Override
    public void run() {
        //key: integer(DocHash/DocID), value:Map<String,Integer> where String is the Word, Integer is it's termCount
        db.insertIndexed_DocsBucket(DocTree);
        db.CloseDB();
    }

}
