# Proxy Server

## Introduction

Welcome to the Multi-Threaded Proxy Server project! This Java-based application is designed to act as an intermediary between clients and servers, handling multiple client requests concurrently to ensure high performance and reliability.


***
## Working

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

_Destination Server Configurations:_
- `destServerIP`: The host name of the destination server to which client requests are forwarded.
- `destServerPort`: The port number of the destination server.

*Note: The destination or main server should be running on the specified host and port before starting the proxy server.*

**Step 3: Run the Main Server**

```bash
cd mainServer/ && npm install && npm run dev
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

Find it in the [Config.java](https://github.com/DarkNinja15/Proxy-Server-Multithreaded-/blob/main/Config.java) file.

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



