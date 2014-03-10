package javaserver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Taryn on 3/7/14.
 */
public class FormResponse extends AbstractResponse {
    private static String dataValue = "";

    public void setDataValue(String value) {
        dataValue = value;
    }

    public String getDataValue() {
        return dataValue;
    }

    @Override
    String getBody(String string) {
        return null;
    }

    @Override
    byte[] getResponseMessage(RequestParser parser) throws IOException {
        String response = getStatusLine("200 OK") + getDateInfo() + getServerInfo() + getContentTypeInfo("text/html") + getAttribute(parser);
        return response.getBytes();
    }

    private String getAttribute(RequestParser parser) throws IOException {
        String method = parser.getMethod();
        Map<String,String> parameters = new HashMap<String, String>();

        if (method.equals("POST") || (method.equals("PUT"))) {
            setDataValue(parameters.get("data"));
        } else if (method.equals("DELETE")) {
            setDataValue("");
        }
        return "<p data = " + getDataValue() + ">There may be a hidden name value here.</p>";
    }

}
