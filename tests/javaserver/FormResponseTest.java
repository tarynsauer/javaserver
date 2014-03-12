package javaserver;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static javaserver.HTTPStatusConstants.OK;

/**
 * Created by Taryn on 3/11/14.
 */
public class FormResponseTest extends TestHelpers {

    @Before
    public void setUp() throws Exception {
        setUpParserForResponseTesting();
    }

    private void setMockFormGetRequest() {
        requestParser.setRequest("GET /form HTTP/1.1--break--Connection: close--break--Host: localhost:5000--break----break----break--");
    }

    private void setMockFormPostRequest() {
        requestParser.setRequest("POST /form HTTP/1.1--break--Connection: close--break--Host: localhost:5000--break--Content-Length: 10--break--Content-Type: application/x-www-form-urlencoded--break----break----break--data=cosby");
    }

    private void setMockFormPutRequest() {
        requestParser.setRequest("PUT /form HTTP/1.1--break--Connection: close--break--Host: localhost:5000--break--Content-Length: 15--break--Content-Type: application/x-www-form-urlencoded--break----break----break--data=heathcliff");
    }

    private void setMockFormDeleteRequest() {
        requestParser.setRequest("DELETE /form HTTP/1.1--break--Connection: close--break--Host: localhost:5000--break----break----break--");
    }

    private byte[] getMockFormResponse(String dataValue) {
        String date = new Date().toString();
        String response = "HTTP/1.1 " + OK +
                "Date: " + date +
                "Server: Taryn's Java Server" +
                "Content-Type: text/html" +
                "<html><title>Taryn's Website</title><body>" +
                "<p data = " + dataValue + ">There may be a hidden name value here.</p></body>" +
                "</html>";
        return response.getBytes();
    }

    @Test
    public void testGetResponseMessageToAGETRequest() throws Exception {
        setMockFormGetRequest();
        byte[] output = new FormResponse().getResponseMessage(requestParser);
        String expectedOutput = responseToString(output);
        String actualOutput = responseToString(getMockFormResponse(""));
        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void testGetResponseMessageToAPOSTRequest() throws Exception {
        setMockFormPostRequest();
        byte[] output = new FormResponse().getResponseMessage(requestParser);
        String expectedOutput = responseToString(output);
        String actualOutput = responseToString(getMockFormResponse("cosby"));
        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void testGetResponseMessageToAPUTRequest() throws Exception {
        setMockFormPutRequest();
        byte[] output = new FormResponse().getResponseMessage(requestParser);
        String expectedOutput = responseToString(output);
        String actualOutput = responseToString(getMockFormResponse("heathcliff"));
        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void testGetResponseMessageToADELETERequest() throws Exception {
        setMockFormDeleteRequest();
        byte[] output = new FormResponse().getResponseMessage(requestParser);
        String expectedOutput = responseToString(output);
        String actualOutput = responseToString(getMockFormResponse(""));
        assertEquals(actualOutput, expectedOutput);
    }
}
