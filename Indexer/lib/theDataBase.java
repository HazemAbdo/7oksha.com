package lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

// ! DON'T FORGET:
// ! Create table indexer;
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
    }

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

    public void insertWord(String word, int DocNum, int indx, int priority) {
        try {
            theStatement.execute("INSERT INTO INDEXER (term,docnum,indx,wordrank) VALUES('" + word + "', " + (DocNum)
                    + ", " + (indx) + ", " + (priority) + ");");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

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
