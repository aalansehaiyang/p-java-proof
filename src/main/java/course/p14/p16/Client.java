package course.p14.p16;


public class Client {
    public static void main(String[] args) {
        Publisher publisher = new PublisherImpl();
        publisher.add(new WeixinObserver());
        publisher.add(new EmailObserver());
        publisher.notify("");
    }
}
