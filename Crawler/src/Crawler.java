
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.io.Serial;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.io.PrintWriter;

public class Crawler implements Runnable
{
    //Max number of pages to be crawled
    private  static  final int MAX_NUMBER_PAGES=5000;
    private  Thread thread;
    //Starting link
    private  String firstLink;
    private ArrayList<String> visitedLinks=new ArrayList<String>();
    //To know which thread is working know
    private Queue<String> queue_URL =new LinkedList<>();

    private int id;
    theDataBase db;
    //constructor
    public Crawler (String link,int num)
    {
        db=new theDataBase();
        // todo fill the queue with number of links do not star with only one link
        firstLink=link;
        queue_URL.add(firstLink);
        id=num;
        thread=new Thread(this);
        thread.start();
    }
    //The crawler must not visit the same URL more than once


    @Override
    public void run()
    {
crawl();
    }
private void crawl()
{
    // TODO     use a for loop instead and  extract to be crawled link form the table in database
    while (!queue_URL.isEmpty() && visitedLinks.size()<MAX_NUMBER_PAGES)
    {
        String url=queue_URL.poll();
        try {
            Document doc = request(url);
            try (PrintWriter out = new PrintWriter(doc.hashCode()+".txt")) {
                out.println(doc.toString());
            }
            // url_name , hash_of_doc

            if (doc != null) {
                //A HTML ((Element)) consists of a tag name, attributes,
                // and child nodes (including text nodes and other elements).
                // From an Element, you can extract data,
                // traverse the node graph, and manipulate the HTML.
                //------------------------------------------------
                // You want to find or manipulate elements using a
                // CSS or jquery-like selector syntax.
                //use select()
                for (Element link : doc.select("a[href]")) {
                    //You have a HTML document that contains relative URLs,
                    // which you need to resolve to absolute URLs.
                    //use absUrl()
                    String next_link = link.absUrl("href");
                    //We make it a set as all its elements must be unique
                    //As we don't want to visit any page more than once
                    if (!visitedLinks.contains(next_link)) {
                        queue_URL.add(next_link);
                    }
                }
                visitedLinks.add(url);
                //System.out.println(url);
            }
        }
        catch (Exception e)
        {
            System.out.println("ERROR IN URL BUT CATCHED");
        }

    }

    /*if(numPages<MAX_NUMBER_PAGES)
    {
        Document doc=request(url);

        if(doc!=null)
        {
            //inserted in the database the current url an hashed its document to use it for checking later

            //
            db.insert_foundsite(url,doc.hashCode(),0);



            //A HTML ((Element)) consists of a tag name, attributes,
            // and child nodes (including text nodes and other elements).
            // From an Element, you can extract data,
            // traverse the node graph, and manipulate the HTML.
            //------------------------------------------------
            // You want to find or manipulate elements using a
            // CSS or jquery-like selector syntax.
            //use select()
            for(Element link : doc.select("a[href]"))
            {
                //You have a HTML document that contains relative URLs,
                // which you need to resolve to absolute URLs.
                //use absUrl()
                String next_link =link.absUrl("href");
                //We make it a set as all its elements must be unique
                //As we don't want to visit any page more than once
                if(visitedLinks.contains(next_link)==false)
                {
                    crawl(numPages++,next_link);
                }
            }

        }
    }*/


}






    //fetching  content from the web, and parse them into Documents
    private Document request (String url) {
        try
        {
           // A Connection provides a convenient interface to
            // fetch content from the web, and parse them into Documents.
            //---------------------------------------------------------
            // The connect(String url) method creates a new Connection,
            // and get() fetches and parses a HTML file.
            // If an error occurs whilst fetching the URL,
           // it will throw an IOException, which you should handle appropriately.
            Connection con;
            con= Jsoup.connect(url);
            Document doc =  con.get();
            //Standard response for successful HTTP requests is 200
            if(con.response().statusCode()==200)
            {
                System.out.println("\n**Bot ID:"+ id +" Recieved webpage at " + url);
                String title=doc.title();
                visitedLinks.add(url);
                return doc;
            }
            return null;
        }
        catch (IOException e)
        {
           return null;
        }
    }

public Thread getThread()
{
    return thread;
}



}
