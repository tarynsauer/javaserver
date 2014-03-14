package tddserver;

/**
 * Created by Taryn on 3/13/14.
 */
public class Logger {
    private static String logs;

    public Logger() {
        this.logs = "GET /log HTTP/1.1 PUT /these HTTP/1.1 HEAD /requests HTTP/1.1";
    }

    public String getLogs() {
        return this.logs;
    }

}
