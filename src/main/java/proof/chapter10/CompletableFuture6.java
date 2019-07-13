package proof.chapter10;

import java.util.concurrent.CompletableFuture;

/**
 * @author onlyone
 */
public class CompletableFuture6 {

    public static void main(String[] args) {

        // 只有当CompletableFuture抛出异常时，才会触发该方法
        CompletableFuture.supplyAsync(() -> "hello word")
                .thenApply(s->{
//                    s=null;
                    return s.length();
                })
                .thenAccept(i-> System.out.println(i))
                .exceptionally(e->{
                    System.out.println("异常："+e);
                    return null;
                });
    }

}
