package course.p14.p7;

/**
 * @author 微信公众号：微观技术
 * 抽象实体
 */
public abstract class AbstractEntity {
    protected AbstractBehavior abstractBehavior;

    public AbstractEntity(AbstractBehavior abstractBehavior) {
        this.abstractBehavior = abstractBehavior;
    }

    public abstract void out();

}
