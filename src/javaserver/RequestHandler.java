package javaserver;

import java.io.IOException;
import java.io.File;
/**
 * Created by Taryn on 3/4/14.
 */
public class RequestHandler {
    private RequestParser parser;
    private String directory;

    public RequestHandler(RequestParser parser, String directory) throws IOException {
        this.parser = parser;
        this.directory = directory;
    }

    public byte[] getResponse() throws IOException {
        String uri = parser.getRequestURI();
            if (uri.equals("/")) {
                return getResponseString("200 OK", "");
            } else if (uri.equals("/foobar")) {
                return getResponseString("404 Not Found", "Oops: 404 Not Found");
            } else {
                return getResponseString("200 OK", "");
            }
        }

    private void getLinks() {
    }

    public byte[] getResponseString(String status, String body) {
        String response = getStatusLine(status) + getDateInfo() + getServerInfo() + getContentTypeInfo() + getBody(body);
        byte[] output = response.getBytes();
        return output;
    }

    public String getStatusLine(String status) {
        return "HTTP/1.1 " + status +  "\r\n";
    }

    public String getDateInfo() {
        java.util.Date date= new java.util.Date();
        String dateString = "Date: " + date.toString() + "\r\n";
        return dateString;
    }

    public String getServerInfo() {
        return "Server: Taryn's Java Server\r\n";
    }

    public String getContentTypeInfo() {
        return "Content-Type: text/html; charset=UTF-8\r\n\r\n";
    }

    public String getBody(String contents) {
        String bodyBegin = "<title>Taryn's Website</title>\n" +
                "</head>\n" +
                "<body>\n";
        String bodyEnd = "</body>\n" +
                "</html>";
        if (contents.equals("")) {
            File directory = new File("/Users/Taryn/8thLight/cob_spec/public/");
            File[] fileListing = directory.listFiles();
            String bodyMiddle = "Hello, World.\n" + listFiles(fileListing);
            return bodyBegin + bodyMiddle + bodyEnd;
        } else {
            return bodyBegin + "<h1>" + contents + "</h1>" + bodyEnd;
        }
    }

    public String listFiles(File[] files) {
        String fileList = "<ul>";
        for (File file : files) {
          fileList += "<li><a href=\"/" + file.getAbsolutePath() + "\">" + file.getName() + "</a></li>";
        }
        return fileList + "</ul>";
    }
}
