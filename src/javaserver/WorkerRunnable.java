package javaserver;

import java.io.*;
import java.net.Socket;

/**

 */
public class WorkerRunnable implements Runnable {
    protected Socket clientSocket = null;
    protected BufferedReader input = null;

    public WorkerRunnable(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void setInput(BufferedReader clientRequest) {
        this.input = clientRequest;
    }

    public void run() {
        try {
            BufferedReader input = getClientRequest(clientSocket);
            byte[] response = getResponse(input);
            provideResponseForClient(response, input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] getResponse(BufferedReader input) throws IOException {
        RequestHandler requestHandler = new RequestHandler(new RequestParser(input));
        return requestHandler.getResponse();
    }

    private void provideResponseForClient(byte[] response, BufferedReader input) throws IOException {
        DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
        output.write(response);
        output.close();
        input.close();
        clientSocket.close();
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