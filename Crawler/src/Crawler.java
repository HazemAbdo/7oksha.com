
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import java.net.MalformedURLException;

import java.io.*;
import java.net.URL;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Crawler implements Runnable
{
    //Max number of pages to be crawled

    private  static  final int MAX_NUMBER_PAGES=5000;
    private  Thread thread;
    //Starting link
    private  String firstLink;
    private ArrayList<String> visitedLinks=new ArrayList<String>();
    //To know which thread is working now
    private Queue<String> queue_URL =new LinkedList<>();

    private int id;
    theDataBase db;
    //constructor
    public Crawler (int num)
    {
        db=new theDataBase();
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
    while (!db.Queue_url_isEmpty() && db.Count_FoundSites()<MAX_NUMBER_PAGES)
    {
        String url;
        synchronized (db)
        {
            try
            {
                url = db.get_next_url_queue();
                db.dequeue_FROM_TABLE();
            }
            catch (Error e)
            {
                url=null;
             // System.out.println("URL IS EMPTY");
            }
        }
        try {
            Document doc = request(url);
         // System.out.println(doc.body().text());
            try (PrintWriter out = new PrintWriter(doc.hashCode()+".txt")) {
                out.println(Jsoup.parse(doc.html()));
            }
            // url_name , hash_of_doc
            //*************************************Robot.txt**********************


            //*************************************Robot.txt**********************

            if (doc != null) {
                ArrayList<String> DisAllows=new ArrayList<String>();
                //A HTML ((Element)) consists of a tag name, attributes,
                // and child nodes (including text nodes and other elements).
                // From an Element, you can extract data,
                // traverse the node graph, and manipulate the HTML.
                //------------------------------------------------
                // You want to find or manipulate elements using a
                // CSS or jquery-like selector syntax.
                //use select()
                DisAllows=Get_DisAllows_Of_Url(url);
             // System.out.println("Hello I am inside crawl func");
                for (int i=0;i<DisAllows.size();i++)
                {
                 // System.out.println(DisAllows.get(i));
                }
                for (Element link : doc.select("a[href]")) {
                    //You have a HTML document that contains relative URLs,
                    // which you need to resolve to absolute URLs.
                    //use absUrl()

                    String next_link = link.absUrl("href");
                  /*  ArrayList<String>temp=new ArrayList<String>();
                    temp.add("https://google.com//search");
                    temp.add("https://google.com//wml?");
                    temp.add("https://google.com//ebooks?*buy=*");
                    temp.add("https://google.com//ebooks?*q=subject:*");
                    int count=0;
                  String  next_link=temp.get(count++);*/
                    //We make it a set as all its elements must be unique
                    //As we don't want to visit any page more than once
                    synchronized (db)
                    {
                        if (!db.URL_exists_in_found_sites(next_link) && !db.URL_exists_in_QUEUE(next_link))
                        {
                            if(!DisAllows.contains(next_link))
                            {
                             // System.out.println(next_link + "  Not in Disallows");
                                db.enqueue_URL_QUEUE(next_link);
                            }
                           /* else
                            {
                             // System.out.println(next_link + "   in Disallows");
                            }*/
                        }
                    }
                }
                db.insert_foundsite(url,doc.hashCode());

                //System.out.println(url);
            }
        }
        catch (Exception e)
        {
         // System.out.println("ERROR IN URL BUT CATCHED");
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
             // System.out.println("\n**Bot ID:"+ id +" Recieved webpage at " + url);
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

//------------------------------------------Take url and return array of disallows urls------------------------------
public  ArrayList<String> Get_DisAllows_Of_Url(String url)
{
    //sending url
    String line = null;
    //array that contains all disallow urls
    ArrayList<String> DisAllows=new ArrayList<String>();
 // System.out.println(url+"/robots.txt");
//To read robots.txt line by line (if this robot.txt exists else return null)
    try(BufferedReader in = new BufferedReader(
            new InputStreamReader(new URL(url+"/robots.txt").openStream()))) {
        //-------------looping to know lines of userAgent o(n)-----------------
        //Can be deleted but for the sake of optimization
        ArrayList<Integer> num_in_UserAgent=new ArrayList<Integer>();
        Integer counter=0;
        while((line = in.readLine()) != null) {

            if(line.contains("User-agent:"))
                num_in_UserAgent.add(counter);
            counter++;
        }
        for (int i=1;i<num_in_UserAgent.size();i++)
        {
            //if first user agent is at line 2 and second in line 7 then the
            //distance between them is 7-2=5
            num_in_UserAgent.set(i,num_in_UserAgent.get(i)-num_in_UserAgent.get(i-1));
        }
        //----------------------------------Get disallow----------------------------------
        //read file again
        BufferedReader inn=new BufferedReader(
                new InputStreamReader(new URL(url+"/robots.txt").openStream()));
        for (int i=0;i<counter;i++) {
            int counter_num_ine_UserAgent = 1;
            line = inn.readLine();

            if (line != null)
            {
                //User-agent: *   ----->  all crawlers(including our crawler)
                //User-agent: anything ------->(our crawler not included)
                if (line.contains("User-agent:")) {
                    if (!line.contains("*")) {
                        //ex: User-agent: Facebook
                        //then all the lines till next User-agent: will be neglected
                        i += num_in_UserAgent.get(counter_num_ine_UserAgent++) - 1;
                    }
                }

                if (line.contains("Disallow:"))
                    {
                        //10------> Disallows:(space)
                        DisAllows.add(url +'/'+line.substring(10));
                       //System.out.println("Robot Disallows " + url + '/' + line.substring(10));
                    }
                    /*else if(line.contains("Allow:"))
                        {
                     // System.out.println("Robot allows " + url + '/' + line.substring(7));

                        }*/
            }
        }
    }
    catch (Exception e)
    {
       return null;
    }
    return  DisAllows;
}





}
