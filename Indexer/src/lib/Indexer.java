package lib;

import snowball.EngStemmer;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Indexer {
    static theDataBase db=new theDataBase();
    public static void goIndexer() {
        long StartTime = System.currentTimeMillis();
        int NThreads = Math.min(Constants.DocHashes.length, Constants.NThreads);
        stemmer[] S = new stemmer[NThreads];
        for (int i = 0; i < NThreads; i++) {
            int start, end;
            start = Constants.DocHashes.length / NThreads * i;
            end = i == NThreads - 1 ? Constants.DocHashes.length : start + Constants.DocHashes.length / NThreads;
            S[i] = new stemmer(start, end);
        }
        ExecutorService tPool = Executors.newFixedThreadPool(Constants.NThreads);
        for (stemmer s : S) {
            tPool.execute(s);
        }
        tPool.shutdown();
        try {
            tPool.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println((System.currentTimeMillis() - StartTime) / 60000);
        long InsertionStartTime = System.currentTimeMillis();
        for (int i = 0; i < NThreads; i++) {
                db.insertIndexed_DocsBucket(S[i].GetDocTree());
        }
        /*ExecutorService tPool2 = Executors.newFixedThreadPool(Constants.NThreads/2);
        for (stemmer s : S) {
            tPool2.execute(new IndexerThread(s.GetDocTree()));
        }
        tPool2.shutdown();
        try {
            tPool2.awaitTermination(3, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }*/

        /*for (int j = 0; j < 2; j++) {
            Collection<Map<Integer, Map<String, Integer>>> DocsBucketsArray = new ArrayList<>(0);
            for (int i = j*NThreads / 2; i < (j+1)*NThreads/2; i++)
                DocsBucketsArray.add(S[i].GetDocTree());
            db.insertIndexed_DocsBucket_Array(DocsBucketsArray);
        }*/
        db.CloseDB();
        System.out.println((System.currentTimeMillis() - InsertionStartTime) / 60000);
    }
    public static void Index(){
        theDataBase db=new theDataBase();
        long StartTime=System.currentTimeMillis();

        for (String DocHash : Constants.DocHashes) {
            readFile inFile = new readFile(Constants.filesDir + DocHash + ".txt", true);
            try {
                var DocMap =  EngStemmer.Stem(inFile.text);//a map of (Word, TermCount)
                db.insertIndexedTree(Integer.parseInt(DocHash), DocMap);//inserts Word | DocHash | TermCount
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        db.CloseDB();
        /*ExecutorService tPool = Executors.newFixedThreadPool(Constants.NThreads);
        for (String file : Constants.DocHashes) {
            tPool.execute(new Stem_Index(Integer.parseInt(file)));
        }
        tPool.shutdown();
        try {
            tPool.awaitTermination(3,TimeUnit.HOURS);
        } catch (InterruptedException e) {
            System.out.println(e);
        }*/
    }
}