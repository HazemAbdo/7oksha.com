import java.util.ArrayList;
public class Main {
    public static void main(String[] args)
    {
        ArrayList<Crawler> bots=new ArrayList<>();
        bots.add(new Crawler("https://abcnews.go.com",1));
        bots.add(new Crawler("https://www.npr.org",2));
        bots.add(new Crawler("https://www.nytimes.com",3));
        for (Crawler w :bots)
        {
            try {
                w.getThread().join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println("FINISHED");
        //TODO robot.txt
        //TODO create the n*threads in the main




        //TODO recrawl on a certain codition
        // RECRWALING IS NOT REQUIRED


    }

}
