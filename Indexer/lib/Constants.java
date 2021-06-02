package lib;

public class Constants {
    // Where files are stored before stemming
    //TODO: change this to the directory in Crawler's directory to take its output 
    public static final String filesDir = "Files/";
    // where files are stored before indexing and after stemming
    public static final String stemmedDir = "Stemmed2/";
    // How many rows will be executed per query, increase this to make queries faster
    public static final int rowsPerQuery = 20000;
    // Number of threads. This will be changed to the number of files if number of files is less than it
    public static final int NThreads = 7;
}
