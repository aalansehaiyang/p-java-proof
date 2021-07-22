package course.p14.p9;

public class ConcreteComponent extends Component {
    @Override
    public void execute() {
        System.out.println("具体子类 ConcreteComponent invoke !");
    }
}
