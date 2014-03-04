package javaserver;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.InetAddress;

/**
 * Created by Taryn on 3/3/14.
 */
public class CustomAuthentication extends Authenticator {
    // Called when password authorization is needed
    protected PasswordAuthentication getPasswordAuthentication() {

        // Get information about the request
        String prompt = getRequestingPrompt();
        String hostname = getRequestingHost();
        InetAddress ipaddr = getRequestingSite();
        int port = getRequestingPort();

        String username = "username";
        String password = "password";

        // Return the information (a data holder that is used by Authenticator)
        return new PasswordAuthentication(username, password.toCharArray());

    }
}
