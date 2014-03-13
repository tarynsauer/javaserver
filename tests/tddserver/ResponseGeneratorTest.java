package tddserver;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by Taryn on 3/13/14.
 */
public class ResponseGeneratorTest {
    private ResponseGenerator generator;

    @Before
    public void setUp() throws Exception {
        setRequestString("PUT /form HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\nContent-Length: 15\r\nContent-Type: application/x-www-form-urlencoded\r\n\r\n\r\ndata=heathcliff");
    }

    private void setRequestString(String str) throws IOException {
        byte[] data = str.getBytes();
        InputStream input = new ByteArrayInputStream(data);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        RequestParser parser = new RequestParser(bufferedReader);
        generator = new ResponseGenerator(parser);
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
}
