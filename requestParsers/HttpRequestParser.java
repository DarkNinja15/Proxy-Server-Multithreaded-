package requestParsers;

import java.util.HashMap;
import java.util.Map;

import requestPackets.HttpRequestPacket;

public class HttpRequestParser {
    public static HttpRequestPacket parseRequest(String httpRequest) {
        String[] lines = httpRequest.split("\r\n");
        String[] requestLineParts = lines[0].split(" ");
        String method = requestLineParts[0];
        String pathWithQuery = requestLineParts[1];
        String version = requestLineParts[2];

        // Parse path and query
        String[] pathAndQuery = pathWithQuery.split("\\?");
        String path = pathAndQuery[0];
        Map<String, String> queryParams = new HashMap<>();
        if (pathAndQuery.length > 1) {
            String[] queryPairs = pathAndQuery[1].split("&");
            for (String pair : queryPairs) {
                String[] keyValue = pair.split("=");
                queryParams.put(keyValue[0], keyValue[1]);
            }
        }

        // Parse headers
        Map<String, String> headers = new HashMap<>();
        int i = 1;
        while (!lines[i].isEmpty()) {
            String[] headerParts = lines[i].split(": ");
            headers.put(headerParts[0], headerParts[1]);
            i++;
        }

        // Parse body
        StringBuilder body = new StringBuilder();
        i++;
        while (i < lines.length) {
            body.append(lines[i]);
            i++;
        }

        HttpRequestPacket parsedRequest = new HttpRequestPacket(method, path, queryParams, headers, version, body.toString());
        // System.out.println("Parsed Request: " + parsedRequest);
        return parsedRequest;
    }
}
