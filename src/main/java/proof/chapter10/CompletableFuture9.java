package proof.chapter10;

import java.util.concurrent.CompletableFuture;

/**
 * @author onlyone
 */
public class CompletableFuture9 {

    public static void main(String[] args) {
        CompletableFuture<String> future1 = new CompletableFuture();

        // future1.thenAccept(t -> {
        // System.out.println("加工后的结果：" + t);
        // });

        future1.thenApply(t -> {
            String result = "加工后的结果：" + t;
            System.out.println(result);
            return result;
        });

        // 休眠一段时间
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 触发任务的执行
        future1.complete("TomGE");

        System.out.println("任务结束");

        // 休眠一段时间
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
