package course.p14.p14;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 微信公众号：微观技术
 */
public class Context {

    private static List<PromotionStrategy> promotionStrategyList = new ArrayList<>();

    static {
        promotionStrategyList.add(new FullReducePromotion());
        promotionStrategyList.add(new FullSendPromotion());
    }

    public void recommand(String promotionType, String productId) {

        PromotionStrategy promotionStrategy = null;
        // 找到对应的策略类
        for (PromotionStrategy temp : promotionStrategyList) {
            if (temp.promotionType().equals(promotionType)) {
                promotionStrategy = temp;
            }
        }
        // 策略子类调用
        promotionStrategy.recommand(productId);
    }
}
