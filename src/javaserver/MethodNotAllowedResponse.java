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
    byte[] getResponseMessage(String status, String condition) throws IOException {
        String response = getStatusLine(status) + getDateInfo() + getServerInfo() + getContentTypeInfo("text/html") + getBody(condition);
        return response.getBytes();
    }
}
