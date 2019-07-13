package proof.util;

import lombok.Data;

import java.util.Random;

/**
 * @author onlyone
 */
@Data
public class Shop {

    private Long   id;
    private String name;
    private Double price;

    public Shop(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public Double computationPrice(Long productId) {
        return 1000 * (id * 0.1);
    }

    // 模拟业务执行，采用随机数休眠一段时间
    public void bizHandle() {
        try {
            Thread.sleep(new Random().nextInt(2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
