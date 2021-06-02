// TODO look for this file's TODOs below
// TODO make TF(term,file) function (term frequency) with SQL queries
// TODO make IDF(term) function (Inverse document frequency) using SQL queries
// TODO make the searchQuery(term) function that returns all files of a word with SQL

package lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

// ! DON'T FORGET:
// ! Create table indexer;
// # table indexer: term, docnum, indx, priority

// Database initializer class
public class theDataBase {
    public Connection theConnection = null;
    public Statement theStatement = null;
    public PreparedStatement ps = null;
    public ResultSet rs = null;

    public theDataBase() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            theConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/searchengine", "root", "00000");
        } catch (Exception e) {
            System.out.println(e.toString());
            if (theConnection == null) {
                System.out.println("Could not connect to the database");
            }
        }
        try {
            theStatement = theConnection.createStatement();
        } catch (Exception e) {
            System.out.println(e.toString());
            if (theStatement == null) {
                System.out.println("Connection could not create statement");
            }
        }
        try {
            // ? should we delete this line?
            // theStatement.execute("DELETE FROM INDEXER;");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // get an array of files names from database
    // TODO: use hashCode instead of URL because fileNames their hashCodes
    // TODO: Make indexer hashCode a Foreign key to hashCode in foundsites table
    public ArrayList<String> getFileNames() {
        ArrayList<String> result = null;
        try {
            ps = theConnection.prepareStatement("SELECT HASH_VALUE FROM FOUNDSITES;");
            rs = ps.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                result.add(rs.getString(1) + ".txt");
            }
        } catch (Exception e) {
            System.out.println("Error selecting HASH_VALUE");
        }
        return result;
    }

    // ! This function is toooooo slow use insertIndexedFile instead
    public void insertWord(String word, int DocNum, int indx, int priority) {
        try {
            theStatement.execute("INSERT INTO INDEXER (term,docnum,indx,wordrank) VALUES('" + word + "', " + (DocNum)
                    + ", " + (indx) + ", " + (priority) + ");");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void insertIndexedFile(ArrayList<String> words, ArrayList<Integer> indecies, int DocNum, int priorities) {
        for (int i = 0; i < words.size(); i++) {
            // initial statement
            String query = new String("INSERT INTO INDEXER (term,docnum,indx,wordrank) VALUES('" + words.get(i) + "', "
                    + (DocNum) + ", " + (indecies.get(i)) + ", " + (priorities) + ")");
            i++;
            // add Constants.rowsPerQuery statements
            for (int j = 1; j < Constants.rowsPerQuery && i < words.size(); i++, j++) {
                query += ",('" + words.get(i) + "', " + (DocNum) + ", " + (indecies.get(i)) + ", " + (priorities) + ")";
            }
            i--;// will be increased again next loop
            query += ";";
            // execute all of them
            try {
                theStatement.execute(query);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    // for debugging reasons, print all rows of indexer table
    public void printAllRows() {
        try {
            ps = theConnection.prepareStatement("select * from INDEXER");

            rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(
                        rs.getString(1) + "   " + rs.getString(2) + "   " + rs.getString(3) + "   " + rs.getString(4));

            }

        } catch (Exception e) {
            System.out.println("Error in getData" + e);
        }
    }

    // TODO YOU MUST STEM TERM
    // ! didn't try it yet
    public int TF(String term, int docNum) {
        if ((term = FilterString.termOk(term)) == "") {
            return 0;
        }
        try {
            ps = theConnection.prepareStatement(
                    "SELECT COUNT * FROM INDEXER WHERE TERM = '" + term + "' AND DOCNUM = " + docNum + ";");
            rs = ps.executeQuery();
            if (rs.next()) {
                return Integer.parseInt(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println("Error selecting HASH_VALUE");
        }
        return 0;
    }
    
    // TODO stem the coming word or send it stemmed
    // $$$$$$$$$$$$$$$$$$$$$$$$$$$$
    // return array of document hashes
    // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    public ArrayList<Integer> searchFor(String term){
        term = FilterString.termOk(term);
        if(term == ""){
            return null;
        }
        ArrayList<Integer> result = null;
        try {
            // $ IMPORTANT SQL QUERY
            ps = theConnection.prepareStatement("SELECT DOCNUM FROM INDEXER WHERE term = '"+term+"';");
            rs = ps.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                result.add(Integer.parseInt(rs.getString(1)) );
            }
        } catch (Exception e) {
            System.out.println("Error selecting HASH_VALUE");
        }
        return result;
    }
    public static void main(String[] args) {
        theDataBase db = new theDataBase();
        db.insertWord("hello", 1, 1, 1);
        db.insertWord("world", 1, 2, 1);
        db.printAllRows();
    }

}
