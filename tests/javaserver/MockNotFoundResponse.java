package javaserver;

/**
 * Created by Taryn on 3/12/14.
 */
public class MockNotFoundResponse extends NotFoundResponse {
    @Override
    public byte[] getResponseMessage(RequestParser parser) {
        return "NotFoundResponse getResponse() called".getBytes();
    }
}
