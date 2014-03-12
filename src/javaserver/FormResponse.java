package javaserver;

import java.io.IOException;
import static javaserver.HTTPStatusConstants.OK;
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
    public byte[] getResponseMessage(RequestParser parser) throws IOException {
        getAttribute(parser);
        String response = getStatusLine(OK) + getDateInfo() + getServerInfo() + getContentTypeInfo("text/html") + getBody();
        return response.getBytes();
    }

    private void getAttribute(RequestParser parser) throws IOException {
        String method = parser.getMethod();

        if ((method.equals("POST") || (method.equals("PUT")))) {
            setDataValue(parser.getVariableValue("data"));
        } else if (method.equals("DELETE")) {
            setDataValue("");
        }
    }

    private String getBody() {
        return bodyBegin() + "<p data = " + getDataValue() +
        ">There may be a hidden name value here.</p>" + bodyEnd();
    }

}
