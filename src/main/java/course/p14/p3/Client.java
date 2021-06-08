package course.p14.p3;

/**
 * @author 微信公众号：微观技术
 */
public abstract class Client {

    private Object tv;
    private Object washer;
    private Object refrigerator;

    public Client(AbstractFactory factory) {
        tv = factory.createTV();
        washer = factory.createWasher();
        refrigerator = factory.createRefrigerator();

    }
}

