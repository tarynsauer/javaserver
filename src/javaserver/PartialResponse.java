package javaserver;

import java.io.IOException;

/**
 * Created by Taryn on 3/7/14.
 */
public class PartialResponse extends RootResponse {
    private String directoryPath = "/Users/Taryn/8thLight/cob_spec/public";

    public byte[] getResponseMessage(RequestParser parser) throws IOException {
        System.out.println(parser.getRequest());
        String response = getStatusLine("206 Partial Response") + getDateInfo() + getServerInfo() + getPartialContent(parser) + getContentTypeInfo("text/html") + getPartialResponse(parser);
        return response.getBytes();
    }

    public String getPartialResponse(RequestParser parser) throws IOException {
        String bodyBegin = "<title>Taryn's Website</title>\n" + "</head>\n" + "<body>\n";
        String bodyEnd = "</body>\n" + "</html>";
        String uri = parser.getRequestURI();
        return bodyBegin + getFileContents(directoryPath + uri) + bodyEnd;
    }

    private String getPartialContent(RequestParser parser) {
        return "Content-Range: bytes 0-4/*\r\nContent-Length: 4\r\n";
//        return "Content-Range: bytes " + parser.getRange() + "/*\r\n";
    }
}
