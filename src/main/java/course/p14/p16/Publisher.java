package course.p14.p16;

/**
 * @author 微信公众号：微观技术
 * <p>
 * 被观察者
 */
public interface Publisher {
    void add(Observer observer);
    void remove(Observer observer);
    void notify(Object object);
}
