package proof.chapter10;

import com.alibaba.fastjson.JSON;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author onlyone
 */
public class CompletableFuture1 {

    public static void main(String[] args) {

//        m1();
        m2();

    }


    public static void m1(){

        /**
         * 两个字符串拼接，再转换为int
         */
        CompletableFuture future=  CompletableFuture.supplyAsync(()->"100")
                .thenCompose(s->CompletableFuture.supplyAsync(()->s+"200"))
                .thenCompose(s->CompletableFuture.supplyAsync(()->Integer.parseInt(s)));

        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void m2(){
         CompletableFuture.supplyAsync(()->"hello world")
                .thenCompose(s->CompletableFuture.supplyAsync(()->s.split("")))
                .thenAccept(sArray-> System.out.println(JSON.toJSONString(sArray)));

        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
