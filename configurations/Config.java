package configurations;

import java.util.HashSet;

public class Config{
    public static final int serverPort=8080;

    // thread pool configuration
    public static final int corePoolSize=2;
    public static final int maxPoolSize=3;
    public static final long keepAliveTime=1L;

    // cache configuration
    public static final int cacheSize=2;

    // destination server configuration
    public static final String tcpServerHost="localhost";
    public static final int tcpServerPort=8081;


    // select server type
    public static final ServerType serverType=ServerType.HTTP;

    public static final String httpServerHost="http://localhost:3000";


    // blacklisted IPs
    public static final HashSet<String> blacklistedIPs = new HashSet<String>() {{
        // add("/127.0.0.1");
    }};
}