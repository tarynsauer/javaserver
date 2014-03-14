package tddserver;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Taryn on 3/13/14.
 */
public class BodyGeneratorTest extends TestHelpers {
    private BodyGenerator generator;
    private StringBuilder builder;

    @Before
    public void setUp() throws Exception {
       builder = new StringBuilder();
    }

    private void setRequestString(String str) throws IOException {
        byte[] data = str.getBytes();
        InputStream input = new ByteArrayInputStream(data);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        RequestParser parser = new RequestParser(bufferedReader);
        generator = new BodyGenerator(parser);
    }

    @Test
    public void testGetContentTypeForRoot() throws IOException {
        setRequestString("GET / HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals(generator.getContentType(), "text/html");
    }

    @Test
    public void testGetContentTypeForTextFile() throws IOException {
        setRequestString("GET /partial_content.txt HTTP/1.1\r\nRange: bytes=0-4\r\nConnection: close--break--Host: localhost:5000\r\n\r\n\r\n");
        assertEquals(generator.getContentType(), "text/plain");
    }

    @Test
    public void testGetContentTypeForImageFile() throws IOException {
        setRequestString("GET /image.jpeg HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals(generator.getContentType(), "image/jpeg");
    }

//    @Test
//    public void testGetPagesReturnsAllSitePages() throws Exception {
//        assertEquals(generator.getPages().size(), 9);
//    }

    private String getRootBody() {
        return "<html><title>Taryn's Website</title><body>" +
                "<h1>Hey, there!</h1><li><a href='file1'>file1</a></li>" +
                "<li><a href='file2'>file2</a></li>" +
                "<li><a href='image.gif'>image.gif</a></li>" +
                "<li><a href='image.jpeg'>image.jpeg</a></li>" +
                "<li><a href='image.png'>image.png</a></li>" +
                "<li><a href='partial_content.txt'>partial_content.txt</a></li>" +
                "<li><a href='text-file.txt'>text-file.txt</a></li></body></html>";
    }

    @Test
    public void testAddRootPageBodyToResponse() throws Exception {
        setRequestString("GET / HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        String actual = new String(generator.addBodyToResponse(builder), "UTF-8");
        assertEquals(normalizeString(actual), normalizeString(getRootBody()));
    }

    @Test
    public void testAddLogsPageBodyToResponse() throws Exception {
        setRequestString("GET /logs HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        String expected = "<html><title>Taryn's Website</title><body><h1>Authentication required</h1>GET /log HTTP/1.1 PUT /these HTTP/1.1 HEAD /requests HTTP/1.1</body></html>";
        String actual = new String(generator.addBodyToResponse(builder), "UTF-8");
        assertEquals(normalizeString(actual), normalizeString(expected));
    }

    @Test
    public void testAddFileBodyToResponse() throws Exception {
        setRequestString("GET /file1 HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        String actual = new String(generator.addBodyToResponse(builder), "UTF-8");
        assertEquals(normalizeString(actual), normalizeString("file1 contents"));
    }

    @Test
    public void testAddGIFImagePageBodyToResponse() throws Exception {
        setRequestString("GET /image.gif HTTP/1.1\r\nRange: bytes=0-4\r\nConnection: close--break--Host: localhost:5000\r\n\r\n\r\n");
        byte[] output = generator.addBodyToResponse(builder);
        assertTrue(output.length > 30000);
    }

    @Test
    public void testAddJPGImagePageBodyToResponse() throws Exception {
        setRequestString("GET /image.jpeg HTTP/1.1\r\nRange: bytes=0-4\r\nConnection: close--break--Host: localhost:5000\r\n\r\n\r\n");
        byte[] output = generator.addBodyToResponse(builder);
        assertTrue(output.length > 38000);
    }

    @Test
    public void testAddPNGImagePageBodyToResponse() throws Exception {
        setRequestString("GET /image.png HTTP/1.1\r\nRange: bytes=0-4\r\nConnection: close--break--Host: localhost:5000\r\n\r\n\r\n");
        byte[] output = generator.addBodyToResponse(builder);
        assertTrue(output.length > 240000);
    }

    @Test
    public void testAddFile1PageBodyToResponse() throws Exception {
        setRequestString("GET /partial_content.txt HTTP/1.1\r\nRange: bytes=0-4\r\nConnection: close--break--Host: localhost:5000\r\n\r\n\r\n");
        String expected = "This";
        String actual = new String(generator.addBodyToResponse(builder), "UTF-8");
        assertEquals(normalizeString(actual), normalizeString(expected));
    }
}