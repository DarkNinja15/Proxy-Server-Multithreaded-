package client;
import java.net.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class client {
    public static void main(String args[]) throws IOException {
        String hostname="localhost";
        int serverPort=8080;

        Socket clientSocket=new Socket(hostname,serverPort);
        System.out.println("Connected to server:"+hostname+" "+serverPort);

        // send data to server
        String body = "{\"username\": \"johndoe\", \"email\": \"johndoe@example.com\"}";
        String httpRequest = "POST /api/users/123/profile?include=details&expand=all HTTP/1.1\r\n"
                               + "Host: example.com\r\n"
                               + "User-Agent: JavaClient/1.0\r\n"
                               + "Content-Type: application/json\r\n"
                               + "Content-Length: " + body.length() + "\r\n"
                               + "\r\n"
                               + body;
        OutputStream output=clientSocket.getOutputStream();
        output.write(httpRequest.getBytes());
        output.flush();



        InputStream inputStream = clientSocket.getInputStream();
        byte[] data = new byte[1024];
        inputStream.read(data);
        System.out.println("Data from server: "+new String(data));

        clientSocket.close();
    }
}

// equivalent curl command for this request:-

// curl -X POST "http://example.com/api/users/123/profile?include=details&expand=all" \
//      -H "Host: example.com" \
//      -H "User-Agent: JavaClient/1.0" \
//      -H "Content-Type: application/json" \
//      -d '{"username": "johndoe", "email": "johndoe@example.com"}'

