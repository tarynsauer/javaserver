package tddserver;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Taryn on 3/13/14.
 */
public class LogsPageTest extends TestHelpers {
    private LogsPage page;

    @Before
    public void setUp() throws Exception {
        page = new LogsPage("/logs");
        setUpParserForResponseTesting();
    }

    @Test
    public void testGetAuthenticatedShouldReturnFalse() throws Exception {
        assertFalse(page.getAuthenticated());
    }

    @Test
    public void testGetContentDisplaysAuthenticationMessageWhenNotAuthenticated() throws Exception {
        assertEquals(page.getContent(requestParser), "<h1>Authentication required</h1>GET /log HTTP/1.1 PUT /these HTTP/1.1 HEAD /requests HTTP/1.1");
    }

    @Test
    public void testGetContentDisplaysLogsHeaderWhenAuthenticated() throws Exception {
        page.setAuthenticated(true);
        assertEquals(page.getContent(requestParser), "<h1>Logs</h1>");
    }
}
