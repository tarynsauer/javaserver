package javaserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
/**
 * Created by Taryn on 3/4/14.
 */
public class RootResponse extends AbstractResponse {
    private String directoryPath = "/Users/Taryn/8thLight/cob_spec/public/";

    @Override
    public byte[] getResponseMessage(String status, String condition) {
        String response = getStatusLine(status) + getDateInfo() + getServerInfo() + getContentTypeInfo("text/html") + getBody(condition);
        return response.getBytes();
    }

    @Override
    public String getBody(String condition) {
        String bodyBegin = "<title>Taryn's Website</title>\n" + "</head>\n" + "<body>\n";
        String bodyEnd = "</body>\n" + "</html>";
        File directory = new File(directoryPath);
        File[] fileListing = directory.listFiles();

        if (condition.equals("root")) {
            return bodyBegin + "<h1>Hey, there!</h1>" + listFiles(fileListing) + bodyEnd;
        } else if (condition.equals("file1")) {
            return bodyBegin + getFileContents(directoryPath + "file1") + bodyEnd;
        } else if (condition.equals("file2")) {
            return bodyBegin + getFileContents(directoryPath + "file2") + bodyEnd;
        } else if (condition.equals("partial_content.txt")) {
            return bodyBegin + getFileContents(directoryPath + "partial_content.txt") + bodyEnd;
        } else if (condition.equals("text-file.txt")) {
            return bodyBegin + getFileContents(directoryPath + "text-file.txt") + bodyEnd;
        } else {
            return "";
        }
    }

    public String listFiles(File[] files) {
        String fileList = "";
        for (File file : files) {
            File filePath = new File(file.getPath());
            fileList += "<li><a href='" + file.getName() + "'>" + file.getName() + "</a></li>";
        }
        return fileList;
    }

    private String getFileContents(String filePath) {
        FileInputStream fis = null;
        StringBuilder builder = new StringBuilder();
        try {
            fis = new FileInputStream(filePath);

            int content;
            while ((content = fis.read()) != -1) {
                builder.append((char)content);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return builder.toString();
    }
}
