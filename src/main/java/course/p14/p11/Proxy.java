package course.p14.p11;


public class Proxy implements AbstractSubject {

    private AbstractSubject abstractSubject;

    public Proxy(AbstractSubject abstractSubject) {
        this.abstractSubject = abstractSubject;
    }

    @Override
    public void execute() {
        System.out.println("老板给Tom哥分配工作了。。。");
        abstractSubject.execute();
    }
}
