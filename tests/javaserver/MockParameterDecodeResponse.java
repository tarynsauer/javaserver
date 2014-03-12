package javaserver;

/**
 * Created by Taryn on 3/12/14.
 */
public class MockParameterDecodeResponse extends ParameterDecodeResponse {
    @Override
    public byte[] getResponseMessage(RequestParser parser) {
        return "ParameterDecodeResponse getResponse() called".getBytes();
    }
}
