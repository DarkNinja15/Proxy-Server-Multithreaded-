import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TCPServer{
    public static void main(String args[]) throws IOException{
        Scanner sc=new Scanner(System.in);
        int serverPort=8080;
        @SuppressWarnings("resource")
        ServerSocket serverSocket=new ServerSocket(serverPort);
        System.out.println("Server is listening on port: "+serverPort);

        // spinning up a thread pool
        ProxyThreadPool threadPool= new ProxyThreadPool(1,2,1L);

        while(true){
            Socket clientSocket=serverSocket.accept();
            String clientIP=clientSocket.getInetAddress().toString();
            int clientPort=clientSocket.getPort();
            System.out.println("Client connected on"+clientIP+" "+clientPort);

            // connecting to a destination server
            threadPool.submitTask(new DestHandler("localhost", 8081));

            clientSocket.close();
            sc.close();
        }

        
    }
}