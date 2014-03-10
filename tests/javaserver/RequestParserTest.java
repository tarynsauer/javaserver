package javaserver;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
/**
 * Created by Taryn on 3/4/14.
 */
public class RequestParserTest {
    private RequestParser requestParser;
    private String str;

    @Before
    public void setUp() throws Exception {
       str = "GET /logs?first_name=John&last_name=Doe&action=Submit HTTP/1.1Host: localhost:5000Connection: Authorization: Basic 1234==Range: bytes=0-4Compile";
       byte[] data = str.getBytes();
       InputStream input = new ByteArrayInputStream(data);
       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
       requestParser = new RequestParser(bufferedReader);
    }

    @Test
    public void testParseRequestReturnsParsedString() throws Exception {
        assertEquals(str, requestParser.getRequest());
    }

    @Test
    public void testGetMethodReturnsMethodString() throws Exception {
        assertEquals("GET", requestParser.getMethod());
    }

    @Test
    public void testGetQueryString() throws Exception {
        assertEquals("first_name=John&last_name=Doe&action=Submit", requestParser.getQueryString());
    }

    @Test
    public void testGetParameters() throws Exception {
        Map<String,String> expectedResult= new HashMap<String, String>();
        expectedResult.put("first_name", "John");
        expectedResult.put("last_name", "Doe");
        expectedResult.put("action", "Submit");
        assertEquals(expectedResult, requestParser.getParameters());
    }

    @Test
    public void testGetRequestURIReturnsString() throws Exception {
        assertEquals("/logs", requestParser.getRequestURI());
    }

    @Test
    public void testGetRangeReturnsString() throws Exception {
        assertEquals("0-4", requestParser.getRange());
    }
}
