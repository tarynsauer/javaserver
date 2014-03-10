package javaserver;

import java.io.IOException;
import static javaserver.HTTPStatusConstants.METHOD_NOT_ALLOWED;
/**
 * Created by Taryn on 3/7/14.
 */
public class MethodNotAllowedResponse extends AbstractResponse {

    @Override
    byte[] getResponseMessage(RequestParser parser) throws IOException {
        String response = getStatusLine(METHOD_NOT_ALLOWED) + getDateInfo() + getServerInfo() + getContentTypeInfo("text/html");
        return response.getBytes();
    }
}
