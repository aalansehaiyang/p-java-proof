package course.p14.p9;

public class ConcreteDecorator extends Decorator {

    public ConcreteDecorator(Component component) {
        super(component);
    }

    @Override
    public void execute() {
        System.out.println("装饰器子类 ConcreteDecorator invoke !");
        super.execute();
    }
}
