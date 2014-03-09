package javaserver;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by Taryn on 3/8/14.
 */
public class ParameterDecodeResponse extends AbstractResponse {
    @Override
    String getBody(String condition) {
        return null;
    }

    @Override
    byte[] getResponseMessage(String status, String url) throws IOException {
        String response = getStatusLine(status) + getDateInfo() + getServerInfo() + getContentTypeInfo("text/html") + getQueries(url);
        return response.getBytes();
    }

    private String getQueries(String url) throws UnsupportedEncodingException {
        String queryString = getQueryString(url);
        String[] allVariables = getAllVariables(queryString);
        String results = "<h1>Decoded parameters:</h1>";
        for (String match : allVariables) {
            results += "<p>" + URLDecoder.decode(match, "UTF-8") + "</p>";
        }
        return results;
    }

    private String getQueryString(String url) {
        String match = null;
        Pattern pattern = Pattern.compile("\\?(.*)");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            match = matcher.group(1);
        }
        return match;
    }

    private String[] getAllVariables(String queryString) {
        String[] queries = queryString.split("=");
        String queryParts = "";
        for(String s : queries) {
            queryParts += s;
            queryParts += " = ";
        }
        return queryParts.split("&");
    }
}
