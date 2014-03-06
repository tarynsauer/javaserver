package javaserver;

/**
 * Created by Taryn on 3/4/14.
 */
public class NotFoundResponse extends AbstractResponse {

    public String getResponseMessage(String status, String condition) {
        return getStatusLine(status) + getDateInfo() + getServerInfo() + getContentTypeInfo("text/html") + getBody(condition);
    }

    @Override
    public String getBody(String condition) {
        String bodyBegin = "<title>Taryn's Website</title>\n" + "</head>\n" + "<body>\n";
        String bodyMiddle = "<h1>404 Not Found</h1>";
        return bodyBegin + bodyMiddle + "</body>\n" + "</html>";
    }
}
