package tddserver;

/**
 * Created by Taryn on 3/13/14.
 */
public class LogsPage extends Page {
    private boolean authenticated;
    private Logger logger;

    public LogsPage(String url) {
        super(url);
        this.authenticated = false;
        this.logger = new Logger();
    }

    public boolean getAuthenticated() {
        return this.authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    @Override
    public String getContent(RequestParser parser) {
        if (getAuthenticated()) {
            setContent("<h1>Logs</h1>");
        } else {
            setContent("<h1>Authentication required</h1>" + logger.getLogs());
        }
        return this.content;
    }

 }
