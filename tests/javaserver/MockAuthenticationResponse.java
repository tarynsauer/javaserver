package javaserver;

/**
 * Created by Taryn on 3/12/14.
 */
public class MockAuthenticationResponse extends AuthenticationResponse {
    @Override
    public byte[] getResponseMessage(RequestParser parser) {
        return "AuthenticationResponse getResponse() called".getBytes();
    }
}
