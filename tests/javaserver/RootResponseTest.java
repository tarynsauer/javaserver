package javaserver;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static javaserver.HTTPStatusConstants.OK;
import static javaserver.JavaserverConstants.DIRECTORY_PATH;
import static org.junit.Assert.assertEquals;

/**
 * Created by Taryn on 3/11/14.
 */
public class RootResponseTest extends TestHelpers {
    @Before
    public void setUp() throws Exception {
        setUpParserForResponseTesting();
    }

    private void setMockFileGetRequest(String fileName) {
        requestParser.setRequest("GET /" + fileName + " HTTP/1.1--break--Connection: close--break--Host: localhost:5000--break----break----break--\n" +
                "New thread starting on port 5000");
    }

    private byte[] getMockRootResponse(String content) {
        String date = new Date().toString();
        String response = "HTTP/1.1 " + OK +
                "Date: " + date +
                "Server: Taryn's Java Server" +
                "Content-Type: text/html" +
                "<html><title>Taryn's Website</title><body>" +
                content + "</body></html>";
        return response.getBytes();
    }

    private String getRootContents() {
        return "<h1>Hey, there!</h1>" +
                "<li><a href='file1'>file1</a></li>" +
                "<li><a href='file2'>file2</a></li>" +
                "<li><a href='image.gif'>image.gif</a></li>" +
                "<li><a href='image.jpeg'>image.jpeg</a></li>" +
                "<li><a href='image.png'>image.png</a></li>" +
                "<li><a href='partial_content.txt'>partial_content.txt</a></li>" +
                "<li><a href='text-file.txt'>text-file.txt</a></li>";
    }

    @Test
    public void testGetFile1ResponseMessage() throws Exception {
        setMockFileGetRequest("file1");
        byte[] output = new RootResponse().getResponseMessage(requestParser);
        String actualOutput = responseToString(output);
        String expectedOutput = responseToString(getMockRootResponse("file1 contents"));
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testGetFile2ResponseMessage() throws Exception {
        setMockFileGetRequest("file2");
        byte[] output = new RootResponse().getResponseMessage(requestParser);
        String actualOutput = responseToString(output);
        String expectedOutput = responseToString(getMockRootResponse("file2 contents"));
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testGetTextFileResponseMessage() throws Exception {
        setMockFileGetRequest("text-file.txt");
        byte[] output = new RootResponse().getResponseMessage(requestParser);
        String actualOutput = responseToString(output);
        String expectedOutput = responseToString(getMockRootResponse("file1 contents"));
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testGetTextRootResponseMessage() throws Exception {
        setMockFileGetRequest("");
        byte[] output = new RootResponse().getResponseMessage(requestParser);
        String actualOutput = responseToString(output);
        String expectedOutput = responseToString(getMockRootResponse(getRootContents()));
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testGetFileContents() throws Exception {
        String contents = new RootResponse().getFileContents(DIRECTORY_PATH + "file1");
        assertEquals("file1 contents", contents);
    }
}
