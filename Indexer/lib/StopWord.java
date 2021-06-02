package lib;

//#https://gist.github.com/sebleier/554280
// This class loads stop words once and checks if a word is a stop word when 
public class StopWord {
    // the whole file will be in that string
    static String allStopWords;
    static boolean alreadyRead = false;

    public static boolean isStopWord(String word) {
        // to read them once
        if (!alreadyRead) {
            ReadFile stopWordsFile = new ReadFile("StopWords/EN");
            allStopWords = stopWordsFile.file;
            alreadyRead = true;
        }
        // TODO: the file is already sorted, think of log(n) binary search
        // check if the word is a stop word
        if (allStopWords.contains(word.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(isStopWord("haha"));
        System.out.println(isStopWord("I"));
        System.out.println(isStopWord("am"));
        System.out.println(isStopWord("Mohamed"));
    }
}
