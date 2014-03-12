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
    private ParameterDecodeResponse parameterDecodeResponse;
    private AuthenticationResponse authenticationResponse;
    private MethodOptionsResponse methodOptionsResponse;
    private RedirectResponse redirectResponse;
    private NotFoundResponse notFoundResponse;

    public RequestHandler(RequestParser parser) throws IOException {
        this.parser = parser;
        this.formResponse = new FormResponse();
        this.rootResponse = new RootResponse();
        this.methodNotAllowedResponse = new MethodNotAllowedResponse();
        this.imageResponse = new ImageResponse();
        this.partialResponse = new PartialResponse();
        this.parameterDecodeResponse = new ParameterDecodeResponse();
        this.authenticationResponse = new AuthenticationResponse();
        this.methodOptionsResponse = new MethodOptionsResponse();
        this.redirectResponse = new RedirectResponse();
        this.notFoundResponse = new NotFoundResponse();
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

    public void setParameterDecodeResponse(ParameterDecodeResponse parameterDecodeResponse) {
        this.parameterDecodeResponse = parameterDecodeResponse;
    }

    public void setAuthenticationResponse(AuthenticationResponse authenticationResponse) {
        this.authenticationResponse = authenticationResponse;
    }

    public void setMethodOptionsResponse(MethodOptionsResponse methodOptionsResponse) {
        this.methodOptionsResponse = methodOptionsResponse;
    }

    public void setRedirectResponse(RedirectResponse redirectResponse) {
        this.redirectResponse = redirectResponse;
    }

    public void setNotFoundResponse(NotFoundResponse notFoundResponse) {
        this.notFoundResponse = notFoundResponse;
    }

    public byte[] getResponse() throws IOException {
        byte[] response;
        String method = parser.getMethod();
        String uri = parser.getRequestURI();

            if ((uri.equals("/")) || (uri.equals("/file2"))) {
                response = rootResponse.getResponseMessage(parser);
            } else if ((uri.equals("/file1")) || (uri.equals("/text-file.txt"))) {
                if (!(method.equals("GET"))) {
                    response = methodNotAllowedResponse.getResponseMessage(parser);
                } else {
                    response = rootResponse.getResponseMessage(parser);
                }
            } else if (uri.startsWith("/image")) {
                response = imageResponse.getResponseMessage(parser);
            } else if (uri.equals("/partial_content.txt")) {
                response = partialResponse.getResponseMessage(parser);
            } else if ((uri.startsWith("/form"))) {
                response = formResponse.getResponseMessage(parser);
            } else if (uri.startsWith("/parameters")) {
                response = parameterDecodeResponse.getResponseMessage(parser);
            } else if (uri.startsWith("/logs")) {
                response = authenticationResponse.getResponseMessage(parser);
            } else if (uri.equals("/method_options")) {
                response = methodOptionsResponse.getResponseMessage(parser);
            } else if (uri.equals("/redirect")) {
                response = redirectResponse.getResponseMessage(parser);
            } else {
                response = notFoundResponse.getResponseMessage(parser);
            }
        return response;
    }

}