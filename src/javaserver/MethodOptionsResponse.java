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
    byte[] getResponseMessage(RequestParser parser) throws IOException {
        String response = getStatusLine("200 OK") + getDateInfo() + getServerInfo() + getResponseHeaderAllows() + getContentTypeInfo("text/html") + getBody("These are your options:");
        return response.getBytes();
    }

    private String getResponseHeaderAllows() {
        return "Allow: GET,HEAD,POST,OPTIONS,PUT\r\n";
    }
}
