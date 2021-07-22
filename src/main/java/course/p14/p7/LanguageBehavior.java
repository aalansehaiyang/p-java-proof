package course.p14.p7;

/**
 * 关于语言行为
 */
public class LanguageBehavior implements AbstractBehavior {
    @Override
    public String action(String name) {
        if ("中国人".equals(name)) {
            return "说 汉语";
        } else if ("美国人".equals(name)) {
            return "说 英语";
        }
        return null;
    }
}
