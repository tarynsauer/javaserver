package javaserver;

/**
 * Created by Taryn on 3/12/14.
 */
public class MockRedirectResponse extends RedirectResponse {
    @Override
    public byte[] getResponseMessage(RequestParser parser) {
        return "RedirectResponse getResponse() called".getBytes();
    }
}
