package javaserver;
import java.io.BufferedReader;
import java.io.IOException;
/**
 * Created by Taryn on 3/4/14.
 */
public class RequestParser {
    private BufferedReader clientRequest;
    private String request;

    public RequestParser(BufferedReader clientRequest) throws IOException {
        this.clientRequest = clientRequest;
        this.request = parseRequest();
    }

    public String getRequest() {
        return this.request;
    }

    public String parseRequest() throws IOException {
        String line;
        StringBuilder stringBuilder = new StringBuilder();

        while(!(line = clientRequest.readLine()).equals("")){
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

    protected String getMethod() throws IOException {
        return request.split(" ")[0];
    }

    protected String getRequestURI() throws IOException {
        return request.split(" ")[1];
    }
}
