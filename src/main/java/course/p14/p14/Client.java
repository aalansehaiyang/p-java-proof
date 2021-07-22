package course.p14.p14;

/**
 * @author 微信公众号：微观技术
 */
public class Client {
    public static void main(String[] args) {
        Context context = new Context();
        context.recommand("FullReduce", "100");
        System.out.println("分割线--------------");
        context.recommand("FullSend", "200");
    }
}
