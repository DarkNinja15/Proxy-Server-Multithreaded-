import java.net.*;
import java.io.IOException;

public class TCPServer{
    public static void main(String args[]) throws IOException{
        int serverPort=8080;
        ServerSocket serverSocket=new ServerSocket(serverPort);
        System.out.println("Server is listening on port: "+serverPort);

        while(true){
            Socket clientSocket=serverSocket.accept();
            String clientIP=clientSocket.getInetAddress().toString();
            int clientPort=clientSocket.getPort();
            System.out.println("Client connected on"+clientIP+" "+clientPort);
            clientSocket.close();
        }
    }
}