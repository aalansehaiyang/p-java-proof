package course.p14.p4;


public class Client {

    public static void main(String[] args) {
        ITV tv = TVFactory.getTV("xiaomi");
        Object result = tv.desc();
        System.out.println(result);
    }
}
