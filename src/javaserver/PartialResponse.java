package javaserver;

import java.io.IOException;

import static javaserver.HTTPStatusConstants.PARTIAL_RESPONSE;
import static javaserver.JavaserverConstants.DIRECTORY_PATH;
/**
 * Created by Taryn on 3/7/14.
 */
public class PartialResponse extends RootResponse {

    public byte[] getResponseMessage(RequestParser parser) throws IOException {
        String response = getStatusLine(PARTIAL_RESPONSE) + getDateInfo() + getServerInfo() +
                getPartialContent(parser) + getContentTypeInfo("text/html") + getPartialResponse(parser);
        return response.getBytes();
    }

    public String getPartialResponse(RequestParser parser) throws IOException {
        String uri = parser.getRequestedFileName();
        return bodyBegin() + getFileContents(DIRECTORY_PATH + uri) + bodyEnd();
    }

    private String getPartialContent(RequestParser parser) {
        return "Content-Range: bytes 0-4/*\r\nContent-Length: 4\r\n";
    }
}
