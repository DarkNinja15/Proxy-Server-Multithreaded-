import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TCPServer{
    public static void main(String args[]) throws IOException{
        Scanner sc=new Scanner(System.in);
        int serverPort=8080;
        ServerSocket serverSocket=new ServerSocket(serverPort);
        System.out.println("Server is listening on port: "+serverPort);

        while(true){
            Socket clientSocket=serverSocket.accept();
            String clientIP=clientSocket.getInetAddress().toString();
            int clientPort=clientSocket.getPort();
            System.out.println("Client connected on"+clientIP+" "+clientPort);

            

            // connecting to a destination server

            int destPort=8081;


            Socket destSocket=new Socket("localhost",destPort);
            
            // InputStream inFromClient=clientSocket.getInputStream();
            // OutputStream outToClient=clientSocket.getOutputStream();

            // InputStream inFromDest=destSocket.getInputStream();
            OutputStream outToDest=destSocket.getOutputStream();

            outToDest.write("Hello from server".getBytes());

            clientSocket.close();
            sc.close();
        }

        
    }
}