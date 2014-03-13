package tddserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Taryn on 3/12/14.
 */
public class RequestParser {
    private BufferedReader clientRequest;
    private String request;
    private String method;
    private String uri;
    private String fileName;
    private String fileExtension;
    private HashMap<String, String> headers;
    private HashMap<String, String> params;

    public RequestParser(BufferedReader clientRequest) throws IOException {
        this.clientRequest = clientRequest;
        parseRequestComponents();
    }

    public String getRequest() { return this.request; }

    public String getMethod() { return this.method; }

    public String getUri() { return this.uri; }

    public String getFileName() { return this.fileName; }

    public String getFileExtension() { return this.fileExtension; }

    public HashMap<String, String> getHeaders() { return this.headers; }

    public HashMap<String, String> getParams() { return this.params; }

    private void parseRequestComponents() throws IOException {
        this.request = parseRequest();
        this.method = parseMethod();
        this.uri = parseUri();
        this.fileName = parseFileName();
        this.fileExtension = parseFileExtension();
        this.params = parseParams();
        this.headers = parseHeaders();
    }

    private String parseRequest() throws IOException {
        String line = "";
        StringBuilder stringBuilder = new StringBuilder();

        while((line = clientRequest.readLine()) != null){
            stringBuilder.append(line + "--break--");
            if (line.equals("")) {
                return stringBuilder.toString() + "--break--" + parseRequestBody();
            }
        }
        return stringBuilder.toString() + "--break--" + parseRequestBody();
    }

    private String parseRequestBody() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        while (clientRequest.ready()) {
            stringBuilder.append((char) clientRequest.read());
        }
        return stringBuilder.toString();
    }

    private String parseMethod() throws IOException {
        return request.split(" ")[0];
    }

    private String parseUri() throws IOException {
        Pattern pattern = Pattern.compile(" (/.*?)[&? ]");
        Matcher matcher = pattern.matcher(request);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "/";
        }
    }

    private String parseFileName() throws IOException {
        Pattern pattern = Pattern.compile(" /(.*?)[&? ]");
        Matcher matcher = pattern.matcher(request);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "/";
        }
    }

    private String parseFileExtension() throws IOException {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (Exception e) {
            return "";
        }
    }

    private HashMap<String, String> parseHeaders() throws IOException {
        String[] splitRequest = request.split("--break--");
        HashMap <String, String> map = new HashMap<String, String>();
        for (String line : splitRequest) {
            String[] mapPair = line.split(": ");
            if (mapPair.length > 1) {
                map.put(mapPair[0], mapPair[1]);
            }
        }
        return map;
    }

    private HashMap<String, String> parseParams() throws IOException {
        String[] splitRequest = request.split("--break----break----break--");
        if (splitRequest.length > 1) {
            splitRequest[0] = null;
        } else {
            return null;
        }
        HashMap <String, String> map = new HashMap<String, String>();
        for (String item : splitRequest) {
            if (item != null) {
                String[] mapPair = item.split("=");
                if (mapPair.length > 1) {
                    map.put(normalizeString(mapPair[0]), mapPair[1]);
                }
            }
        }
        return map;
    }

    private String normalizeString(String input) {
        return input.replace("\n", "").replace("\r", "");
    }
}