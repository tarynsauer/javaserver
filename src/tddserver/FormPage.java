package tddserver;

/**
 * Created by Taryn on 3/13/14.
 */
public class FormPage extends Page {
    private static String data;

    public FormPage(String url) {
        super(url);
        this.data = "";
        this.content = "<p data = " + getData() + ">There may be a hidden name value here.</p>";
    }

    @Override
    public String getContent(RequestParser parser) {
        setContent("<p data = " + getData() + ">There may be a hidden name value here.</p>");
        return this.content;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String value) {
        this.data = value;
    }

}
