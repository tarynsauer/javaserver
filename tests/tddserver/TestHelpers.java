package tddserver;

import java.io.*;

/**
 * Created by Taryn on 3/13/14.
 */
public class TestHelpers {
    public RequestParser requestParser;

    public String responseToString(byte[] input) throws UnsupportedEncodingException {
        String expectedOutput = new String(input, "UTF-8");
        return normalizeString(expectedOutput);
    }

    public void setUpParserForResponseTesting() throws IOException {
        byte[] data = "".getBytes();
        InputStream input = new ByteArrayInputStream(data);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        requestParser = new RequestParser(bufferedReader);
    }

    public String normalizeString(String input) {
        return input.replace("\n", "").replace("\r", "");
    }

    public String getDate() {
        java.util.Date date= new java.util.Date();
        return date.toString();
    }

}
