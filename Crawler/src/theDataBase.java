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
                    System.out.println(rs.getString(1)+"   "+ rs.getString(2)); //here you can get data, the '1' indicates column number based on your query

                }

            }
            catch(Exception e)
            {
                System.out.println("Error in getData"+e);
            }
        }
        public void insert_foundsite(String url, int hash_code,int priority)
        {

            String theQuery="INSERT INTO foundsites values ('"+url+"','"+Integer.toString(hash_code)+"'"+priority+")";
            String kak="INSERT INTO users (first_name, last_name, is_admin, num_points) "
                    +"VALUES ('Fred', 'Flinstone', false, 10000)";
            try {
                ps = theConnection.prepareStatement(theQuery);
                ps.executeUpdate();
            }
            catch (Exception e)
            {
              //  System.out.println(e.toString());
              //  System.out.println("ERROR IN SQL STATEMENT ");

            }
        }

    public static void main(String[] args) {
        theDataBase db=new theDataBase();

       // db.insert_foundsite("kahhk.com",196515761);
        db.printAllRows();
        String MYNAME="MOHAMED";
      //  System.out.println(MYNAME.hashCode());
        //1948515761
    }


}
