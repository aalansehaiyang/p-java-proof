package course.p14.p14;

/**
 * @author 微信公众号：微观技术
 */
public interface PromotionStrategy {

    //活动类型
    String promotionType();

    // 活动优惠
    int recommand(String productId);
}
