package javaserver;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Taryn on 3/4/14.
 */
public class RequestHandler {
    private HashMap<String, String> parsedRequest;

    public RequestHandler(HashMap<String, String> parsedRequest) throws IOException {
        this.parsedRequest = parsedRequest;
    }

    public byte[] getResponse() {
//        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        String response = "HTTP/1.1 200 OK\n" +
                "<html>\n" +
                "<head>\n" +
                "  <title>An Example Page</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "  Hello World, this is a very simple HTML document.\n" +
                "</body>\n" +
                "</html>";
        System.out.println(response);
        byte[] output = response.getBytes();
        System.out.println(output.toString());
        return output;
    }
}
