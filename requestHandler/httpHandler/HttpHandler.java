package requestHandler.httpHandler;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import configurations.Config;
import requestPackets.HttpRequestPacket;

public class HttpHandler {
    private HttpRequestPacket httpPacket;
    private HttpClient client;
    private HttpResponse<String> response;

    public HttpHandler(HttpRequestPacket httpPacket) throws IOException {
        this.httpPacket = httpPacket;
        client = HttpClient.newHttpClient();
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
        }
    }

    public HttpResponse<String> getResponse() {
        return response;
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
