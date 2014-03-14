package tddserver;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by Taryn on 3/13/14.
 */
public class ParametersPage extends Page {
    public ParametersPage(String url) {
        super(url);
    }

    @Override
    public String getContent(RequestParser parser) throws UnsupportedEncodingException {
        setContent("<h1>Decoded parameters:</h1>");
        getQueries(parser);
        return this.content;
    }

    private void getQueries(RequestParser parser) throws UnsupportedEncodingException {
        String[] allVariables = parser.getAllVariables();
        for (String match : allVariables) {
            this.content = this.content + "<p>" + URLDecoder.decode(match, "UTF-8") + "</p>";
        }
    }
}
