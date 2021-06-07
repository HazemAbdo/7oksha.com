package lib;

import java.util.HashMap;
import java.util.Map;

public class FoundSites {

    public static void SitesHashes() {

        theDataBase db = new theDataBase();
        Constants.DocHashes= db.getFileNames().toArray(new String[0]);
        db.CloseDB();
    }
}
