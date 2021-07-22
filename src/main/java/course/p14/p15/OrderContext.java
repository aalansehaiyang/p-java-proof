package course.p14.p15;

import course.p14.p15.state.MakeOrderState;

/**
 * @author 微信公众号：微观技术
 * 订单上下文
 */
public class OrderContext {
    private OrderState currentOrderState;

    public OrderContext(OrderState currentOrderState) {
        if (currentOrderState == null) {
            this.currentOrderState = new MakeOrderState();
        } else {
            this.currentOrderState = currentOrderState;
        }
    }

    public void setCurrentOrderState(OrderState currentOrderState) {
        this.currentOrderState = currentOrderState;
    }

    public void execute() {
        currentOrderState.handle(this);
    }

}

