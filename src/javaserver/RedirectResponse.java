package javaserver;

import java.io.IOException;

/**
 * Created by Taryn on 3/8/14.
 */
public class RedirectResponse extends AbstractResponse {

    @Override
    byte[] getResponseMessage(RequestParser parser) throws IOException {
        String response = getStatusLine("302 Moved Permanently") + getLocation("/") + getServerInfo() + getContentTypeInfo("text/html");
        return response.getBytes();
    }

    String getLocation(String uri) {
        return "Location: http://localhost:5000/\r\n";
    }
}
