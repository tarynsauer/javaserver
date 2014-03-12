package javaserver;

import java.io.IOException;

/**
 * Created by Taryn on 3/4/14.
 */
public class RequestHandler {
    private RequestParser parser;
    private RootResponse rootResponse;
    private MethodNotAllowedResponse methodNotAllowedResponse;
    private FormResponse formResponse;
    private ImageResponse imageResponse;
    private PartialResponse partialResponse;

    public RequestHandler(RequestParser parser) throws IOException {
        this.parser = parser;
        this.formResponse = new FormResponse();
        this.rootResponse = new RootResponse();
        this.methodNotAllowedResponse = new MethodNotAllowedResponse();
        this.imageResponse = new ImageResponse();
        this.partialResponse = new PartialResponse();
    }

    public void setRootResponse(RootResponse rootResponse) {
        this.rootResponse = rootResponse;
    }

    public void setMethodNotAllowedResponse(MethodNotAllowedResponse methodNotAllowedResponse) {
        this.methodNotAllowedResponse = methodNotAllowedResponse;
    }

    public void setImageResponse(ImageResponse imageResponse) {
        this.imageResponse = imageResponse;
    }

    public void setPartialResponse(PartialResponse partialResponse) {
        this.partialResponse = partialResponse;
    }

    public byte[] getResponse() throws IOException {
        byte[] response;
        String method = parser.getMethod();
        String uri = parser.getRequestURI();

            if (uri.equals("/")) {
                response = rootResponse.getResponseMessage(parser);
            } else if (uri.equals("/file1")) {
                if (!(method.equals("GET"))) {
                    response = methodNotAllowedResponse.getResponseMessage(parser);
                } else {
                    response = rootResponse.getResponseMessage(parser);
                }
            } else if (uri.equals("/file2")) {
                response = rootResponse.getResponseMessage(parser);
            } else if (uri.startsWith("/image")) {
                response = imageResponse.getResponseMessage(parser);
            } else if (uri.equals("/partial_content.txt")) {
                response = partialResponse.getResponseMessage(parser);
            } else if (uri.equals("/text-file.txt")) {
                if (!(method.equals("GET"))) {
                    response = methodNotAllowedResponse.getResponseMessage(parser);
                } else {
                    response = rootResponse.getResponseMessage(parser);
                }
            } else if ((uri.startsWith("/form"))) {
                response = formResponse.getResponseMessage(parser);
            } else if (uri.startsWith("/parameters")) {
                response = new ParameterDecodeResponse().getResponseMessage(parser);
            } else if (uri.startsWith("/logs")) {
                response = new AuthenticationResponse().getResponseMessage(parser);
            } else if (uri.equals("/method_options")) {
                response = new MethodOptionsResponse().getResponseMessage(parser);
            } else if (uri.equals("/redirect")) {
                response = new RedirectResponse().getResponseMessage(parser);
            } else {
                response = new NotFoundResponse().getResponseMessage(parser);
            }
        return response;
    }

}
