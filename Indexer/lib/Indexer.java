package lib;
import java.util.ArrayList;

public class Indexer implements Runnable{
    //array of file paths
    ArrayList<String> Docs;
    theDataBase db;
    int start,end;
    public Indexer(ArrayList<String>Docs,int start,int end,theDataBase db){
        this.Docs = Docs;
        this.start = start;
        this.end = end;
        this.db = db;
    }

    @Override
    public void run() {
        int indx = 0;
        for (int i = this.start; i < this.end; i++) {
            ReadFile rFile = new ReadFile(this.Docs.get(i));
            String[] lines = rFile.file.split("\n");
            for (int j = 0; j < lines.length; j++) {
                String[] terms = lines[j].split(" ");
                for (int k = 0; k < terms.length; k++) {
                    db.insertWord(terms[k], i, indx++, 0);
                }
            }
        }
    }

    public static void main(String[] args) {
        ArrayList<String> files = new ArrayList<>();
        files.add("Files/in1.txt");
        theDataBase db = new theDataBase();
        Thread t1 = new Thread(new Indexer(files,0,1,db));
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
    }
}
