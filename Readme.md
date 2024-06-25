# Proxy Server

## Introduction

Welcome to the Multi-Threaded Proxy Server project! This Java-based application is designed to act as an intermediary between clients and servers, handling multiple client requests concurrently to ensure high performance and reliability.


***
## Stats

| Agent | Test 1 | Test 2 | Test 3 | Test 4 | Test 5 | Average |
|----|----|----|----|----|----|----|
| Proxy | 9ms | 6ms | 8ms | 14 ms | 9ms | 9.2ms |
| Direct | 16ms | 7ms | 5ms | 5ms | 5ms | 7.6ms |

The table above shows the average time it takes to send a POST request to the server using the proxy server and a direct connection.


As we can see there is a very minimal difference between the two methods. The average time for the proxy is 9.2ms and the average time for the direct connection is 7.6ms. The direct connection is just faster by 1.6ms.

The test is done by sending a POST request to the server and measuring the time it takes to get a response. The test is done 5 times and the average time is calculated. The test is done on a local server and the client is on the same network as the server.

The POST request sent has a body,header and query parameters. The body is a JSON object, the header is a key-value pair and the query parameters are key-value pairs.
***

## Connect your own server to the proxy server

### Why connect your own server?

Connecting your own server to the proxy server allows you to test the proxy server with your own server and see how it performs. You can send requests to your server through the proxy server and observe the response times and performance.

### How to connect your own server?

To connect your own server to the proxy server, you need to follow these steps:

1. Set up your own server: You need to have your own server running on a specific host and port. The server can be a TCP server, an HTTP server, or any other type of server that you want to test with the proxy server.(TCP connection does not have all the features as HTTP connection)

2. Update the configurations: You need to update the configurations in the `Config.java` file to specify the host and port of your server. You can specify the host and port for the TCP server or the HTTP server, depending on the type of server you are using.

3. Start your server: You need to start your server before starting the proxy server. Make sure that your server is running and listening for incoming connections on the specified host and port.

4. Start the proxy server: Once your server is running, you can start the proxy server by compiling and running the `TCPProxyServer.java` file. The proxy server will listen for incoming client connections and forward the requests to your server.

5. Send requests to the proxy server: You can send requests to the proxy server using the client program or any other tool that you prefer. The proxy server will forward the requests to your server, and you can observe the response times and performance.

NOTE: You need to establish a TCP connection between the client and the proxy server no matter what type of server you are using.

You can see the example in [client]([Config.java](https://github.com/DarkNinja15/Proxy-Server-Multithreaded-/blob/main/configurations/Config.java)) floder.

## Working

##### Note: The project is currently under development and the following steps may not work as expected.

To get started, there are a few prerequisites that need to be installed on your system:

- Java Development Kit (JDK)
- Node.js and npm
- Curl (optional)

for windows users, you can download the JDK from [here](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) and Node.js from [here](https://nodejs.org/en/download/)

for Debian-based users, you can install the JDK using the following command:

```bash
sudo apt install default-jdk && sudo apt install default-jre
```

and Node.js using the following command:

```bash
sudo apt install nodejs
```

for arch-based users, you can install the JDK using the following command:

```bash
sudo pacman -S jdk-openjdk && sudo pacman -S jre-openjdk
```

and Node.js using the following command:

```bash
sudo pacman -S nodejs
```

Once you have installed the required software, you can follow the steps below to run the Multi-Threaded Proxy Server:

**Step 1: Clone the repository**

```bash
git clone https://github.com/DarkNinja15/Proxy-Server-Multithreaded-.git
```

**Step 2: Set up configurations**

Edit the `Config.java` file in the root directory to specify the following configurations:


_Proxy Server Configurations:_
- `serverPort`: The port number on which the proxy server listens for incoming client connections.

_Thread Pool Configurations:_
- `corePoolSize`: The number of worker threads to keep in the thread pool, even if they are idle. (Minimum value: 1)
- `maxPoolSize`: The maximum number of threads to allow in the thread pool. (Minimum value: 1)
- `keepAliveTime`: The maximum time that excess idle threads will wait for new tasks before terminating.

_Caching Configurations:_
- `cacheSize`: The maximum number of responses that can be stored in the cache.

_Server Type:_
- `serverType`: The type of server to run (for now only tcp and http option are available).

_TCP Server Configurations:_
- `tcpServerHost`: The host name of the destination server to which client requests are forwarded.
- `tcpServerPort`: The port number of the destination server.

_HTTP Server Configurations:_
- `httpServerHost`: The host name and the port of the destination server to which client requests are forwarded.


*Note: The destination or main server should be running on the specified host and port before starting the proxy server.*

**Step 3: Run the Main Server**

For TCP Server:
```bash
cd mainServer/node_tcp_server/ && npm install && npm run dev
```

For HTTP Server:
```bash
cd mainServer/node_express_server/ && npm install && npm run dev
```

**Step 4: Compile and run the Proxy Server**

```bash
javac TCPProxyServer.java && java TCPProxyServer
```

**Step 5: Start the Client**

```bash
cd client && javac client.java && java client
```

OR 

You can also use the curl command to send a request to the proxy server:

```bash
curl http://localhost:<PROXY_SERVER_PORT>
```
Replace <PROXY_SERVER_PORT> with the port number on which the proxy server is running.

Find it in the [Config.java](https://github.com/DarkNinja15/Proxy-Server-Multithreaded-/blob/main/configurations/Config.java) file.

***

## Project Journey


### Intial Planning and Research

To build this project, my first challange was to choose a programing language.
In the past I have already tried building such applilcations with languages like C++ but while building it, it felt very hard.

This time I was already learning java...and I thought why not give it a try and started building it with java.

I planned the need and basic features I need to put into this project through visualizations which can be found in the Visualizations file.

### Building the Proxy Server

#### Step 1: Setting up the Server

Firstly I set up a TCP server that listens for incoming client connections on a specified port. This server is responsible for accepting client connections and creating a new thread to handle each client request.

#### Step 2: Handling Client Requests (Multi-Threading)

When a client connects to the server, the server creates a new thread to handle the client request. This thread reads the client request, parses the request headers, and forwards the request to the destination server specified in the request.

#### Step 3: Forwarding Requests to Destination Server

The proxy server acts as an intermediary between the client and the destination server. It reads the client request, forwards the request to the destination server, reads the response from the destination server.

#### Step 4: Need for a Thread Pool

To handle multiple client requests concurrently, I implemented a thread pool that manages a pool of worker threads. When a client request arrives, the server assigns the request to an available worker thread from the thread pool.

#### Step 5: Caching Responses

To improve performance and reduce latency, I implemented a caching mechanism that stores responses from the destination server. When a client request arrives, the proxy server checks if the response is already cached and serves the cached response if available.
This is done using the LRU (Least Recently Used) cache eviction policy.

***

## Conclusion

The Multi-Threaded Proxy Server project was a great learning experience for me. I got to explore various concepts such as multi-threading, socket programming, caching, and thread pooling in Java. I faced several challenges along the way, but I was able to overcome them by researching and experimenting with different approaches.



