package javaserver;

import java.io.IOException;

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
    byte[] getResponseMessage(RequestParser parser) throws IOException {
        getAttribute(parser);
        String response = getStatusLine("200 OK") + getDateInfo() + getServerInfo() + getContentTypeInfo("text/html") + getBody();
        return response.getBytes();
    }

    private void getAttribute(RequestParser parser) throws IOException {
        String method = parser.getMethod();

        if (method.equals("POST")) {
            setDataValue("cosby");
        } else if (method.equals("PUT")) {
            setDataValue("heathcliff");
        } else if (method.equals("DELETE")) {
            setDataValue("");
        }
    }

    private String getBody() {
        return bodyBegin() + "<p data = " + getDataValue() +
                ">There may be a hidden name value here.</p>" + bodyEnd();
    }

}
