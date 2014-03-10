package javaserver;

import java.io.IOException;

/**
 * Created by Taryn on 3/4/14.
 */
public class RequestHandler {
    private RequestParser parser;
    private String directory;
    private FormResponse formResponse;

    public RequestHandler(RequestParser parser, String directory) throws IOException {
        this.parser = parser;
        this.directory = directory;
        this.formResponse = new FormResponse();
    }

    public byte[] getResponse() throws IOException {
        byte[] response;
        String method = parser.getMethod();
        String uri = parser.getRequestURI();

            if (uri.equals("/")) {
                response = new RootResponse().getResponseMessage(parser);
            } else if (uri.equals("/file1")) {
                if (!(method.equals("GET"))) {
                    response = new MethodNotAllowedResponse().getResponseMessage(parser);
                } else {
                    response = new RootResponse().getResponseMessage(parser);
                }
            } else if (uri.equals("/file2")) {
                response = new RootResponse().getResponseMessage(parser);
            } else if (uri.startsWith("/image")) {
                response = new ImageResponse().getResponseMessage(parser);
            } else if (uri.equals("/partial_content.txt")) {
                response = new PartialResponse().getResponseMessage(parser);
            } else if (uri.equals("/text-file.txt")) {
                if (!(method.equals("GET"))) {
                    response = new MethodNotAllowedResponse().getResponseMessage(parser);
                } else {
                    response = new RootResponse().getResponseMessage(parser);
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
