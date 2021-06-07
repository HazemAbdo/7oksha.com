package snowball;

import lib.Constants;
import lib.FilterString;
import lib.readFile;
import snowball.ext.englishStemmer;

import java.util.HashMap;
import java.util.Map;

public class EngStemmer {

    public static final Map<String, Integer> Stem(String IN) throws Throwable {

        SnowballStemmer stemmer = new englishStemmer();

        String[] Words = IN.split("[^\\w]+");
        Map<String, Integer> Output = new HashMap<String, Integer>();

        for (String word : Words) {
            String newWord = FilterString.termOk(word);
            if (!newWord.isBlank()) {
                //newWord = newWord.toLowerCase();
                stemmer.setCurrent(newWord);
                stemmer.stem();
                String temp = stemmer.getCurrent();
                if (Output.get(temp) == null)
                    Output.put(temp, 1);
                else
                    Output.put(temp, Output.get(temp) + 1);
            }
        }
        return Output;
    }

    public static void main(String args[]) throws Throwable {
        int sum=0;
        for (String word : Constants.DocHashes) {
            readFile inFile = new readFile(Constants.filesDir +word + ".txt", true);
            sum+=new EngStemmer().Stem(inFile.text).size();
            //sum+=res.size();
            //System.out.println(res.size());
        }
        /*for (String word : hashes) {
            System.out.println(word);
        }*/
            System.out.println(sum);

        /*String W="the     quick brown/fox";
        for(String s:W.split("[^\\w]+"))
            System.out.println(s);*/

        //for (String key : res.keySet()) {
        //Integer value = res.get(key);
        //System.out.println(key + " " + value);
        //}
    }
}
