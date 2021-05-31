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
        public void insert_foundsite(String url, int hash_code)
        {

            //String theQuery="INSERT INTO foundsites values ('"+url+"','"+Integer.toString(hash_code)+"');"+priority+")";
            String theQuery="INSERT INTO foundsites (URL,HASH_VALUE) VALUES ('"+url+"','"+hash_code+"');";
            try {
                ps = theConnection.prepareStatement(theQuery);
                ps.executeUpdate();
            }
            catch (Exception e)
            {
              //  System.out.println(e.toString());
              System.out.println("ERROR IN SQL STATEMENT ");

            }
        }
        public int Queue_url_isEmpty()
        {
            String theQuery="SELECT * FROM searchengine.queue_sites;";
            //int flag=0;
            try{
                ps=theConnection.prepareStatement(theQuery);

                rs=ps.executeQuery();
                if (rs.next() == false)
                {
                    return 1;   //means empty
                }
                else
                {
                    return 0;   // means not empty
                }

            }
            catch(Exception e)
            {
                System.out.println("Error in getData"+e);
            }
            return 1;
        }

    public static void main(String[] args) {
        theDataBase db=new theDataBase();

       db.insert_foundsite("ddddd.com",1111);
        db.printAllRows();
        String MYNAME="MOHAMED";
      //  System.out.println(MYNAME.hashCode());
        //1948515761
    }


}


