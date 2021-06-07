package lib;
import java.util.ArrayList;
import java.util.Collections;
//#https://gist.github.com/sebleier/554280
// This class loads stop words once and checks if a word is a stop word when 
public class StopWord {
    // the whole file will be in that string
    // static String allStopWords;
    static boolean alreadyRead = false;
    static ArrayList<String> allStopWords = new ArrayList<>();

    public static boolean isStopWord(String word) {
        // to read them once
        if (!alreadyRead) {
            alreadyRead = true;
            // allStopWords = new ArrayList<>();
            readFile stopWordsFile = new readFile("./src/StopWords/EN");
            String[] stopWordsStringArr = stopWordsFile.text.toLowerCase().split("\n");
            for (int i = 0; i < stopWordsStringArr.length; i++) {
                allStopWords.add(stopWordsStringArr[i]);
            }
            Collections.sort(allStopWords);
        }
        // check if the word is a stop word (if found in the stop words)
        if(Collections.binarySearch(allStopWords, word)>=0){
            return true;
        } else {
            return false;
        }
    }

    /*public static void main(String[] args) {
        System.out.println(isStopWord("haha"));
        System.out.println(isStopWord("I"));
        System.out.println(isStopWord("am"));
        System.out.println(isStopWord("Mohamed"));
    }*/
}
