package tddserver;

import java.io.IOException;

/**
 * Created by Taryn on 3/12/14.
 */
public class ResponseGenerator {
    private RequestParser parser;
    private RequestManager manager;
    private BodyGenerator bodyGenerator;

    public ResponseGenerator(RequestParser parser) {
        this.parser = parser;
        this.manager = new RequestManager(parser);
        this.bodyGenerator = new BodyGenerator(parser);
    }

    public byte[] getResponseMessage(RequestParser parser) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(displayStatus(manager.getStatus()));
        builder.append(displayDate());
        builder.append(displayServer());
        builder.append(displayContentType());

        return bodyGenerator.addBodyToResponse(builder, manager.getContents());
    }

    private static String displayStatus(String status) {
        return "HTTP/1.1 " + status +  "\r\n";
    }

    private String displayDate() {
        java.util.Date date= new java.util.Date();
        return "Date: " + date.toString() + "\r\n";
    }

    private String displayServer() {
        return "Server: Taryn's Java Server\r\n";
    }

    private String displayContentType() throws IOException {
        return "Content-Type: " + manager.getContentType() + "\r\n\r\n";
    }

}
