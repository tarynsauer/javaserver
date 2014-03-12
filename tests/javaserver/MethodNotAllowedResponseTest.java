package javaserver;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static javaserver.HTTPStatusConstants.METHOD_NOT_ALLOWED;
import static org.junit.Assert.assertEquals;

/**
 * Created by Taryn on 3/11/14.
 */
public class MethodNotAllowedResponseTest extends TestHelpers {
    @Before
    public void setUp() throws Exception {
        setUpParserForResponseTesting();
    }

    private void setMockMethodNotAllowedRequest(String method) {
        requestParser.setRequest(method + " /file1 HTTP/1.1--break--Connection: close--break--Host: localhost:5000--break----break----break--");
    }

    private byte[] getMockMethodNotAllowedResponse() {
        String date = new Date().toString();
        String response = "HTTP/1.1 " + METHOD_NOT_ALLOWED +
                "Date: " + date +
                "Server: Taryn's Java Server" +
                "Content-Type: text/html";
        return response.getBytes();
    }

    @Test
    public void testGetResponseMessageForRequests() throws Exception {
        String[] methods = {"POST", "PUT"};
        for (String method: methods) {
            setMockMethodNotAllowedRequest(method);
            byte[] output = new MethodNotAllowedResponse().getResponseMessage(requestParser);
            String expectedOutput = responseToString(output);
            String actualOutput = responseToString(getMockMethodNotAllowedResponse());
            assertEquals(actualOutput, expectedOutput);
        }

    }
}
