package tddserver;

import java.io.UnsupportedEncodingException;

/**
 * Created by Taryn on 3/13/14.
 */
public class Page {
    private String uri;
    protected static String content;

    public Page(String url) {
        this.uri = url;
        this.content = "";
    }

    public String getUri() {
        return this.uri;
    }

    public String getContent(RequestParser parser) throws UnsupportedEncodingException {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
