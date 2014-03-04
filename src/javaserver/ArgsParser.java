package javaserver;
import static java.lang.Integer.parseInt;
/**
 * Created by Taryn on 3/3/14.
 */
public class ArgsParser {
    private String[] cliArgs;

    public ArgsParser(String[] args) {
        cliArgs = args;
    }

    public Integer parsePort() {

        int port;

        if ((cliArgs.length > 0) && cliArgs[0].equals("-p")){
            port = parseInt(cliArgs[1]);

        } else {
            port = 5000;
        }

        return port;
    }

    public String parseDirectory() {

        String directory;

        if ((cliArgs.length > 0) && cliArgs[2].equals("-d")){
            directory = cliArgs[3];

        } else {
            directory = "none";
        }

        return directory;
    }
}
