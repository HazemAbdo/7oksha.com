package lib;

import snowball.EngStemmer;

public class Stem_Index implements Runnable {
    theDataBase db;
    Integer DocHash;

    public Stem_Index(Integer filehash)/*ArrayList<Integer> docs, int start, int end,int Number)*/ {
        db = new theDataBase();
        DocHash = filehash;
    }

    @Override
    public void run() {
        readFile inFile = new readFile(Constants.filesDir + DocHash + ".txt", true);
        try {
            var DocMap = EngStemmer.Stem(inFile.text);//a map of (Word, TermCount)
            db.insertIndexedTree(DocHash, DocMap);//inserts Word | DocHash | TermCount
        } catch (Throwable e) {
            e.printStackTrace();
        }
        db.CloseDB();
    }
}
