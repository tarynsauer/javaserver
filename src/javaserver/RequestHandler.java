package javaserver;

import java.io.IOException;

/**
 * Created by Taryn on 3/4/14.
 */
public class RequestHandler {
    private RequestParser parser;
    private String directory;
    private FormResponse formResponse;

    public RequestHandler(RequestParser parser, String directory) throws IOException {
        this.parser = parser;
        this.directory = directory;
        this.formResponse = new FormResponse();
    }

    public byte[] getResponse() throws IOException {
        byte[] response;
        String method = parser.getMethod();
        String uri = parser.getRequestURI();
        String authentication = parser.getAuthentication();

            if (uri.equals("/")) {
                response = new RootResponse().getResponseMessage("200 OK", "root");
            } else if (uri.equals("/foobar")) {
                response = new NotFoundResponse().getResponseMessage("404 Not Found", "Boo! 404 Not Found");
            } else if (uri.equals("/file1")) {
                if (!(method.equals("GET"))) {
                    response = new MethodNotAllowedResponse().getResponseMessage("405 Method Not Allowed", "file1");
                } else {
                    response = new RootResponse().getResponseMessage("200 OK", "file1");
                }
            } else if (uri.equals("/file2")) {
                response = new RootResponse().getResponseMessage("200 OK", "file2");
            } else if (uri.equals("/image.gif")) {
                response = new ImageResponse().getResponseMessage("200 OK", "image.gif");
            } else if (uri.equals("/image.jpeg")) {
                response = new ImageResponse().getResponseMessage("200 OK", "image.jpeg");
            } else if (uri.equals("/image.png")) {
                response = new ImageResponse().getResponseMessage("206 OK", "image.png");
            } else if (uri.equals("/partial_content.txt")) {
                response = new PartialResponse().getResponseMessage("206 Partial Content", "partial_content.txt");
            } else if (uri.equals("/text-file.txt")) {
                if (!(method.equals("GET"))) {
                    response = new MethodNotAllowedResponse().getResponseMessage("405 Method Not Allowed", "text-file.txt");
                } else {
                    response = new RootResponse().getResponseMessage("200 OK", "text-file.txt");
                }
            } else if ((uri.equals("/form"))) {
                response = formResponse.getResponseMessage("200 OK", method);
            } else if (uri.startsWith("/parameters?")) {
                response = new ParameterDecodeResponse().getResponseMessage("200 OK", uri);
            } else if (uri.startsWith("/logs")) {
                response = new AuthenticationResponse().getResponseMessage("401 Unauthorized", authentication);
            } else if ((uri.equals("/these")) && method.equals("PUT")) {
                response = new RootResponse().getResponseMessage("200 OK", "PUT /these HTTP/1.1");
            } else if ((uri.equals("/requests")) && method.equals("HEAD")) {
                response = new RootResponse().getResponseMessage("200 OK", "HEAD /requests HTTP/1.1");
            } else if (uri.startsWith("/method_options")) {
                response = new MethodOptionsResponse().getResponseMessage("200 OK", "These are your options:");
            } else if (uri.startsWith("/redirect")) {
                response = new RedirectResponse().getResponseMessage("302 Moved Permanently", "/");
            } else {
                response = new RootResponse().getResponseMessage("200 OK", "Hmm...I should say something here.");
            }
        return response;
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
