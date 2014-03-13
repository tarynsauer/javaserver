package tddserver;

/**
 * Created by Taryn on 3/12/14.
 */
public class RequestManager {
    private RequestParser parser;

    public RequestManager(RequestParser parser) {
        this.parser = parser;
    }

    public String getContentType() {
        String ext = parser.getFileExtension();
        if (ext.equals(".txt")) {
            return "text/plain";
        } else if (ext.equals(".jpg") || ext.equals(".jpeg")) {
            return "image/jpeg";
        } else if (ext.equals(".gif")) {
            return "image/gif";
        } else if (ext.equals(".png")) {
            return "image/png";
        } else {
            return "text/html";
        }
    }

    public String getStatus() {
        return null;
    }

    public String getContents() {
        return null;
    }
}
