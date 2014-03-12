package javaserver;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Taryn on 3/4/14.
 */

public class RequestHandlerTest extends TestHelpers {
    private RequestHandler requestHandler;

    @Before
    public void setUp() throws Exception {
        setUpParserForResponseTesting();
        requestHandler = new RequestHandler(requestParser);
    }

    private void setMockRequest(String request) {
        requestParser.setRequest(request);
    }

    @Test
    public void testGetResponseForRootRequests() throws Exception {
        String[] uris = {"/", "file1", "file2", "text-file.txt"};
        for (String uri: uris) {
            requestHandler.setRootResponse(new MockRootResponse());
            setMockRequest("GET " + uri + " HTTP/1.1--break--Connection: close--break--Host: localhost:5000--break----break----break--");
            String actualOutput = responseToString(requestHandler.getResponse());
            assertEquals(actualOutput, "RootResponse getResponse() called");
        }
    }

    @Test
    public void testGetResponseForMethodNotAllowedRequests() throws Exception {
        String[] requests = {"PUT /file1", "POST /text-file.txt"};
        for (String request: requests) {
            requestHandler.setMethodNotAllowedResponse(new MockMethodNotAllowedResponse());
            setMockRequest(request + " HTTP/1.1--break--Connection: close--break--Host: localhost:5000--break----break----break--");
            String actualOutput = responseToString(requestHandler.getResponse());
            assertEquals(actualOutput, "MethodNotAllowedResponse getResponse() called");
        }
    }

    @Test
    public void testGetResponseForImageRequests() throws Exception {
        String[] files = {"image.jpeg", "image.png", "image.gif"};
        for (String file: files) {
            requestHandler.setImageResponse(new MockImageResponse());
            setMockRequest("GET /" + file + " HTTP/1.1--break--Connection: close--break--Host: localhost:5000--break----break----break--");
            String actualOutput = responseToString(requestHandler.getResponse());
            assertEquals(actualOutput, "ImageResponse getResponse() called");
        }
    }

    @Test
    public void testGetResponseForPartialResponse() throws Exception {
        requestHandler.setPartialResponse(new MockPartialResponse());
        setMockRequest("GET /partial_content.txt HTTP/1.1--break--Connection: close--break--Host: localhost:5000--break----break----break--");
        String actualOutput = responseToString(requestHandler.getResponse());
        assertEquals(actualOutput, "PartialResponse getResponse() called");
    }

    @Test
    public void testGetResponseForParameterDecodeResponse() throws Exception {
        requestHandler.setParameterDecodeResponse(new MockParameterDecodeResponse());
        setMockRequest("GET /parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff HTTP/1.1--break--Connection: close--break--Host: localhost:5000--break----break----break--");
        String actualOutput = responseToString(requestHandler.getResponse());
        assertEquals(actualOutput, "ParameterDecodeResponse getResponse() called");
    }

    @Test
    public void testGetResponseForAuthenticationResponse() throws Exception {
        requestHandler.setAuthenticationResponse(new MockAuthenticationResponse());
        setMockRequest("GET /logs HTTP/1.1--break--Authorization: Basic YWRtaW46aHVudGVyMg==--break--Connection: close--break--Host: localhost:5000--break----break----break--");
        String actualOutput = responseToString(requestHandler.getResponse());
        assertEquals(actualOutput, "AuthenticationResponse getResponse() called");
    }

    @Test
    public void testGetResponseForMethodOptionsResponse() throws Exception {
        String[] methods = {"OPTIONS", "GET", "PUT", "POST", "HEAD"};
        for (String method: methods) {
            requestHandler.setMethodOptionsResponse(new MockMethodOptionsResponse());
            setMockRequest(method + " /method_options HTTP/1.1--break--Connection: close--break--Host: localhost:5000--break----break----break--");
            String actualOutput = responseToString(requestHandler.getResponse());
            assertEquals(actualOutput, "MethodOptionsResponse getResponse() called");
        }
    }

    @Test
    public void testGetResponseForRedirectResponse() throws Exception {
        requestHandler.setRedirectResponse(new MockRedirectResponse());
        setMockRequest("GET /redirect HTTP/1.1--break--Connection: close--break--Host: localhost:5000--break----break----break--");
        String actualOutput = responseToString(requestHandler.getResponse());
        assertEquals(actualOutput, "RedirectResponse getResponse() called");
    }

    @Test
    public void testGetResponseForNotFoundResponse() throws Exception {
        requestHandler.setNotFoundResponse(new MockNotFoundResponse());
        setMockRequest("GET /foobar HTTP/1.1--break--Connection: close--break--Host: localhost:5000--break----break----break--");
        String actualOutput = responseToString(requestHandler.getResponse());
        assertEquals(actualOutput, "NotFoundResponse getResponse() called");
    }
}