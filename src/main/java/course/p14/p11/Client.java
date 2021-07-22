package course.p14.p11;


public class Client {
    public static void main(String[] args) {
        AbstractSubject subject = new RealSubject();
        AbstractSubject proxy = new Proxy(subject);
        proxy.execute();
    }
}
