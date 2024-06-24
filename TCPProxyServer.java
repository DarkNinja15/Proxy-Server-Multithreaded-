import threadPool.ProxyThreadPool;
import lruCache.LRUcache;
import requestHandler.TcpHandler;
import requestHandler.httpHandler.HttpHandler;
import requestPackets.HttpRequestPacket;
import requestParsers.HttpRequestParser;

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
                    threadPool.submitTask(new TcpHandler(Config.destServerIP,
                            Config.destServerPort, clientSocket, cache));

                    sc.close();
                }

            case HTTP:
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    String clientIP = clientSocket.getInetAddress().toString();
                    int clientPort = clientSocket.getPort();
                    System.out.println("Client connected on " + clientIP + ":" + clientPort);

                    BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    OutputStream output = clientSocket.getOutputStream();

                    StringBuilder request = new StringBuilder();
                    String line;
                    int contentLength = 0;

                    // Read request headers
                    while (!(line = input.readLine()).isBlank()) {
                        request.append(line).append("\r\n");
                        if (line.startsWith("Content-Length")) {
                            contentLength = Integer.parseInt(line.split(": ")[1]);
                        }
                    }
                    request.append("\r\n");

                    // Read request body
                    char[] body = new char[contentLength];
                    input.read(body, 0, contentLength);
                    request.append(body);

                    // Parse the request
                    String httpRequest = request.toString();
                    System.out.println("Received HTTP request:\n" + httpRequest);
                    HttpRequestPacket httpPacket = HttpRequestParser.parseRequest(httpRequest);

                    HttpHandler httpHandler= new HttpHandler(httpPacket);

                    
                    // Respond to client
                    output.write((String.valueOf(httpHandler.getResponse().statusCode())+" "+httpHandler.getResponse().body()).getBytes());
                    output.flush();

                    sc.close();
                }
            default:
                break;
        }
        sc.close();
    }
}