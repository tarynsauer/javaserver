package javaserver;

import java.io.IOException;

/**
 * Created by Taryn on 3/7/14.
 */
public class MethodNotAllowedResponse extends AbstractResponse {
    @Override
    String getBody(String condition) {
        return null;
    }

    @Override
    byte[] getResponseMessage(RequestParser parser) throws IOException {
        String response = getStatusLine("405 Method Not Allowed") + getDateInfo() + getServerInfo() + getContentTypeInfo("text/html") + getBody("none");
        return response.getBytes();
    }
}
