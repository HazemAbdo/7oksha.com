import java.util.ArrayList;
public class Main {
    public static void main(String[] args)
    {
        ArrayList<Crawler> bots=new ArrayList<>();

        for(int i=0;i<3;i++)
        {
            bots.add(new Crawler(i));
        }
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







    }

}
