package course.p14.p15.state;

import course.p14.p15.OrderContext;
import course.p14.p15.OrderState;

/**
 * @author 微信公众号：微观技术
 * 创建订单
 */
public class MakeOrderState implements OrderState {
    public static MakeOrderState instance = new MakeOrderState();

    @Override
    public void handle(OrderContext context) {

        System.out.println("1、创建订单");
        context.setCurrentOrderState(PayOrderState.instance);
    }
}
