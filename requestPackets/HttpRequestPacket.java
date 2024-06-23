package requestPackets;

import java.util.Map;

public class HttpRequestPacket {
    public String method;
    public String path;
    public Map<String, String> queryParams;
    public Map<String, String> headers;
    public String version;
    public String body;

    public HttpRequestPacket(String method, String path, Map<String, String> queryParams, Map<String, String> headers,
            String version, String body) {
        this.method = method;
        this.path = path;
        this.queryParams = queryParams;
        this.headers = headers;
        this.version = version;
        this.body = body;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", queryParams=" + queryParams +
                ", headers=" + headers +
                ", version='" + version + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
