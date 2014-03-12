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
       str = "GET /logs?first_name=John&last_name=Doe&action=Submit HTTP/1.1\r\nHost: localhost:5000\r\nConnection: true\r\nAuthorization: Basic 1234==\r\nRange: bytes=0-4Compile";
       byte[] data = str.getBytes();
       InputStream input = new ByteArrayInputStream(data);
       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
       requestParser = new RequestParser(bufferedReader);
    }

    @Test
    public void testParseRequestReturnsParsedString() throws Exception {
        assertEquals("GET /logs?first_name=John&last_name=Doe&action=Submit HTTP/1.1--break--Host: localhost:5000--break--Connection: true--break--Authorization: Basic 1234==--break--Range: bytes=0-4Compile--break----break--", requestParser.getRequest());
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
    public void testGetRequestedFileName() throws Exception {
        String test = "GET /filename.txt?first_name=John&last_name=Doe&action=Submit HTTP/1.1Host: localhost:5000Connection: trueAuthorization: Basic 1234==Range: bytes=0-4Compile";
        byte[] data = test.getBytes();
        InputStream input = new ByteArrayInputStream(data);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        RequestParser parser = new RequestParser(bufferedReader);
        assertEquals("filename.txt", parser.getRequestedFileName());
    }

    @Test
    public void testGetHeadersReturnsHashOfHeaders() throws Exception {
        Map<String,String> expectedResult= new HashMap<String, String>();
        expectedResult.put("Range", "bytes=0-4Compile");
        expectedResult.put("Authorization", "Basic 1234==");
        expectedResult.put("Host", "localhost:5000");
        expectedResult.put("Connection", "true");
        assertEquals(expectedResult, requestParser.getHeaders());
    }

    @Test
    public void testGetVariables() throws Exception {
        String test = "";
        byte[] data = test.getBytes();
        InputStream input = new ByteArrayInputStream(data);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        RequestParser parser = new RequestParser(bufferedReader);
        parser.setRequest("POST /form HTTP/1.1--break--Connection: close--break--Host: localhost:5000--break--Content-Length: 10--break--Content-Type: application/x-www-form-urlencoded--break----break----break--data=cosby");
        Map<String,String> expectedResult= new HashMap<String, String>();
        expectedResult.put("data", "cosby");
        assertEquals(expectedResult, parser.getVariables());
    }


    @Test
    public void testGetVariableValue() throws Exception {
        String test = "";
        byte[] data = test.getBytes();
        InputStream input = new ByteArrayInputStream(data);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        RequestParser parser = new RequestParser(bufferedReader);
        parser.setRequest("POST /form HTTP/1.1--break--Connection: close--break--Host: localhost:5000--break--Content-Length: 10--break--Content-Type: application/x-www-form-urlencoded--break----break----break--data=cosby");
        assertEquals("cosby", parser.getVariableValue("data"));
    }

    @Test
    public void testGetHeaderValue() throws Exception {
        String test = "";
        byte[] data = test.getBytes();
        InputStream input = new ByteArrayInputStream(data);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        RequestParser parser = new RequestParser(bufferedReader);
        parser.setRequest("POST /form HTTP/1.1--break--Connection: close--break--Host: localhost:5000--break--Content-Length: 10--break--Content-Type: application/x-www-form-urlencoded--break--Range: bytes=0-4--break----break----break--data=cosby");
        assertEquals("bytes=0-4", parser.getHeaderValue("Range"));
    }
}
