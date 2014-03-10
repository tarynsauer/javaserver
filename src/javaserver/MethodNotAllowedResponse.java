package javaserver;

import java.io.IOException;

/**
 * Created by Taryn on 3/7/14.
 */
public class MethodNotAllowedResponse extends AbstractResponse {

    @Override
    byte[] getResponseMessage(RequestParser parser) throws IOException {
        String response = getStatusLine("405 Method Not Allowed") + getDateInfo() + getServerInfo() + getContentTypeInfo("text/html");
        return response.getBytes();
    }
}
