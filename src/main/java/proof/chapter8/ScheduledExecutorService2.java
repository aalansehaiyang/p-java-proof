package proof.chapter8;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author onlyone
 */
public class ScheduledExecutorService2 {

    public static void main(String[] args) {

        MyScheduledRunnable runnable = new MyScheduledRunnable();
        // 业务运行2秒
        runnable.setBizCostTime(2000L);

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        // 循环延迟任务，首次延迟1秒，开始执行任务
        // 然后，每次隔1秒再次运行任务，但如果上一次任务还没有结束（因为每一次任务需要2秒），会等它结束后，才执行下一次任务
        service.scheduleAtFixedRate(runnable, 1, 1, TimeUnit.SECONDS);

    }
}
