# Proxy Server

## Introduction

Welcome to the Multi-Threaded Proxy Server project! This Java-based application is designed to act as an intermediary between clients and servers, handling multiple client requests concurrently to ensure high performance and reliability.

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

