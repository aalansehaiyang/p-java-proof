package proof.chapter10;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author onlyone
 */
public class CompletableFuture10 {

    private static Long startTime = System.currentTimeMillis();

    public static void main(String[] args) {

        Supplier<String> supplier1 = () -> {
            record("supplier1");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello";
        };

        Function<String, String> function1 = s -> {
            record("function1");
            return s + " CompletableFuture10";
        };


        Function<String, String> function2 = s -> {
            record("function2");
            return s + " end!";
        };


//        CompletableFuture<String> f0 = CompletableFuture.supplyAsync(supplier1) // ①
//                                                        .thenApply(function1) // ②
//                                                        .thenApply(function2);// ③
//
//        System.out.println(f0.join());


        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(supplier1) // ①
                .thenApplyAsync(function1) // ②
                .thenApplyAsync(function2);// ③

        System.out.println(f1.join());

//        87，线程：ForkJoinPool.commonPool-worker-1 supplier1
//        2091，线程：ForkJoinPool.commonPool-worker-1 function1
//        2092，线程：ForkJoinPool.commonPool-worker-2 function2
//        Hello CompletableFuture10 end!

    }

    public static void record(String methodFlag) {
        System.out.println((System.currentTimeMillis() - startTime) + "，线程：" + Thread.currentThread().getName() + " "
                           + methodFlag);
    }
}
