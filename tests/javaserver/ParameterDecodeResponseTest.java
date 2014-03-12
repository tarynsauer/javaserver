package javaserver;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static javaserver.HTTPStatusConstants.OK;
import static org.junit.Assert.assertEquals;

/**
 * Created by Taryn on 3/11/14.
 */
public class ParameterDecodeResponseTest extends TestHelpers {
    @Before
    public void setUp() throws Exception {
        setUpParserForResponseTesting();
    }

    private void setParameterDecodeRequest() {
        requestParser.setRequest("GET /parameters?variable_1=Operators" + "" +
                "%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40" +
                "%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F" +
                "&variable_2=stuff HTTP/1.1--break--Connection: close--break--Host: localhost:5000--break----break----break--");
    }

    private byte[] getMockParameterDecodeResponse() {
        String date = new Date().toString();
        String response = "HTTP/1.1 " + OK +
                "Date: " + date +
                "Server: Taryn's Java Server" +
                "Content-Type: text/html" +
                "<html><title>Taryn's Website</title><body>" +
                "<h1>Decoded parameters:</h1>" +
                "<p>variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?</p>" +
                "<p>variable_2 = stuff</p></body>" +
                "</html>";
        return response.getBytes();
    }

    @Test
    public void testGetResponseMessageParsesAndReturnsParameters() throws Exception {
        setParameterDecodeRequest();
        byte[] output = new ParameterDecodeResponse().getResponseMessage(requestParser);
        String expectedOutput = responseToString(output);
        String actualOutput = responseToString(getMockParameterDecodeResponse());
        assertEquals(actualOutput, expectedOutput);
    }
}
