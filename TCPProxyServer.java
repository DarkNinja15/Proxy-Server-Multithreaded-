import threadPool.ProxyThreadPool;
import lruCache.LRUcache;
import requestHandler.TcpHandler;
import requestHandler.httpHandler.HttpHandler;

import java.net.*;
import java.io.*;
import java.util.Scanner;

import configurations.Config;

public class TCPProxyServer {
    public static void main(String args[]) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        int serverPort = Config.serverPort;
        @SuppressWarnings("resource")
        ServerSocket serverSocket = new ServerSocket(serverPort);
        System.out.println("Proxy Server is listening on port: " + serverPort);

        // spinning up a thread pool
        ProxyThreadPool threadPool = new ProxyThreadPool(Config.corePoolSize, Config.maxPoolSize, Config.keepAliveTime);

        // setup cache
        LRUcache cache = new LRUcache(Config.cacheSize);

        switch (Config.serverType) {
            case TCP:
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    String clientIP = clientSocket.getInetAddress().toString();
                    int clientPort = clientSocket.getPort();
                    System.out.println("Client connected on " + clientIP + ":" + clientPort);

                    if (cache.get(clientIP) != null) {
                        String data = cache.get(clientIP);
                        OutputStream outToClient = clientSocket.getOutputStream();
                        outToClient.write(data.getBytes());
                        continue;
                    }

                    // connecting to a destination server
                    threadPool.submitTask(new TcpHandler(Config.tcpServerHost,
                            Config.tcpServerPort, clientSocket, cache));

                    sc.close();
                }

            case HTTP:
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    String clientIP = clientSocket.getInetAddress().toString();
                    int clientPort = clientSocket.getPort();
                    System.out.println("Client connected on " + clientIP + ":" + clientPort);

                    threadPool.submitTask(new HttpHandler(clientSocket));

                    sc.close();
                }
            default:
                break;
        }
        sc.close();
    }
}