package javaserver;

import javaserver.RequestParser;
import javaserver.PartialResponse;

public class MockPartialResponse extends PartialResponse {

    @Override
    public byte[] getResponseMessage(RequestParser parser) {
        return "PartialResponse getResponse() called".getBytes();
    }
}