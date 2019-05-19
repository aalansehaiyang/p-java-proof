package proof.chapter8;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author onlyone
 */
public class ScheduledExecutorService1 {

    public static void main(String[] args) {

        MyScheduledRunnable runnable = new MyScheduledRunnable();
        // 业务运行2秒
        runnable.setBizCostTime(2000L);

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        // 单次任务，延迟1秒，开始执行任务
        service.schedule(runnable, 1, TimeUnit.SECONDS);

    }
}
