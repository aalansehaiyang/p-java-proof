package course.p14.p9;


public class Decorator extends Component {

    protected Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void execute() {
        component.execute();
    }
}
