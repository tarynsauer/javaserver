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
    public byte[] getResponseMessage(RequestParser parser) throws IOException {
        String response = getStatusLine("200 OK") + getDateInfo() + getServerInfo() + getContentTypeInfo("text/html") + getFile(parser);
        return response.getBytes();
    }

    public String getFile(RequestParser parser) throws IOException {
        String bodyBegin = "<title>Taryn's Website</title>\n" + "</head>\n" + "<body>\n";
        String bodyEnd = "</body>\n" + "</html>";
        String uri = parser.getRequestURI();
        File directory = new File(directoryPath);
        File[] fileListing = directory.listFiles();

        if (uri.equals("/")) {
            return bodyBegin + "<h1>Hey, there!</h1>" + listFiles(fileListing) + bodyEnd;
        } else if (uri.equals("/file1")) {
            return bodyBegin + getFileContents(directoryPath + "file1") + bodyEnd;
        } else if (uri.equals("/file2")) {
            return bodyBegin + getFileContents(directoryPath + "file2") + bodyEnd;
        } else if (uri.equals("/text-file.txt")) {
            return bodyBegin + getFileContents(directoryPath + "text-file.txt") + bodyEnd;
        } else {
            return bodyBegin + "<h1>Hmm...it looks like you're looking for:</h1>" + "<p>" + uri + "</p>" + bodyEnd;
        }
    }

    public String listFiles(File[] files) {
        String fileList = "";
        for (File file : files) {
            fileList += "<li><a href='" + file.getName() + "'>" + file.getName() + "</a></li>";
        }
        return fileList;
    }

    public String getFileContents(String filePath) {
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