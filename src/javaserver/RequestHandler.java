package javaserver;

import java.io.IOException;
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
        String response;
        String uri = parser.getRequestURI();
            if (uri.equals("/")) {
                response = new RootResponse().getResponseMessage("200 OK", "root");
            } else if (uri.equals("/foobar")) {
                response = new NotFoundResponse().getResponseMessage("404 Not Found", "Boo! 404 Not Found");
            } else if (uri.equals("/file1")) {
                response = new RootResponse().getResponseMessage("200 OK", "file1");
            } else if (uri.equals("/file2")) {
                response = new RootResponse().getResponseMessage("200 OK", "file2");
            } else if (uri.equals("/image.gif")) {
                response = new ImageResponse().getResponseMessage("200 OK", "image.gif");
            } else if (uri.equals("/image.jpeg")) {
                response = new ImageResponse().getResponseMessage("200 OK", "image.jpeg");
            } else if (uri.equals("/image.png")) {
                response = new ImageResponse().getResponseMessage("200 OK", "image.png");
            } else if (uri.equals("/partial_content.txt")) {
                response = new RootResponse().getResponseMessage("200 OK", "partial_content.txt");
            } else if (uri.equals("/text-file.txt")) {
                response = new RootResponse().getResponseMessage("200 OK", "text-file.txt");
            } else {
                response = new RootResponse().getResponseMessage("200 OK", "Hmm...I should say something here.");
            }
        return response.getBytes();
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

}
