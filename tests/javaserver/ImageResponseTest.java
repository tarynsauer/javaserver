package javaserver;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertTrue;
import static javaserver.HTTPStatusConstants.OK;
/**
 * Created by Taryn on 3/11/14.
 */
public class ImageResponseTest extends TestHelpers {
    @Before
    public void setUp() throws Exception {
        setUpParserForResponseTesting();
    }

    private void setMockImageRequest(String fileName) {
        requestParser.setRequest("GET /" + fileName + " HTTP/1.1--break--Connection: close--break--Host: localhost:5000--break----break----break--");
    }

    private byte[] getMockImageResponse(String fileExtension) {
        String date = new Date().toString();
        String response = "HTTP/1.1 " + OK +
        "Date: " + date +
        "Server: Taryn's Java Server" +
        "Content-Type: image/" + fileExtension;
        return response.getBytes();
    }

    @Test
    public void testGetResponseMessageForJPEG() throws Exception {
        setMockImageRequest("image.jpeg");
        byte[] output = new ImageResponse().getResponseMessage(requestParser);
        String expectedOutput = responseToString(output);
        String headers = responseToString(getMockImageResponse("jpeg"));
        assertTrue(expectedOutput.contains(headers));
    }

    @Test
    public void testGetResponseMessageForGIF() throws Exception {
        setMockImageRequest("image.gif");
        byte[] output = new ImageResponse().getResponseMessage(requestParser);
        String expectedOutput = responseToString(output);
        String headers = responseToString(getMockImageResponse("gif"));
        assertTrue(expectedOutput.contains(headers));
    }

    @Test
    public void testGetResponseMessageForPNG() throws Exception {
        setMockImageRequest("image.png");
        byte[] output = new ImageResponse().getResponseMessage(requestParser);
        String expectedOutput = responseToString(output);
        String headers = responseToString(getMockImageResponse("png"));
        assertTrue(expectedOutput.contains(headers));
    }

    @Test
    public void testGetResponseLengthForJPEG() throws Exception {
        setMockImageRequest("image.jpeg");
        byte[] output = new ImageResponse().getResponseMessage(requestParser);
        assertTrue(output.length > 38000);
    }

    @Test
    public void testGetResponseLengthForGIF() throws Exception {
        setMockImageRequest("image.gif");
        byte[] output = new ImageResponse().getResponseMessage(requestParser);
        assertTrue(output.length > 30000);
    }

    @Test
    public void testGetResponseLengthForPNG() throws Exception {
        setMockImageRequest("image.png");
        byte[] output = new ImageResponse().getResponseMessage(requestParser);
        assertTrue(output.length > 240000);
    }
}
