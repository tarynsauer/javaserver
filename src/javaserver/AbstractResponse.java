package javaserver;

import java.io.IOException;
/**
 * Created by Taryn on 3/4/14.
 */
public abstract class AbstractResponse {

    abstract byte[] getResponseMessage(RequestParser parser) throws IOException;

    public static String getStatusLine(String status) {
        return "HTTP/1.1 " + status +  "\r\n";
    }

    public String getDateInfo() {
        java.util.Date date= new java.util.Date();
        return "Date: " + date.toString() + "\r\n";
    }

    public String getServerInfo() {
        return "Server: Taryn's Java Server\r\n";
    }

    public String getContentTypeInfo(RequestParser parser) throws IOException {
        return "Content-Type: " + getContentType(parser.getFileExtension()) + "\r\n\r\n";
    }

    public String bodyBegin() {
        return "<html><title>Taryn's Website</title><body>";
    }

    public String bodyEnd() {
        return "</body></html>";
    }

    private String getContentType(String fileExtension) {
        if (fileExtension.equals(".txt")) {
            return "text/plain";
        } else if (fileExtension.equals(".jpg") || fileExtension.equals(".jpeg")) {
            return "image/jpeg";
        } else if (fileExtension.equals(".gif")) {
            return "image/gif";
        } else if (fileExtension.equals(".png")) {
            return "image/png";
        } else {
            return "text/html";
        }
    }


}
