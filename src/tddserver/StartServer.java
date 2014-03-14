package tddserver;

import java.io.IOException;

/**
 * Created by Taryn on 3/13/14.
 */
public class StartServer {
    public static void main(String[] args) throws IOException {
        MultiThreadedServer server = new MultiThreadedServer(5000, "some_directory");
        new Thread(server).start();
    }
}
