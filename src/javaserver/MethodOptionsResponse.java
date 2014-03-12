package javaserver;

import java.io.IOException;
import static javaserver.HTTPStatusConstants.OK;
/**
 * Created by Taryn on 3/6/14.
 */
public class MethodOptionsResponse extends AbstractResponse {

    @Override
    byte[] getResponseMessage(RequestParser parser) throws IOException {
        String response = getStatusLine(OK) + getDateInfo() + getServerInfo() + getResponseHeaderAllows() +
                getContentTypeInfo(parser) + getBody();
        return response.getBytes();
    }

    private String getResponseHeaderAllows() {
        return "Allow: GET,HEAD,POST,OPTIONS,PUT\r\n";
    }

    private String getBody() {
        return bodyBegin() + "<h1>These are your options</h1>" + bodyEnd();
    }
}
