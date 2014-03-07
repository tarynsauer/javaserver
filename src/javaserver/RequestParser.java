package javaserver;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

        while((line = clientRequest.readLine()) != null){

            stringBuilder.append(line);
            if (line.equals("")) {
                return stringBuilder.toString();
            }

        }
        return stringBuilder.toString();
    }

    protected String getMethod() throws IOException {
        return request.split(" ")[0];
    }

    protected String getRequestURI() throws IOException {
        Pattern pattern = Pattern.compile(" (/.*?) ");
        Matcher matcher = pattern.matcher(request);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "/";
        }    }

    public String getAuthentication() {
        Pattern pattern = Pattern.compile("Authorization: Basic (.*?)==");
        Matcher matcher = pattern.matcher(request);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }
}
