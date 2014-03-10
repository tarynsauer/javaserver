package javaserver;

import java.io.IOException;
import static javaserver.JavaserverConstants.AUTH_TOKEN;
import static javaserver.HTTPStatusConstants.OK;
import static javaserver.HTTPStatusConstants.UNAUTHORIZED;
/**
 * Created by Taryn on 3/3/14.
 */
public class AuthenticationResponse extends AbstractResponse {

    @Override
    byte[] getResponseMessage(RequestParser parser) throws IOException {
        String authentication = parser.getAuthentication();
        if (isValid(authentication)) {
            String response = getStatusLine(OK) + getDateInfo() + getServerInfo() + getContentTypeInfo("text/html") + getBody();
            return response.getBytes();
        } else {
            String response = getStatusLine(UNAUTHORIZED) + getDateInfo()  + getAuthenticateHeader() +
                    getServerInfo() + getContentTypeInfo("text/html") + getUnauthorizedBody();
            return response.getBytes();
        }
    }

    private String getAuthenticateHeader() {
        return "WWW-Authenticate: Basic realm=\"Authentication required for Logs\"\r\n";
    }

    private boolean isValid(String authentication) {
        if (authentication == null) {
            return false;
        } else if (authentication.equals(AUTH_TOKEN)) {
            return true;
        } else {
            return false;
        }
    }

    private String getBody() {
        return bodyBegin() +
                "<h1>Logs</h1><p>GET /log HTTP/1.1</p><p>PUT /these HTTP/1.1</p><p>HEAD /requests HTTP/1.1</p>" +
                bodyEnd();
    }

    private String getUnauthorizedBody() {
        return bodyBegin() + "<h1>Authentication required</h1>" + bodyEnd();
    }
}
