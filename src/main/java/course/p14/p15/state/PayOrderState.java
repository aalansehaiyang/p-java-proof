package course.p14.p15.state;

import course.p14.p15.OrderContext;
import course.p14.p15.OrderState;

/**
 * @author 微信公众号：微观技术
 * 付款
 */
public class PayOrderState implements OrderState {
    public static PayOrderState instance = new PayOrderState();

    @Override
    public void handle(OrderContext context) {

        System.out.println("2、支付宝付款");
        context.setCurrentOrderState(ReceiveGoodOrderState.instance);
    }
}
