package  lib;

import snowball.EngStemmer;

import java.util.HashMap;
import java.util.Map;

public class stemmer implements Runnable {
    // array of file paths
    int start, end;
    String fileHashes;
    private Map<Integer, Map<String, Integer>> stemmedDocTrees;
    public stemmer( int start, int end) {
        this.start = start;
        this.end = end;
        stemmedDocTrees = new HashMap<Integer,Map<String, Integer>>();
    }

    public final Map<Integer, Map<String, Integer>> GetDocTree() {
        return stemmedDocTrees;
    }
 
    @Override
    public void run() {
        for (int i = this.start; i < this.end; i++) {
            readFile Doc = new readFile(Constants.filesDir + Constants.DocHashes[i]+".txt", true);
            try {
                var temp=stemmedDocTrees.put(Integer.parseInt( Constants.DocHashes[i]),(HashMap<String, Integer>) EngStemmer.Stem(Doc.text));
                if(temp!=null)
                    System.out.println("Error in limits start=" +start +" end="+end);
            } catch (Throwable e) {
                System.out.println(e.getMessage());
            }
        }
    }
}