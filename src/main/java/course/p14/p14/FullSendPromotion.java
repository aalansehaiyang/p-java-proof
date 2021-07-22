package course.p14.p14;

/**
 * @author 微信公众号：微观技术
 */
public class FullSendPromotion implements PromotionStrategy {
    @Override
    public String promotionType() {
        return "FullSend";
    }

    @Override
    public int recommand(String productId) {
        System.out.println("参加满送活动");
        return 0;
    }
}
