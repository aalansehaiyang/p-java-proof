package proof.chapter10;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author onlyone
 */
public class CompletableFuture3 {

    public static void main(String[] args) {

        // 两个CompletableFuture，当其中任意一个CompletableFuture计算完成时，触发Consumer执行

        Random random = new Random();

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "future1 task!";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "future2 task!";
        });

        CompletableFuture<Void> result = future1.acceptEither(future2, (s) -> {
            System.out.println("最后的结果：" + s);
        });

        try {
            result.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
