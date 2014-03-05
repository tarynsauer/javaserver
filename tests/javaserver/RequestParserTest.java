package javaserver;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;
/**
 * Created by Taryn on 3/4/14.
 */
public class RequestParserTest {
    private RequestParser requestParser;
    private String str;

    @Before
    public void setUp() throws Exception {
       str = "GET /logs HTTP/1.1Host: localhost:5000Connection: keep-aliveAccept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*";
       byte[] data = str.getBytes();
       InputStream input = new ByteArrayInputStream(data);
       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
       requestParser = new RequestParser(bufferedReader);
    }

    @Test
    public void testParseRequestReturnsHashMapOfValues() throws Exception {
        assertEquals(str, requestParser.getRequest());
    }

    @Test
    public void testGetMethodReturnsMethodString() throws Exception {
        assertEquals("GET", requestParser.getMethod());
    }

    @Test
    public void testGetRequestURIReturnsString() throws Exception {
        assertEquals("/logs", requestParser.getRequestURI());
    }
}
