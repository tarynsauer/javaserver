package javaserver;
import static java.lang.Integer.parseInt;
import static javaserver.JavaserverConstants.DIRECTORY_PATH;
import static javaserver.JavaserverConstants.DEFAULT_PORT;
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
            port = DEFAULT_PORT;
        }

        return port;
    }

    public String parseDirectory() {
        String directory;

        if ((cliArgs.length > 0) && cliArgs[2].equals("-d")){
            directory = cliArgs[3];

        } else {
            directory = DIRECTORY_PATH;
        }

        return directory;
    }
}
