package javaserver;

import javaserver.ImageResponse;
import javaserver.RequestParser;

public class MockImageResponse extends ImageResponse {

    @Override
    public byte[] getResponseMessage(RequestParser parser) {
        return "ImageResponse getResponse() called".getBytes();
    }
}