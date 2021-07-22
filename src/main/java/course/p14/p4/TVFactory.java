package course.p14.p4;

/**
 * @author 微信公众号：微观技术
 */
public class TVFactory {

    public static ITV getTV(String name) {

        switch (name) {
            case "haier":
                return new HaierTV();
            case "xiaomi":
                return new XiaomiTV();
            default:
                return null;
        }
    }

}
