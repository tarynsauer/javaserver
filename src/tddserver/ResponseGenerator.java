package tddserver;

import java.io.IOException;
import static javaserver.HTTPStatusConstants.*;
import static javaserver.JavaserverConstants.*;
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
        builder.append(displayResponseHeaders());
        builder.append(displayContentType());

        return bodyGenerator.addBodyToResponse(builder, manager.getContents());
    }

    public String displayResponseHeaders() {
        if (manager.getStatus().equals(UNAUTHORIZED)) {
            return "WWW-Authenticate: Basic realm=\"Authentication required for Logs\"\r\n";
        } else if (manager.getStatus().equals(MOVED_PERMANENTLY)) {
            return "Location: http://localhost:" + Integer.toString(DEFAULT_PORT) + manager.getRedirect() +"\r\n";
        } else if (manager.methodOptionsRequired()) {
            return "Allow: " + manager.getAllowedOptionsList() + "\r\n";
        } else {
            return "";
        }
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