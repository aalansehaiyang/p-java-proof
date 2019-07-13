package proof.chapter10;

import com.alibaba.fastjson.JSON;
import proof.util.Shop;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author onlyone
 */
public class CompletableFuture7 {

    static List<Shop> shops;

    public static void main(String[] args) {
        // 造数据
        shops = Arrays.asList(new Shop(1L, "shop1"), new Shop(2L, "shop2"), new Shop(3L, "shop3"),
                              new Shop(4L, "shop4"), new Shop(5L, "shop5"), new Shop(6L, "shop6"));

        startTime = System.currentTimeMillis();

        List<Shop> result = null;

        // 已耗时毫秒：1613
//        result = findPriceTest1(1L);

        // 已耗时毫秒：1610
         result = findPriceTest2(1L);

        recordTime(JSON.toJSONString(result));

    }

    // 实现一
    private static List<Shop> findPriceTest1(Long productId) {

        List<CompletableFuture<Shop>> priceFutures = shops.stream().map(shop -> {
            return CompletableFuture.supplyAsync(() -> {
                // 计算价格
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                shop.setPrice(shop.computationPrice(productId));
                return shop;
            });
        }).collect(Collectors.toList());

        return priceFutures.stream().map(f -> f.join()).collect(Collectors.toList());
    }

    // 实现二
    private static List<Shop> findPriceTest2(Long productId) {

        // 并行流
        List<Shop> result = shops.parallelStream().map(shop -> {
            // 计算价格
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            shop.setPrice(shop.computationPrice(productId));
            return shop;
        }).collect(Collectors.toList());

        return result;
    }

    private static Long startTime;

    private static void recordTime(String stage) {
        System.out.println(stage + "，已耗时毫秒：" + (System.currentTimeMillis() - startTime));
    }
}
