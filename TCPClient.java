import java.net.*;
import java.io.IOException;

public class TCPClient {
    public static void main(String args[]) throws IOException {
        String hostname="localhost";
        int serverPort=8080;

        Socket clientSocket=new Socket(hostname,serverPort);
        System.out.println("Connected to server:"+hostname+" "+serverPort);

        clientSocket.close();
    }
}
