package course.p14.p15;

/**
 * @author 微信公众号：微观技术
 * 订单状态，接口定义（扩展实现若干不同状态的子类）
 */
public interface OrderState {

    void handle(OrderContext context);
}
