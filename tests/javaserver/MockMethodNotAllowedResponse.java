package javaserver;

public class MockMethodNotAllowedResponse extends MethodNotAllowedResponse {

    @Override
    public byte[] getResponseMessage(RequestParser parser) {
        return "MethodNotAllowedResponse getResponse() called".getBytes();
    }
}
