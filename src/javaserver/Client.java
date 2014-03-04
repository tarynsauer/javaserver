package javaserver;
import java.net.Socket;
import java.io.PrintWriter;
/**
 * Created by Taryn on 3/3/14.
 */
public class Client {
    private static Socket socket;
    private static PrintWriter printWriter;
    public static void main(String[] args)
    {
        try
        {
            socket = new Socket("localhost",5000);
            printWriter = new PrintWriter(socket.getOutputStream(),true);
            printWriter.println("Hello Socket");
            printWriter.println("EYYYYYAAAAAAAA!!!!");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
