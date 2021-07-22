package course.p14.p7;

/**
 * 关于食物的行为
 */
public class FoodBehavior implements AbstractBehavior {

    @Override
    public String action(String name) {
        if ("中国人".equals(name)) {
            return "吃 饺子";
        } else if ("美国人".equals(name)) {
            return "吃 汉堡";
        }
        return null;
    }
}
