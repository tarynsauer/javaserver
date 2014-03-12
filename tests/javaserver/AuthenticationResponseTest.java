package javaserver;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import static javaserver.HTTPStatusConstants.UNAUTHORIZED;
import static javaserver.HTTPStatusConstants.OK;
import static org.junit.Assert.assertEquals;

/**
 * Created by Taryn on 3/11/14.
 */
public class AuthenticationResponseTest extends TestHelpers {

    @Before
    public void setUp() throws Exception {
        setUpParserForResponseTesting();
    }

    private void setMockLogsRequest(String authHeader) {
        requestParser.setRequest("GET /logs HTTP/1.1--break--" + authHeader + "Connection: close--break--" + "Host: localhost:5000--break----break----break--");
    }

    private byte[] getMockLogsSuccessfulResponse() {
        String date = new Date().toString();
        String response = "HTTP/1.1 " + OK +
        "Date: " + date +
        "Server: Taryn's Java Server" +
        "Content-Type: text/html" +
         "<title>Taryn's Website</title><html><body>" +
         "<h1>Logs</h1><p>GET /log HTTP/1.1</p><p>PUT /these HTTP/1.1</p><p>HEAD /requests HTTP/1.1</p></body>" +
        "</html>";
        return response.getBytes();
    }

    private byte[] getMockLogsUnsuccessfulResponse() {
        String date = new Date().toString();
        String response = "HTTP/1.1 " + UNAUTHORIZED +
                "Date: " + date +
                "WWW-Authenticate: Basic realm=\"Authentication required for Logs\"" +
                "Server: Taryn's Java Server" +
                "Content-Type: text/html" +
                "<title>Taryn's Website</title><html><body>" +
                "<h1>Authentication required</h1></body>" +
                "</html>";
        return response.getBytes();
    }

    @Test
    public void testAuthenticationResponseReturnsAuthorizationUnsuccessfulResponse() throws Exception {
        setMockLogsRequest("");
        byte[] output = new AuthenticationResponse().getResponseMessage(requestParser);
        String expectedOutput = responseToString(output);
        String actualOutput = responseToString(getMockLogsUnsuccessfulResponse());
        assertEquals(actualOutput, expectedOutput);
    }

    @Test
    public void testAuthenticationResponseReturnsAuthorizationSuccessfulResponse() throws Exception {
      setMockLogsRequest("Authorization: Basic YWRtaW46aHVudGVyMg==--break--");
      byte[] output = new AuthenticationResponse().getResponseMessage(requestParser);
      String expectedOutput = responseToString(output);
      String actualOutput = responseToString(getMockLogsSuccessfulResponse());
      assertEquals(actualOutput, expectedOutput);
    }

}