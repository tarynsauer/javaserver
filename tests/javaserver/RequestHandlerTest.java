package javaserver;
import javaserver.mocks.MockImageResponse;
import javaserver.mocks.MockRootResponse;
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

//    @Test
//    public void testGetResponseForImageRequests() throws Exception {
//        String[] files = {"image.jpeg", "image.png", "image.gif"};
//        for (String file: files) {
//            requestHandler.setImageResponse(new MockImageResponse());
//            setMockRequest("GET /" + file + " HTTP/1.1--break--Connection: close--break--Host: localhost:5000--break----break----break--");
//            String actualOutput = responseToString(requestHandler.getResponse());
//            assertEquals(actualOutput, "ImageResponse getResponse() called");
//        }
//    }

}
