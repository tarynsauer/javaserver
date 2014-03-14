package tddserver;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by Taryn on 3/13/14.
 */
public class ParametersPageTest {
    private ParametersPage page;
    private RequestParser requestParser;

    @Before
    public void setUp() throws Exception {
        page = new ParametersPage("/parameters");
    }

    private void mockParametersRequest() throws Exception {
        String str = "GET /parameters?variable_1=Operators" + "" +
                "%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40" +
                "%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F" +
                "&variable_2=stuff HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n";
        byte[] data = str.getBytes();
        InputStream input = new ByteArrayInputStream(data);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        requestParser = new RequestParser(bufferedReader);
    }

    private String getExpectedResponse() {
        String date = new Date().toString();
        return "<h1>Decoded parameters:</h1>" +
                "<p>variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?</p>" +
                "<p>variable_2 = stuff</p>";
    }

    @Test
    public void testGetContentReturnsParsedParameters() throws Exception {
        mockParametersRequest();
        assertEquals(getExpectedResponse(), page.getContent(requestParser));
    }
}
