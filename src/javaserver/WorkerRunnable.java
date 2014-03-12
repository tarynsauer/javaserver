package javaserver;

import java.io.*;
import java.net.Socket;

/**

 */
public class WorkerRunnable implements Runnable {
    private final Socket clientSocket;
    private final String directory;
    private BufferedReader input;

    public WorkerRunnable(Socket clientSocket, String directory, BufferedReader input) {
        this.clientSocket = clientSocket;
        this.directory = directory;
        this.input = input;
    }

    public void run() {
        try {
            byte[] response = getResponse();
            provideResponseForClient(response);
        } catch (IOException e) {
            System.out.println("Request could not be processed");
            e.printStackTrace();
        }
    }

    private byte[] getResponse() throws IOException {
        RequestHandler requestHandler = new RequestHandler(new RequestParser(input));
        return requestHandler.getResponse();
    }

    private void provideResponseForClient(byte[] response) throws IOException {
        DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
        output.write(response);
        output.close();
        input.close();
        clientSocket.close();
    }

}