Proxy Server


This project is a simple proxy server that forwards requests to a target server.
Cooked in Java, this project uses the `java.net` package to create a server socket and listen for incoming requests. When a request is received, the server creates a new thread to handle the request and forwards it to the target server. The response from the target server is then sent back to the client.
