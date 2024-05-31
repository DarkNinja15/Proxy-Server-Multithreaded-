import threadPool.ProxyThreadPool;
import lruCache.LRUcache;
import serverHandler.DestHandler;

import java.net.*;
import java.io.*;
import java.util.Scanner;


public class TCPProxyServer{
    public static void main(String args[]) throws IOException{
        Scanner sc=new Scanner(System.in);
        int serverPort=8080;
        @SuppressWarnings("resource")
        ServerSocket serverSocket=new ServerSocket(serverPort);
        System.out.println("Proxy Server is listening on port: "+serverPort);

        // spinning up a thread pool
        ProxyThreadPool threadPool= new ProxyThreadPool(1,2,1L);

        // setup cache
        LRUcache cache=new LRUcache(2);

        while(true){
            Socket clientSocket=serverSocket.accept();
            String clientIP=clientSocket.getInetAddress().toString();
            int clientPort=clientSocket.getPort();
            System.out.println("Client connected on"+clientIP+" "+clientPort);

            if(cache.get(clientIP)!=null){
                String data=cache.get(clientIP);
                OutputStream outToClient=clientSocket.getOutputStream();
                outToClient.write(data.getBytes());
                continue;
            }

            // connecting to a destination server
            threadPool.submitTask(new DestHandler("localhost", 8081,clientSocket,cache));

            sc.close();
        }

    }
}