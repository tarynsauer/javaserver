package tddserver;

import java.io.File;

import static javaserver.JavaserverConstants.DIRECTORY_PATH;

/**
 * Created by Taryn on 3/13/14.
 */
public class RootPage extends Page {
    public RootPage(String url) {
        super(url);
    }

    @Override
    public String getContent(RequestParser parser) {
        buildHomepage();
        return this.content;
    }

    private void buildHomepage() {
        File directory = new File(DIRECTORY_PATH);
        File[] fileListing = directory.listFiles();
        this.content = "<h1>Hey, there!</h1>" + listFiles(fileListing);
    }

    private String listFiles(File[] files) {
        String fileList = "";
        for (File file : files) {
            fileList += "<li><a href='" + file.getName() + "'>" + file.getName() + "</a></li>";
        }
        return fileList;
    }
}
