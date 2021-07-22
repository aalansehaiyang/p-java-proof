package course.p14.p15.state;

import course.p14.p15.OrderContext;
import course.p14.p15.OrderState;

/**
 * @author 微信公众号：微观技术
 * 确认收货
 */
public class ReceiveGoodOrderState implements OrderState {
    public static ReceiveGoodOrderState instance = new ReceiveGoodOrderState();

    @Override
    public void handle(OrderContext context) {

        System.out.println("3、确认收到货物");
    }
}