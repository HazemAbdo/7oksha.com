package  lib;
public class Constants {
    //Hashes.txt path
    public static  final String HashesPath="./main/allhashes.txt";
    public static String[] DocHashes;
    // Where files are stored before stemming
    public static final String filesDir = "./main/5000/";
    // where files are stored before indexing and after stemming
    public static final String stemmedDir = "./main/5000_output/";
    // How many rows will be executed per query, increase this to make queries faster
    public static final int rowsPerQuery = 10000;
    // Number of threads. This will be changed to the number of files if number of files is less than it
    public static final int NThreads = 8;
    // Number of files made
    // public static int NFILES = 0;
    // Number of files per Indexer query
    public static final int filesPerIndexerQuery = 300;
}
