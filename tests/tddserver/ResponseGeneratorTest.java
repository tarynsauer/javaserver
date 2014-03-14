package tddserver;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by Taryn on 3/13/14.
 */
public class ResponseGeneratorTest extends TestHelpers {
    private ResponseGenerator generator;

    private void setRequestString(String str) throws IOException {
        byte[] data = str.getBytes();
        InputStream input = new ByteArrayInputStream(data);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        RequestParser parser = new RequestParser(bufferedReader);
        generator = new ResponseGenerator(parser);
    }

    @Before
    public void setUp() throws Exception {
        setRequestString("PUT /form HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\nContent-Length: 15\r\nContent-Type: application/x-www-form-urlencoded\r\n\r\n\r\ndata=heathcliff");
    }

    @Test
    public void testDisplayResponseHeadersReturnsAuthenticateHeader() throws Exception {
        setRequestString("GET /logs HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals(generator.displayResponseHeaders(), "WWW-Authenticate: Basic realm=\"Authentication required for Logs\"\r\n");
    }

    @Test
    public void testDisplayResponseHeadersReturnsLocationHeader() throws Exception {
        setRequestString("GET /redirect HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals(generator.displayResponseHeaders(), "Location: http://localhost:5000/\r\n");
    }

    @Test
    public void testDisplayResponseHeadersReturnsMethodOptionsHeader() throws Exception {
        setRequestString("OPTIONS /method_options HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals(generator.displayResponseHeaders(), "Allow: GET,HEAD,POST,OPTIONS,PUT\r\n");
    }

    private String expectedRootResponse() {
        return "HTTP/1.1 200 OK" +
        "Date: " + getDate() +
        "Server: Taryn's Java Server" +
        "Content-Type: text/html" +
        "<html><title>Taryn's Website</title><body><h1>Hey, there!</h1>" +
        "<li><a href='file1'>file1</a></li><li><a href='file2'>file2</a></li>" +
        "<li><a href='image.gif'>image.gif</a></li>" +
        "<li><a href='image.jpeg'>image.jpeg</a></li>" +
        "<li><a href='image.png'>image.png</a></li>" +
        "<li><a href='partial_content.txt'>partial_content.txt</a></li>" +
        "<li><a href='text-file.txt'>text-file.txt</a></li></body></html>";
    }

    @Test
    public void testGetResponseMessage() throws Exception {
        setRequestString("GET / HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        String actual = new String(generator.getResponseMessage(requestParser), "UTF-8");
        assertEquals(normalizeString(actual), normalizeString(expectedRootResponse()));
    }
}