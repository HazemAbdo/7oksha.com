package lib;
//#https://gist.github.com/sebleier/554280
public class StopWord {
    static String allStopWords;
    static boolean alreadyRead = false;
    public static boolean isStopWord(String word){
        //to read them once
        if(!alreadyRead){
            ReadFile stopWordsFile = new ReadFile("StopWords/EN");
            allStopWords = stopWordsFile.file;
            alreadyRead = true;
        }
        if(allStopWords.contains(word.toLowerCase())){
            return true;
        }
        else{
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
