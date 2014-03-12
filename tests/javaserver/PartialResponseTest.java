package javaserver;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static javaserver.HTTPStatusConstants.PARTIAL_RESPONSE;
import static org.junit.Assert.assertEquals;

/**
 * Created by Taryn on 3/11/14.
 */
public class PartialResponseTest extends TestHelpers{


    @Before
    public void setUp() throws Exception {
        setUpParserForResponseTesting();
    }

    private void setMockPartialResponseRequest() {
        requestParser.setRequest("GET /partial_content.txt HTTP/1.1--break--Range: bytes=0-4--break--Connection: close--break--Host: localhost:5000--break----break----break--");
    }

    private byte[] getMockPartialResponse() {
        String date = new Date().toString();
        String response = "HTTP/1.1 " + PARTIAL_RESPONSE +
                "Date: " + date +
                "Server: Taryn's Java Server" +
                "Content-Type: text/plainThis";
        return response.getBytes();
    }

    @Test
    public void testGetResponseMessageToAGETRequest() throws Exception {
        setMockPartialResponseRequest();
        byte[] output = new PartialResponse().getResponseMessage(requestParser);
        String expectedOutput = responseToString(output);
        String actualOutput = responseToString(getMockPartialResponse());
        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void testGetBeginRange() throws Exception {
        setMockPartialResponseRequest();
        int expectedValue = PartialResponse.getBeginRange(requestParser);
        assertEquals(expectedValue, 0);
    }

    @Test
    public void testGetEndRange() throws Exception {
        setMockPartialResponseRequest();
        int expectedValue = PartialResponse.getEndRange(requestParser);
        assertEquals(expectedValue, 4);
    }
}
