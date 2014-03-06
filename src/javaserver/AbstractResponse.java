package javaserver;

import java.io.FileNotFoundException;

/**
 * Created by Taryn on 3/4/14.
 */
public abstract class AbstractResponse {
    abstract String getBody(String condition);
    abstract String getResponseMessage(String status, String condition) throws FileNotFoundException;

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

    public String getContentTypeInfo(String contentType) {
        return "Content-Type: text/html; charset=UTF-8\r\n\r\n";
    }

}
