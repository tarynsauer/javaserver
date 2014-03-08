package javaserver;

import java.io.IOException;

/**
 * Created by Taryn on 3/8/14.
 */
public class RedirectResponse extends AbstractResponse {
    @Override
    String getBody(String condition) {
        return null;
    }

    @Override
    byte[] getResponseMessage(String status, String condition) throws IOException {
        String response = getStatusLine(status) + getLocation(condition) + getServerInfo() + getContentTypeInfo("text/html");
        return response.getBytes();
    }

    String getLocation(String condition) {
        return "Location: " + condition + "\r\n";
    }
}
