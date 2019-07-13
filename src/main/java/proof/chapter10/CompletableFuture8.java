package proof.chapter10;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


import proof.util.Shop;

/**
 * @author onlyone
 */
public class CompletableFuture8 {

    static List<Shop> shops;

    public static void main(String[] args) {
        // 造数据
        shops = Arrays.asList(new Shop(1L, "shop1"), new Shop(2L, "shop2"), new Shop(3L, "shop3"),
                              new Shop(4L, "shop4"));

        // 开始时间
        startTime = System.currentTimeMillis();

        // 异步
        shops.stream()
                .map(shop->{
                    return CompletableFuture.supplyAsync(()->{
                        shop.bizHandle();
                        return shop;
                    });
                })
                .map(f->{
                   return f.thenAccept(o-> System.out.println(o));
                })
                .collect(Collectors.toList());  //只有加上终止状态才会执行

        recordTime("任务结束");

        // 阻止JVM提前退出
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    private static Long startTime;

    private static void recordTime(String stage) {
        System.out.println(stage + "，已耗时毫秒：" + (System.currentTimeMillis() - startTime));
    }
}
