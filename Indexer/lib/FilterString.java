package lib;

// This class detects stop words (like a, an, this,etc..)
// and also returns only english letters and numbers
public class FilterString {

    public static String termOk(String term){
        String finalTerm = new String(term);
        // detect stop words
        if (StopWord.isStopWord(finalTerm)) {
            return "";
        }
        // Only Alphabetic or numeral
        finalTerm = finalTerm.replaceAll("[^a-zA-Z0-9]", ""); // #https://www.geeksforgeeks.org/how-to-remove-all-non-alphanumeric-characters-from-a-string-in-java/
        return finalTerm;
    }

    public static void main(String[] args) {
        System.out.println(FilterString.termOk("(Hello@!()>., World"));
    }
}
