package proof.chapter8;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author onlyone
 */
public class ScheduledExecutorService3 {

    public static void main(String[] args) {

        MyScheduledRunnable runnable = new MyScheduledRunnable();
        // 业务运行2秒
        runnable.setBizCostTime(2000L);

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        // 循环延迟任务，首次延迟1秒，开始执行任务
        // 然后，待任务结束后，间隔1秒开始执行下一次任务
        service.scheduleWithFixedDelay(runnable, 1, 1, TimeUnit.SECONDS);

    }

}
