import java.net.*;
import java.io.IOException;
import java.io.InputStream;

public class client {
    public static void main(String args[]) throws IOException {
        String hostname="localhost";
        int serverPort=8080;

        Socket clientSocket=new Socket(hostname,serverPort);
        System.out.println("Connected to server:"+hostname+" "+serverPort);

        InputStream inputStream = clientSocket.getInputStream();
        byte[] data = new byte[1024];
        inputStream.read(data);
        System.out.println("Data from server: "+new String(data));

        clientSocket.close();
    }
}
