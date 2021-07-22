package course.p14.p7;


public class USAPeopleEntity extends AbstractEntity {

    private String name = "美国人";

    public USAPeopleEntity(AbstractBehavior abstractBehavior) {
        super(abstractBehavior);
    }

    @Override
    public void out() {
        System.out.println("我是 " + name + " , 我" + abstractBehavior.action(name));
    }
}