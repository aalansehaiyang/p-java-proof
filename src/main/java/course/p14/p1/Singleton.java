package course.p14.p1;

/**
 * @author 微信公众号：微观技术
 */
public class Singleton {

    private static Singleton instance = new Singleton();

    // 让构造函数为 private，这样该类就不会被实例化
    private Singleton() {
    }

    // 获取唯一可用的对象
    public static Singleton getInstance() {
        return instance;
    }
}
