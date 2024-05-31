package threadPool;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ProxyThreadPool {
    private ThreadPoolExecutor threadPoolExecutor;

    public ProxyThreadPool(int corePoolSize, int maxPoolSize, long keepAliveTime) {
        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.MINUTES,
                new LinkedBlockingQueue<Runnable>());
    }

    public void submitTask(Runnable task) {
        int freeThreads = getFreeThreads();
        System.out.println("free threads: " + freeThreads);
        while (freeThreads == 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            freeThreads = getFreeThreads();
        }
        threadPoolExecutor.submit(task);
    }

    private int getFreeThreads() {
        return threadPoolExecutor.getCorePoolSize() - threadPoolExecutor.getActiveCount();
    }

    public void shutdown() throws InterruptedException {
        threadPoolExecutor.awaitTermination(1, TimeUnit.SECONDS);
    }
}
