package course.p14.p18;


import lombok.Data;

/**
 * @author 微信公众号：微观技术
 * 原始对象
 */
@Data
public class Originator {
    private Long id;
    private String productName;
    private String picture;

    // 创建快照
    public Memento bak() {
        return new Memento(id, productName, picture);
    }

    //恢复
    public void restore(Memento m) {
        this.id = m.getId();
        this.productName = m.getProductName();
        this.picture = m.getPicture();
    }
}
