package course.p14.p3;

/**
 * @author 微信公众号：微观技术
 */
public class XiaomiFactory extends AbstractFactory {
    @Override
    Object createTV() {
        return null;
    }

    @Override
    Object createWasher() {
        return null;
    }

    @Override
    Object createRefrigerator() {
        return null;
    }

    public static void main(String[] args) {
        Object a = 2.4f;
        if (a instanceof Float) {
            System.out.println("dddd");
        }
    }

}
