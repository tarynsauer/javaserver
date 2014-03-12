package javaserver;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static javaserver.HTTPStatusConstants.OK;
import static org.junit.Assert.assertEquals;

/**
 * Created by Taryn on 3/11/14.
 */
public class MethodOptionsResponseTest extends TestHelpers {
    @Before
    public void setUp() throws Exception {
        setUpParserForResponseTesting();
    }

    private void setMockMethodOptionsRequest(String method) {
        requestParser.setRequest(method + " /method_options HTTP/1.1--break--Connection: close--break--Host: localhost:5000--break----break----break--");
    }

    private byte[] getMockMethodOptionsResponse() {
        String date = new Date().toString();
        String response = "HTTP/1.1 " + OK +
                "Date: " + date +
                "Server: Taryn's Java Server" +
                "Allow: GET,HEAD,POST,OPTIONS,PUT" +
                "Content-Type: text/html" +
                "<title>Taryn's Website</title><html><body>" +
                "<h1>These are your options</h1></body></html>";
        return response.getBytes();
    }

    @Test
    public void testGetResponseMessageForGetRequest() throws Exception {
        String[] methods = {"GET", "PUT", "POST", "HEAD", "OPTIONS"};
        for (String method: methods) {
            setMockMethodOptionsRequest(method);
            byte[] output = new MethodOptionsResponse().getResponseMessage(requestParser);
            String expectedOutput = responseToString(output);
            String actualOutput = responseToString(getMockMethodOptionsResponse());
            assertEquals(actualOutput, expectedOutput);
        }

    }

}
