package javaserver;

import java.io.IOException;

/**
 * Created by Taryn on 3/6/14.
 */
public class MethodOptionsResponse extends AbstractResponse {
    @Override
    String getBody(String condition) {
        return "<h1>" + condition + "</h1>";
    }

    @Override
    byte[] getResponseMessage(String status, String condition) throws IOException {
        String response = getStatusLine(status) + getDateInfo() + getServerInfo() + getResponseHeaderAllows() + getContentTypeInfo("text/html") + getBody(condition);
        return response.getBytes();
    }

    private String getResponseHeaderAllows() {
        return "Allow: GET,HEAD,POST,OPTIONS,PUT\r\n";
    }
}
