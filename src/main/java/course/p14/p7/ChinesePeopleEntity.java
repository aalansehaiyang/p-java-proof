package course.p14.p7;

/**
 * 具体实体
 */
public class ChinesePeopleEntity extends AbstractEntity {

    private String name = "中国人";

    public ChinesePeopleEntity(AbstractBehavior abstractBehavior) {
        super(abstractBehavior);
    }

    @Override
    public void out() {
        System.out.println("我是 " + name + " , 我" + abstractBehavior.action(name));
    }
}
