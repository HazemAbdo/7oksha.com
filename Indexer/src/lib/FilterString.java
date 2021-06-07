package  lib;

public class FilterString {

    public static String termOk(String term) {
        // detect stop words
        String finalTerm = term.replaceAll("[^a-zA-Z]", ""); // #https://www.geeksforgeeks.org/how-to-remove-all-non-alphanumeric-characters-from-a-string-in-java/

        finalTerm=finalTerm.toLowerCase();
        if (finalTerm.length() < 3)
            finalTerm = "";
        else if(finalTerm.length()>30)
            finalTerm="";
        else if (StopWord.isStopWord(finalTerm)) {
            finalTerm = "";
        }
        return finalTerm;
    }

    /*public static void main(String[] args) {
        System.out.println(FilterString.termOk("google)"));
    }*/
}
