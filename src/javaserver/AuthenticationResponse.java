package javaserver;

import java.io.IOException;

/**
 * Created by Taryn on 3/3/14.
 */
public class AuthenticationResponse extends AbstractResponse {

    @Override
    String getBody(String condition) {
        return "<h1>" + condition + "</h1>";
    }

    @Override
    byte[] getResponseMessage(String status, String authentication) throws IOException {
        if ((valid(authentication))) {
            String response = getStatusLine("200 OK") + getDateInfo() + getServerInfo() + getContentTypeInfo("text/html") + getBody("Authentication required");;
            return response.getBytes();
        } else {
            String response = getStatusLine(status) + getDateInfo()  + getAuthenticateHeader() + getServerInfo() + getContentTypeInfo("text/html") + getBody("Logs");
            return response.getBytes();
        }
    }

    private String getAuthenticateHeader() {
        return "WWW-Authenticate: Basic realm=\"You must log in to view Logs\"\r\n";
    }

    private boolean valid(String authentication) {
        if (authentication == null) {
            return false;
        } else if (authentication.equals("YWRtaW46aHVudGVyMg")) {
            return true;
        } else {
            return false;
        }
    }
}
