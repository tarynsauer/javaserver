package tddserver;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static javaserver.HTTPStatusConstants.*;
/**
 * Created by Taryn on 3/13/14.
 */
public class RequestManagerTest {
    private RequestManager manager;

    @Before
    public void setUp() throws Exception {
        setRequestString("PUT /form HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\nContent-Length: 15\r\nContent-Type: application/x-www-form-urlencoded\r\n\r\n\r\ndata=heathcliff");
    }

    private void setRequestString(String str) throws IOException {
        byte[] data = str.getBytes();
        InputStream input = new ByteArrayInputStream(data);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        RequestParser parser = new RequestParser(bufferedReader);
        manager = new RequestManager(parser);
    }

    @Test
    public void testGetStatusReturnsRedirect() throws Exception {
        assertEquals(manager.getStatus(), OK);
    }

    @Test
    public void testGetStatusReturnsOK() throws Exception {
        setRequestString("GET /redirect HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals(manager.getStatus(), MOVED_PERMANENTLY);
    }

    @Test
    public void testGetStatusReturnsMETHODNOTALLOWED() throws Exception {
        setRequestString("PUT /file1 HTTP/1.1/\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals(manager.getStatus(), METHOD_NOT_ALLOWED);
    }

    @Test
    public void testGetStatusReturnsUNAUTHORIZED() throws Exception {
        setRequestString("GET /logs HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals(manager.getStatus(), UNAUTHORIZED);
    }

    @Test
    public void testGetStatusReturnsNOTFOUND() throws Exception {
        setRequestString("GET /foobar HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals(manager.getStatus(), NOT_FOUND);
    }


    @Test
    public void testGetStatusReturnsPARTIALRESPONSE() throws Exception {
        setRequestString("GET /partial_content.txt HTTP/1.1\r\nRange: bytes=0-4\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals(manager.getStatus(), PARTIAL_RESPONSE);
    }

    @Test
    public void testUriFoundReturnsFalseWhenUriIsInvalid() throws Exception {
        setRequestString("GET /foobar HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertFalse(manager.uriFound());
    }

    @Test
    public void testUriFoundReturnsTrueWhenUriIsValid() throws Exception {
        setRequestString("GET / HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertTrue(manager.uriFound());
    }
}