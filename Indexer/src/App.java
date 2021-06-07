import lib.FoundSites;
import lib.Indexer;

public class App {
    public static void main(String[] args) throws InterruptedException {
        FoundSites.SitesHashes();
        long start = System.currentTimeMillis();
        Indexer.goIndexer();
        //Indexer.Index();
        long time=System.currentTimeMillis()-start;
        System.out.println(time/60000);
    }
}