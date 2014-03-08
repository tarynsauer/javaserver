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
    String getBody(String method) {
        if (method.equals("POST")) {
            setDataValue("cosby");
        } else if (method.equals("PUT")) {
            setDataValue("heathcliff");
        } else if (method.equals("DELETE")) {
            setDataValue("");
        }
        return "<p data = " + getDataValue() + ">There may be a hidden name value here.</p>";
    }

    @Override
    byte[] getResponseMessage(String status, String method) throws IOException {
        String response = getStatusLine(status) + getDateInfo() + getServerInfo() + getContentTypeInfo("text/html") + getBody(method);
        return response.getBytes();
    }
}
