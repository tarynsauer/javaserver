package javaserver;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
/**
 * Created by Taryn on 3/4/14.
 */
public class RequestParser {
    private BufferedReader clientRequest;

    public RequestParser(BufferedReader clientRequest) throws IOException {
        this.clientRequest = clientRequest;
    }

    public HashMap<String, String> parseRequest() throws IOException {
        String line = "";
        StringBuilder stringBuilder = new StringBuilder();
        HashMap<String, String> parsedRequest = new HashMap<String, String>();


        while((line = clientRequest.readLine()) != null){
            stringBuilder.append(line);
        }

        parsedRequest.put("method", getMethod(stringBuilder.toString()));
        parsedRequest.put("uri", getRequestURI(stringBuilder.toString()));
        return parsedRequest;
    }

    protected String getMethod(String input) throws IOException {
        String method = input.toString().split(" ")[0];
        return method;
    }

    protected String getRequestURI(String input) throws IOException {
        String method = input.toString().split(" ")[1];
        return method;
    }
}
