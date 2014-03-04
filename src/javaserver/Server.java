package javaserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by Taryn on 3/3/14.
 */
public class Server {
    protected int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        Socket clientSocket = null;

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                clientSocket = serverSocket.accept();
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                System.out.println("New thread started on port " + port);

                HashMap<String, String> parsedRequest = new RequestParser(input).parseRequest();
                RequestHandler requestHandler = new RequestHandler(parsedRequest);

                byte[] response = requestHandler.getResponse();
                DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
                output.write(response);
                output.close();
                input.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Error occurred while listening on port " + port );
            e.printStackTrace();
        }
    }



}
