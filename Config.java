public class Config{
    static final int serverPort=8080;

    // thread pool configuration
    static final int corePoolSize=1;
    static final int maxPoolSize=3;
    static final long keepAliveTime=1L;

    // cache configuration
    static final int cacheSize=2;

    // destination server configuration
    static final String destServerIP="localhost";
    static final int destServerPort=8081;
}