package javaserver;

import java.io.IOException;
import static javaserver.HTTPStatusConstants.NOT_FOUND;
/**
 * Created by Taryn on 3/4/14.
 */
public class NotFoundResponse extends AbstractResponse {

    public byte[] getResponseMessage(RequestParser parser) throws IOException {
        String response = getStatusLine(NOT_FOUND) + getDateInfo() + getServerInfo() + getContentTypeInfo("text/html") + pageNotFoundMessage(parser.getRequestURI());
        return response.getBytes();
    }

    public String pageNotFoundMessage(String uri) {
        return bodyBegin() + "<h1>Hmm...you're looking for " + uri + ".</h1><p>No dice.</p>" + bodyEnd();
    }

}
