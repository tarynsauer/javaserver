package javaserver;

import java.io.IOException;

/**
 * Created by Taryn on 3/7/14.
 */
public class FormResponse extends AbstractResponse {
    private String dataValue = "cosby";

    public void setDataValue(String value) {
        this.dataValue = value;
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
        return "<p data = " + dataValue + ">There may be a hidden name value here.</p>";
    }

    @Override
    byte[] getResponseMessage(String status, String method) throws IOException {
        String response = getStatusLine(status) + getDateInfo() + getServerInfo() + getContentTypeInfo("text/html") + getBody(method);
        return response.getBytes();
    }
}
