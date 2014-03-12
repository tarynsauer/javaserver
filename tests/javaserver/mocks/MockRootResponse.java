package javaserver.mocks;

import javaserver.RequestParser;
import javaserver.RootResponse;

public class MockRootResponse extends RootResponse {

    @Override
    public byte[] getResponseMessage(RequestParser parser) {
        return "RootResponse getResponse() called".getBytes();
    }
}