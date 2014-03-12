package javaserver;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static javaserver.HTTPStatusConstants.NOT_FOUND;
import static org.junit.Assert.assertEquals;

/**
 * Created by Taryn on 3/11/14.
 */
public class NotFoundResponseTest extends TestHelpers {
    @Before
    public void setUp() throws Exception {
        setUpParserForResponseTesting();
    }

    private void setMockNotFoundRequest() {
        requestParser.setRequest("GET /foobar HTTP/1.1--break--Connection: close--break--Host: localhost:5000--break----break----break--");
    }

    private byte[] getMockNotFoundResponse() {
        String date = new Date().toString();
        String response = "HTTP/1.1 " + NOT_FOUND +
                "Date: " + date +
                "Server: Taryn's Java Server" +
                "Content-Type: text/html" +
                "<html><title>Taryn's Website</title><body>" +
                "<h1>Hmm...you're looking for /foobar.</h1><p>No dice.</p></body></html>";
        return response.getBytes();
    }

    @Test
    public void testGetResponseMessage() throws Exception {
        setMockNotFoundRequest();
        byte[] output = new NotFoundResponse().getResponseMessage(requestParser);
        String expectedOutput = responseToString(output);
        String actualOutput = responseToString(getMockNotFoundResponse());
        assertEquals(actualOutput, expectedOutput);
    }

}
