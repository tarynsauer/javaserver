package javaserver;

import java.io.IOException;

/**
 * Created by Taryn on 3/7/14.
 */
public class PartialResponse extends RootResponse {
    private String directoryPath = "/Users/Taryn/8thLight/cob_spec/public";

    public byte[] getResponseMessage(RequestParser parser) throws IOException {
        String response = getStatusLine("200 OK") + getDateInfo() + getServerInfo() + getPartialContent() + getContentTypeInfo("text/html") + getPartialResponse(parser);
        return response.getBytes();
    }

    public String getPartialResponse(RequestParser parser) throws IOException {
        String bodyBegin = "<title>Taryn's Website</title>\n" + "</head>\n" + "<body>\n";
        String bodyEnd = "</body>\n" + "</html>";
        String uri = parser.getRequestURI();
        return bodyBegin + getFileContents(directoryPath + uri) + bodyEnd;
    }

    private String getPartialContent() {
        return "Range: bytes=0-999\r\n" ;// + "Content-Length: 1000\r\n" + "Content-Range: bytes 0-999/3980\r\n";
    }
}
