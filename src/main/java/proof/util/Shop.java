package proof.util;

import lombok.Data;

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

}
