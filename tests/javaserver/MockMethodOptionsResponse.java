package javaserver;

/**
 * Created by Taryn on 3/12/14.
 */
public class MockMethodOptionsResponse extends MethodOptionsResponse {
    @Override
    public byte[] getResponseMessage(RequestParser parser) {
        return "MethodOptionsResponse getResponse() called".getBytes();
    }
}
