package proof.chapter3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author onlyone
 */
public class Semaphore1 {

    private static final int       THREAD_COUNT = 30;

    private static ExecutorService threadPool   = Executors.newFixedThreadPool(THREAD_COUNT);
    private static Semaphore       s            = new Semaphore(5);

    private static long            startTime    = System.currentTimeMillis();

    // 相对当前时间
    private static String currentTime() {
        return (System.currentTimeMillis() - startTime) + " ,";
    }

    public static void main(String[] args) {

        for (int i = 0; i < THREAD_COUNT; i++) {

            int a = i;
            threadPool.execute(new Runnable() {

                @Override
                public void run() {
                    try {
                        s.acquire();
                        System.out.println(currentTime() + "do something...." + a);
                        s.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        threadPool.shutdown();

    }

}
