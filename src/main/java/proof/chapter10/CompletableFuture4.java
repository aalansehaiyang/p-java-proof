package proof.chapter10;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author onlyone
 */
public class CompletableFuture4 {

    // 多个任务并行执行。返回的是CompletableFuture<Void>，
    // 并不能组合前面多个 CompletableFuture 的计算结果，需要借助Stream来组合结果
    public static void main(String[] args) {
        startTime = System.currentTimeMillis();
        Random random = new Random();

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Future1 task!";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Future2 task!";
        });

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Future3 task!";
        });

        CompletableFuture.allOf(future1, future2, future3);

        List<String> list = Stream.of(future1, future2,
                                      future3).map(CompletableFuture::join).collect(Collectors.toList());

        recordTime("结果：" + list);

    }

    private static Long startTime;

    private static void recordTime(String stage) {
        System.out.println(stage + "，已耗时毫秒：" + (System.currentTimeMillis() - startTime));
    }

}
