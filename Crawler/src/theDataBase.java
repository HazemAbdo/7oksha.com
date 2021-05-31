import java.io.Serial;
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
        public boolean Queue_url_isEmpty()
        {
            String theQuery="SELECT * FROM searchengine.queue_sites;";
            //int flag=0;
            try{
                ps=theConnection.prepareStatement(theQuery);

                rs=ps.executeQuery();
                if (rs.next() == false)
                {
                    return true;   //means empty
                }
                else
                {
                    return false;   // means not empty
                }

            }
            catch(Exception e)
            {
                System.out.println("Error in getData"+e);
            }
            return true;
        }
        public int Count_FoundSites()
        {
            String query = "select count(*) from foundsites";
            //Executing the query
            try {
                ps=theConnection.prepareStatement(query);
                rs = ps.executeQuery(query);
                //Retrieving the result
                rs.next();
                int count = rs.getInt(1);
                //System.out.println("Number of records in the foundsites table: " + count);
                return count;
            }
            catch (Exception e)
            {
            System.out.println("ERROR IN GETSIZE"+e.toString());
            }
            return -1;
        }
        public String get_next_url_queue()
        {
            String theQuery="SELECT  URL,MIN(ID) FROM queue_sites ;";
            try{
                ps=theConnection.prepareStatement(theQuery);

                rs=ps.executeQuery();
                /*while(rs.next())
                {
                    System.out.println(rs.getString(1)+"   "+ rs.getString(2)); //here you can get data, the '1' indicates column number based on your query

                }*/
                rs.next();
                return rs.getString(1);

            }
            catch(Exception e)
            {
                System.out.println("Error in getData"+e);
            }
            return  "";
        }
        public void dequeue_FROM_TABLE()
        {
            String theQuery="delete from queue_sites order by ID limit 1;";
            try{
                ps=theConnection.prepareStatement(theQuery);

                ps.executeUpdate();
            }
            catch(Exception e)
            {
                System.out.println("Error in Deleting"+e);
            }

        }

    public void enqueue_URL_QUEUE(String url)
    {


        String theQuery="INSERT INTO  QUEUE_SITES (URL) VALUES ('"+url+"');";
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

    public boolean URL_exists_in_found_sites(String url)  //true if exists,   false if not exist
    {
        String theQuery="SELECT * FROM foundsites where URL='"+url+"';";
        //int flag=0;
        try{
            ps=theConnection.prepareStatement(theQuery);

            rs=ps.executeQuery();
            if (rs.next() == false)
            {
                return false;   //means DOES NOT exist
            }
            else
            {
                return true;   // means exists
            }

        }
        catch(Exception e)
        {
            System.out.println("Error in getData"+e);
        }
        return false;
    }


    public static void main(String[] args) {
        theDataBase db=new theDataBase();

     //  db.enqueue_URL_QUEUE("KAAAAAAAAAAAAAAAAAAAAAAAAK.com");
       // System.out.println("mohamed exists="+db.URL_exists_in_found_sites("kmohamed.com"));
       // System.out.println("rols exists="+db.URL_exists_in_found_sites("rola.com"));
        /*System.out.println("next url="+db.get_next_url_queue());
        db.dequeue_FROM_TABLE();
        System.out.println("next url="+db.get_next_url_queue());*/
       /* System.out.println("size="+db.Count_FoundSites());
       db.insert_foundsite("dfggdfgsgdgdfgdddd.com",111551);
        db.insert_foundsite("ddddfgdsfgsdfsasdasdasdasdasdasdasdd.com",331122);
        db.printAllRows();
        System.out.println("size="+db.Count_FoundSites());
        String MYNAME="MOHAMED";*/
      //  System.out.println(MYNAME.hashCode());
        //1948515761
    }


}


