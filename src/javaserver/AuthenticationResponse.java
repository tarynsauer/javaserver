package javaserver;

import java.io.IOException;

/**
 * Created by Taryn on 3/3/14.
 */
public class AuthenticationResponse extends AbstractResponse {

    @Override
    byte[] getResponseMessage(RequestParser parser) throws IOException {
        String authentication = parser.getAuthentication();
        if ((valid(authentication))) {
            String response = getStatusLine("200 OK") + getDateInfo() + getServerInfo() + getContentTypeInfo("text/html") +
                    "<h1>Logs</h1>" +
                    "<p>GET /log HTTP/1.1</p>" +
                    "<p>PUT /these HTTP/1.1</p>" +
                    "<p>HEAD /requests HTTP/1.1</p>";
            return response.getBytes();
        } else {
            String response = getStatusLine("401 Unauthorized") + getDateInfo()  + getAuthenticateHeader() +
                    getServerInfo() + getContentTypeInfo("text/html") + "<h1>Authentication required</h1>";
            return response.getBytes();
        }
    }

    private String getAuthenticateHeader() {
        return "WWW-Authenticate: Basic realm=\"Authentication required for Logs\"\r\n";
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
