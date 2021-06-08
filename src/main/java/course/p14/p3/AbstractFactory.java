package course.p14.p3;

/**
 * @author 微信公众号：微观技术
 */
public abstract class AbstractFactory {

    // 生产电视
    abstract Object createTV();

    // 生产洗衣机
    abstract Object createWasher();

    // 生产冰箱
    abstract Object createRefrigerator();

}
