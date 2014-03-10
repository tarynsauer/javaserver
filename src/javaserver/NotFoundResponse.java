package javaserver;

/**
 * Created by Taryn on 3/4/14.
 */
public class NotFoundResponse extends AbstractResponse {

    public byte[] getResponseMessage(RequestParser parser) {
        String response = getStatusLine("404 Not Found") + getDateInfo() + getServerInfo() + getContentTypeInfo("text/html") + getBody("404 Not Found");
        return response.getBytes();
    }

    public String getBody(String condition) {
        String bodyBegin = "<title>Taryn's Website</title>\n" + "</head>\n" + "<body>\n";
        String bodyMiddle = "<h1>" + condition + "</h1>";
        return bodyBegin + bodyMiddle + "</body>\n" + "</html>";
    }
}
