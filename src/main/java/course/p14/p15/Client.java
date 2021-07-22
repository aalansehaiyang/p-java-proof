package course.p14.p15;

/**
 * @author 微信公众号：微观技术
 */
public class Client {
    public static void main(String[] args) {
        OrderContext orderContext = new OrderContext(null);
        orderContext.execute();
        orderContext.execute();
        orderContext.execute();
    }
}

