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
// table indexer: term, docnum, indx, priority

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
            ps = theConnection.prepareStatement("SELECT URL FROM FOUNDSITES;");
            rs = ps.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                result.add(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println("Error selecting URL");
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

    // TODO: use array of indecies instead of in-place indecies in order to get true
    // TODO: indecies in the original file
    // TODO: (This is because stopped words are ommited)
    public void insertIndexedFile(ArrayList<String> words, int DocNum, int priorities) {
        for (int i = 0; i < words.size(); i++) {
            // initial statement
            String query = new String("INSERT INTO INDEXER (term,docnum,indx,wordrank) VALUES('" + words.get(i) + "', "
                    + (DocNum) + ", " + (i) + ", " + (priorities) + ")");
            i++;
            // add Constants.rowsPerQuery statements
            for (int j = 1; j < Constants.rowsPerQuery && i < words.size(); i++, j++) {
                query += ",('" + words.get(i) + "', " + (DocNum) + ", " + (i) + ", " + (priorities) + ")";
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

    public static void main(String[] args) {
        theDataBase db = new theDataBase();
        db.insertWord("hello", 1, 1, 1);
        db.insertWord("world", 1, 2, 1);
        db.printAllRows();
    }

}
