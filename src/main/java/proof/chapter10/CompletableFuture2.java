package proof.chapter10;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author onlyone
 */
public class CompletableFuture2 {
    public static void main(String[] args) {

        /**
         * 两个字符串拼接，再转换为int
         */
        CompletableFuture<String> future=  CompletableFuture.supplyAsync(()->"100");

        CompletableFuture<String> future2=  CompletableFuture.supplyAsync(()->"200");

        CompletableFuture<Integer> future3=future.thenCombine(future2,(s1,s2)->{
             return Integer.parseInt(s1+s2);
        });

        try {
            System.out.println(future3.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
