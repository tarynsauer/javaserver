package javaserver;

import java.io.IOException;
/**
 * Created by Taryn on 3/3/14.
 */

public class StartServer {
    public static void main(String[] args) throws IOException {
        ArgsParser options = new ArgsParser(args);
        int port = options.parsePort();
        String directory = options.parseDirectory();

        Server server = new Server(port, directory);
        new Thread(server).start();
    }
}
