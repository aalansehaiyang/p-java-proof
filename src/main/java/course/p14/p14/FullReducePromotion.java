package course.p14.p14;

/**
 * @author 微信公众号：微观技术
 */
public class FullReducePromotion implements PromotionStrategy {
    @Override
    public String promotionType() {
        return "FullReduce";
    }

    @Override
    public int recommand(String productId) {
        System.out.println("参加满减活动");
        return 0;
    }
}
