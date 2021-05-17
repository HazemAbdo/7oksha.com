import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Crawler implements Runnable
{
    //Max number of pages to be crawled
    private  static  final int MAX_NUMBER_PAGES=5000;
    private  Thread thread;
    //Starting link
    private  String firstLink;
    //We make it a set as all its elements must be unique
    //As we don't want to visit any page more than once
    private Set<String> pagesVisited = new HashSet<String>();
    private List<String> pagesToVisit = new LinkedList<String>();
    //To know which thread is working know
    private int id;
    //constructor
    public Crawler (String link,int num)
    {
        firstLink=link;
        id=num;
        thread=new Thread(this);
        thread.start();
    }

    @Override
    public void run()
    {

    }
}
