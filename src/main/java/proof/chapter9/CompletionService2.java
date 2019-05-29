package proof.chapter9;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author onlyone
 */
public class CompletionService2 {

    private static Long startTime;

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // 创建 CompletionService
        CompletionService<String> cs = new ExecutorCompletionService<>(executor);

        recordTime("启动");

        TaskCallable taskCallable1 = new TaskCallable("任务-1", 3000L);
        TaskCallable taskCallable2 = new TaskCallable("任务-2", 5000L);
        TaskCallable taskCallable3 = new TaskCallable("任务-3", 2000L);

        List<Future<String>> futureList = new ArrayList<>();
        futureList.add(cs.submit(taskCallable1));
        futureList.add(cs.submit(taskCallable2));
        futureList.add(cs.submit(taskCallable3));
        recordTime("三次任务全部提交");

        for (int i = 0; i < 3; i++) {
            String r = cs.take().get();
            recordTime("得到结果：" + r);
            // 简单地通过判空来检查是否成功返回
            if (r != null) {
                break;
            }
        }

        for (Future<String> f : futureList) {
            f.cancel(true);
        }

    }

    private static void recordTime(String stage) {
        System.out.println(stage + "，已耗时毫秒：" + (System.currentTimeMillis() - startTime));
    }

}
