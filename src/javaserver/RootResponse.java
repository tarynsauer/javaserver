package javaserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import static javaserver.JavaserverConstants.DIRECTORY_PATH;
import static javaserver.HTTPStatusConstants.OK;
/**
 * Created by Taryn on 3/4/14.
 */
public class RootResponse extends AbstractResponse {

    @Override
    public byte[] getResponseMessage(RequestParser parser) throws IOException {
        String response = getStatusLine(OK) + getDateInfo() + getServerInfo() + getContentTypeInfo("text/html") + getFile(parser);
        return response.getBytes();
    }

    private String getFile(RequestParser parser) throws IOException {
        String bodyBegin = bodyBegin();
        String bodyEnd = bodyEnd();
        String uri = parser.getRequestURI();
        File directory = new File(DIRECTORY_PATH);
        File[] fileListing = directory.listFiles();

        if (uri.equals("/")) {
            return bodyBegin + "<h1>Hey, there!</h1>" + listFiles(fileListing) + bodyEnd;
        } else if (uri.equals("/file1")) {
            return bodyBegin + getFileContents(DIRECTORY_PATH + "file1") + bodyEnd;
        } else if (uri.equals("/file2")) {
            return bodyBegin + getFileContents(DIRECTORY_PATH + "file2") + bodyEnd;
        } else {
            return bodyBegin + getFileContents(DIRECTORY_PATH + "text-file.txt") + bodyEnd;
        }
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

    private String listFiles(File[] files) {
        String fileList = "";
        for (File file : files) {
            fileList += "<li><a href='" + file.getName() + "'>" + file.getName() + "</a></li>";
        }
        return fileList;
    }
}