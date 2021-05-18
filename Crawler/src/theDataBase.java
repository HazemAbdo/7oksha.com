import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class theDataBase {
        public Connection theConnection=null;
        public Statement theStatement=null;
        public PreparedStatement ps=null;
        public ResultSet rs=null;
       public theDataBase()
        {
            try {
                theConnection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/searchengine", "root", "toor");
            }
            catch (Exception e)
            {
                System.out.println(e.toString());
                if(theConnection==null)
                {
                System.out.println("AAAAAAA1");
                }
            }
            try {
                theStatement = theConnection.createStatement();
            }
            catch (Exception e)
            {
                System.out.println(e.toString());
                if(theStatement==null) {
                    System.out.println("AAAAAAA2");
                }
            }
        }
        public void printAllRows()
        {
            try{
                ps=theConnection.prepareStatement("select * from foundsites");

                rs=ps.executeQuery();
                while(rs.next())
                {
                    System.out.println(rs.getString(2)); //here you can get data, the '1' indicates column number based on your query

                }

            }
            catch(Exception e)
            {
                System.out.println("Error in getData"+e);
            }
        }

    public static void main(String[] args) {
        theDataBase db=new theDataBase();
        db.printAllRows();
    }


}
