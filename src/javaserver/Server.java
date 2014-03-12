package javaserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Taryn on 3/3/14.
 */
public class Server {
    private int port;
    private String directory;
    private ServerSocket serverSocket = null;

    public Server(int port, String directory) {
        this.port = port;
        this.directory = directory;
    }

    public void start() {
        openServerSocket();
        int numConnections = 0;

        while(true) {
            Socket clientSocket = acceptClientConnection();
            BufferedReader input = getClientRequest(clientSocket);

            System.out.println("Connection " + numConnections++ + " on port " + this.port);

            WorkerRunnable workerRunnable = new WorkerRunnable(clientSocket, directory, input);
            Thread thread = new Thread(workerRunnable);
            thread.start();
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            throw new RuntimeException("Can't open port " + this.port, e);
        }
    }

    private Socket acceptClientConnection() {
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.out.println("Can't accept client connection");
            e.printStackTrace();
        }
        return clientSocket;
    }

    private BufferedReader getClientRequest(Socket clientSocket) {
        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Can't get client request");
            e.printStackTrace();
        }
        return input;
    }


}
