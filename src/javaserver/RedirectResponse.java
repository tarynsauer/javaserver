package javaserver;

import java.io.IOException;
import static javaserver.HTTPStatusConstants.MOVED_PERMANENTLY;
import static javaserver.JavaserverConstants.DEFAULT_PORT;
/**
 * Created by Taryn on 3/8/14.
 */
public class RedirectResponse extends AbstractResponse {

    @Override
    byte[] getResponseMessage(RequestParser parser) throws IOException {
        String response = getStatusLine(MOVED_PERMANENTLY) + getLocation("/") + getDateInfo() + getServerInfo() + getContentTypeInfo("text/html");
        return response.getBytes();
    }

    String getLocation(String uri) {
        return "Location: http://localhost:" + Integer.toString(DEFAULT_PORT) + uri +"\r\n";
    }
}
