package course.p14.p9;


public class Client {

    public static void main(String[] args) {
        Component component = new ConcreteComponent();
        ConcreteDecorator decorator = new ConcreteDecorator(component);
        decorator.execute();
    }
}
