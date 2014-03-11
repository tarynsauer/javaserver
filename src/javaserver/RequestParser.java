package javaserver;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by Taryn on 3/4/14.
 */
public class RequestParser {
    private BufferedReader clientRequest;
    private String request;
    private HashMap<String, String> headers;

    public RequestParser(BufferedReader clientRequest) throws IOException {
        this.clientRequest = clientRequest;
        this.request = parseRequest();
        this.headers = getHeaders();
        System.out.println(getHeaders());
    }

    public String getRequest() {
        return this.request;
    }

    public HashMap<String, String> getHeaders() throws IOException {
        String[] splitRequest = getRequest().split("--break--");
        HashMap <String, String> map = new HashMap<String, String>();
        for (String line : splitRequest) {
            String[] mapPair = line.split(": ");
            if (mapPair.length > 1) {
                map.put(mapPair[0], mapPair[1]);
            } else {
                map.put(mapPair[0], "");
            }
        }
        return map;
    }

    public String parseRequest() throws IOException {
        String line = "";
        StringBuilder stringBuilder = new StringBuilder();

        while((line = clientRequest.readLine()) != null){
            stringBuilder.append(line + "--break--");
            if (line.equals("")) {
                return stringBuilder.toString() + "--break--" + getRequestBody();
            }
        }
        return stringBuilder.toString() + "--break--" + getRequestBody();
    }

    private String getRequestBody() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        while (clientRequest.ready()) {
            stringBuilder.append((char) clientRequest.read());
        }
        return stringBuilder.toString();
    }

    public boolean includesHeader(String headerName) {
        return request.contains(headerName);
    }

    protected String getMethod() throws IOException {
        return request.split(" ")[0];
    }

    protected String getRequestURI() throws IOException {
        Pattern pattern = Pattern.compile(" (/.*?)[&? ]");
        Matcher matcher = pattern.matcher(request);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "/";
        }
    }

    protected String getRequestedFileName() throws IOException {
        Pattern pattern = Pattern.compile(" /(.*?)[&? ]");
        Matcher matcher = pattern.matcher(request);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "/";
        }
    }

    public String getAuthentication() {
        Pattern pattern = Pattern.compile("Authorization: Basic (.*?)==");
        Matcher matcher = pattern.matcher(request);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    public String getQueryString() {
        String match = null;
        Pattern pattern = Pattern.compile("\\?(.*?) ");
        Matcher matcher = pattern.matcher(request);
        if (matcher.find()) {
            match = matcher.group(1);
        }
        return match;
    }

    public Map<String, String> getParameters() {
        String queryString = getQueryString();
        String[] parametersList = queryString.split("&");

        Map<String,String> allParameters= new HashMap<String, String>();
        for (String parameter : parametersList) {
            String[] keyValPair = parameter.split("=");
            allParameters.put(keyValPair[0], keyValPair[1]);
        }
        return allParameters;
    }

    public String getRange() {
        Pattern pattern = Pattern.compile("Range: bytes=(.*?)[A-Z \\$]");
        Matcher matcher = pattern.matcher(request);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "/";
        }
    }
}
