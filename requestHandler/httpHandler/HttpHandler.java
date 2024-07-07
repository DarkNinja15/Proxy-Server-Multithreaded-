package requestHandler.httpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import configurations.Config;
import requestPackets.HttpRequestPacket;
import requestParsers.HttpRequestParser;

public class HttpHandler implements Runnable {
    private HttpRequestPacket httpPacket;
    private HttpClient client;
    private HttpResponse<String> response;
    private OutputStream output;

    public HttpHandler(Socket clientSocket) throws IOException {
        this.output = clientSocket.getOutputStream();
        client = HttpClient.newHttpClient();
        BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

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
        this.httpPacket = HttpRequestParser.parseRequest(httpRequest);
    }

    @Override
    public void run() {
        try {
            System.out.println("method=" + httpPacket.method);
            if (httpPacket.method.equals("GET")) {
                getRequest();
            } else if (httpPacket.method.equals("POST")) {
                postRequest();
            } else if (httpPacket.method.equals("PUT")) {
                putRequest();
            } else if (httpPacket.method.equals("DELETE")) {
                deleteRequest();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }finally {
            try {
                sendResponse();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private HttpResponse<String> getResponse() {
        return response;
    }

    private void sendResponse() throws IOException{
        output.write((String.valueOf(getResponse().statusCode())+" "+getResponse().body()).getBytes());
        output.flush();
    }

    private void getRequest() throws IOException, InterruptedException {
        HttpRequest request = buildRequestBuilder().GET().build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());
    }

    private void postRequest() throws IOException, InterruptedException {
        HttpRequest request = buildRequestBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(httpPacket.body))
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());

    }

    private void putRequest() throws IOException, InterruptedException {
        HttpRequest request = buildRequestBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(httpPacket.body))
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());
    }

    private void deleteRequest() throws IOException, InterruptedException {
        HttpRequest request = buildRequestBuilder().DELETE().build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());
    }

    private HttpRequest.Builder buildRequestBuilder() {
        String fullUrl = Config.httpServerHost + httpPacket.path + "?" + buildQueryString();
        HttpRequest.Builder request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl));

        httpPacket.headers.forEach(request::header);

        return request;
    }

    private String buildQueryString() {
        return httpPacket.queryParams.entrySet().stream()
                .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
    }
}
