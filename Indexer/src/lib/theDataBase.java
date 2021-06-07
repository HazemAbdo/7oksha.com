package lib;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// ! DON'T FORGET:
// ! Create table indexer;
// # table indexer: term, docnum, indx, priority

// Database initializer class
public class theDataBase {
    Connection theConnection = null;
    Statement theStatement = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public theDataBase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            theConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/searchengine", "root", "toor");
            theStatement = theConnection.createStatement();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (theConnection == null) {
                System.out.println("Could not connect to the database");
            }
            System.out.println(e.getMessage());
            if (theStatement == null) {
                System.out.println("Connection could not create statement");

            }
        }
    }

    public void CloseDB() {
        try {
            rs.close();
        } catch (Exception e) { /* Ignored */ }
        try {
            theStatement.close();
        } catch (Exception e) { /* Ignored */ }
        try {
            ps.close();
        } catch (Exception e) { /* Ignored */ }
        try {
            theConnection.close();
        } catch (Exception e) { /* Ignored */ }
    }

    public ArrayList<Integer> getFileHashes() {
        ArrayList<Integer> result = null;
        try {
            ps = theConnection.prepareStatement("SELECT DocID FROM FOUNDSITES ORDER BY DocID DESC;");
            rs = ps.executeQuery();
            result = new ArrayList<>(0);
            while (rs.next()) {
                result.add(Integer.parseInt(rs.getString(1)));
            }
        } catch (Exception e) {
            System.out.println("Error selecting DocID");
        }
        return result;
    }

    public ArrayList<String> getFileNames() {
        ArrayList<String> result = null;
        try {
            ps = theConnection.prepareStatement("SELECT DocID FROM FOUNDSITES ORDER BY DocID DESC;");
            rs = ps.executeQuery();
            result = new ArrayList<>(0);
            while (rs.next()) {
                result.add(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println("Error selecting DocID");
        }
        return result;
    }

    // ! This function is toooooo slow use insertIndexedFile instead
    public void insertWord(String word, int DocNum, int indx, int priority) {
        try {
            theStatement.execute("INSERT INTO INDEXER (term,docnum,indx,wordrank) VALUES('" + word + "', " + (DocNum)
                    + ", " + (indx) + ", " + (priority) + ");");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertIndexedTree(Integer DocNum, Map<String, Integer> Words_Freq) {
        try {
            ps = theConnection.prepareStatement("INSERT INTO INDEXER VALUES(?,?,?)");
        } catch (SQLException throwables) {
            System.out.println("prepare Statement failed");
        }
        Words_Freq.forEach((key, value) -> {
            try {
                ps.setString(1, key);
                ps.setInt(2, DocNum);
                ps.setInt(3, value);
                ps.addBatch();

            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());
                System.out.println("(" + key + "," + value + ")");
            }
        });
        try {
            ps.executeBatch();
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
        try {
            ps.clearBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insertIndexed_DocsBucket(Map<Integer,Map<String, Integer>> DocBucket) {
        try {
            ps = theConnection.prepareStatement("INSERT INTO INDEXER VALUES(?,?,?)");
        } catch (SQLException throwables) {
            System.out.println("prepare Statement failed");
        }
        DocBucket.forEach((key_DocHash, value_word_termCount) -> {
            try {
                ps.setInt(2, key_DocHash);
                value_word_termCount.forEach((key,value)->{
                    try {
                        ps.setString(1, key);
                        ps.setInt(3, value);
                        ps.addBatch();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());
                System.out.println("(" + key_DocHash + "," + value_word_termCount + ")");
            }
        });
        try {
            ps.executeBatch();
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
        try {
            ps.clearBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void insertIndexed_DocsBucket_Array(Collection<Map<Integer,Map<String, Integer>>> DocBucket_Array) {
        try {
            ps = theConnection.prepareStatement("INSERT INTO INDEXER VALUES(?,?,?)");
        } catch (SQLException throwables) {
            System.out.println("prepare Statement failed");
        }
        for(var DocBucket:DocBucket_Array) {
            DocBucket.forEach((key_DocHash, value_word_termCount) -> {
                try {
                    ps.setInt(2, key_DocHash);
                    value_word_termCount.forEach((key, value) -> {
                        try {
                            ps.setString(1, key);
                            ps.setInt(3, value);
                            ps.addBatch();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    });
                } catch (SQLException throwables) {
                    System.out.println(throwables.getMessage());
                    System.out.println("(" + key_DocHash + "," + value_word_termCount + ")");
                }
            });
        }
        try {
            ps.executeBatch();
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
        try {
            ps.clearBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    // for debugging reasons, print all rows of indexer table
    public void printAllRows() {
        try {
            ps = theConnection.prepareStatement("select * from INDEXER");

            rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(
                        rs.getString(1) + "   " + rs.getString(2) + "   " + rs.getString(3) +
                                "   " + rs.getString(4));

            }

        } catch (Exception e) {
            System.out.println("Error in getData" + e);
        }
    }

    public int TF(String term, int docNum) {
        if ((term = FilterString.termOk(term)).equals("")) {
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

    public ArrayList<Integer> searchFor(String term) {
        term = FilterString.termOk(term);
        if (term.equals("")) {
            return null;
        }
        ArrayList<Integer> result = null;
        try {
            // $ IMPORTANT SQL QUERY
            ps = theConnection.prepareStatement("SELECT DOCNUM FROM INDEXER WHERE term = '" + term + "';");
            rs = ps.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                result.add(Integer.parseInt(rs.getString(1)));
            }
        } catch (Exception e) {
            System.out.println("Error selecting HASH_VALUE");
        }
        return result;
    }

    public void insertFoundSites(Map<Integer, String> DocHash_URL) {
        try {
            ps = theConnection.prepareStatement("INSERT IGNORE INTO FOUNDSITES (URL,HASH_VALUE) VALUES(?,?)");
        } catch (SQLException throwables) {
            System.out.println("prepare Statement failed");
        }
        DocHash_URL.forEach((key, value) -> {
            try {
                ps.setString(1, value);
                ps.setInt(2, key);
                ps.addBatch();

            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());
                System.out.println("(" + key + "," + value + ")");
            }
        });
        try {
            ps.executeBatch();
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
        try {
            ps.clearBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /*public static void main(String[] args) {
        theDataBase db = new theDataBase();
        db.printAllRows();
    }*/
}
