package course.p14.p16;


import java.util.ArrayList;
import java.util.List;

public class PublisherImpl implements Publisher {
    private List<Observer> observerList = new ArrayList<>();

    @Override
    public void add(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void remove(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notify(Object object) {
        System.out.println("创建订单，并发送通知事件");
        observerList.stream().forEach(t -> t.handle());
    }
}
