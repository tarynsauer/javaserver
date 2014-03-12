package javaserver;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static javaserver.HTTPStatusConstants.MOVED_PERMANENTLY;
import static javaserver.JavaserverConstants.DEFAULT_PORT;
import static org.junit.Assert.assertEquals;

/**
 * Created by Taryn on 3/11/14.
 */
public class RedirectResponseTest extends TestHelpers {
    @Before
    public void setUp() throws Exception {
        setUpParserForResponseTesting();
    }

    private void setMockRedirectRequest() {
        requestParser.setRequest("GET /redirect HTTP/1.1--break--Connection: close--break--Host: localhost:5000--break----break----break--");
    }

    private byte[] getMockRedirectResponse() {
        String date = new Date().toString();
        String response = "HTTP/1.1 " + MOVED_PERMANENTLY +
                "Location: http://localhost:" + Integer.toString(DEFAULT_PORT) + "/" +
                "Date: " + date +
                "Server: Taryn's Java Server" +
                "Content-Type: text/html";
        return response.getBytes();
    }

    @Test
    public void testGetResponseMessage() throws Exception {
        setMockRedirectRequest();
        byte[] output = new RedirectResponse().getResponseMessage(requestParser);
        String expectedOutput = responseToString(output);
        String actualOutput = responseToString(getMockRedirectResponse());
        assertEquals(actualOutput, expectedOutput);
    }
}
