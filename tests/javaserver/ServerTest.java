package javaserver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Taryn on 3/13/14.
 */
public class ServerTest extends TestHelpers {
    private Server server;
    private MockClient mockClient;

    @Before
    public void setUp() throws Exception {
        server = new Server(5000, "directory");
        new Thread(server).start();
        mockClient = new MockClient();
    }

    @After
    public void tearDown() throws Exception {
        mockClient.closeClient();
        server.stop();
    }

    @Test
    public void testServerReturnsRootResponse() throws Exception {
        String str = "GET / HTTP/1.1/\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n";
        mockClient.stubRequest(str);
        assertEquals(mockClient.getResponse(), expectedRequestResponse(str));
    }

    @Test
    public void testServerReturnsFile1Response() throws Exception {
        String str = "GET /file1 HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000r\n\r\n\r\n";
        mockClient.stubRequest(str);
        assertEquals(mockClient.getResponse(), expectedRequestResponse(str));
    }

}
>>>>>>> master
