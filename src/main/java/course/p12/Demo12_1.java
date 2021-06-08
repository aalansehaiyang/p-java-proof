//package course.p12;
//
//import javax.annotation.Resource;
//
///**
// * @author 微信公众号：微观技术
// */
//
//public class Demo12_1 {
//
//
//    class WeixinPay {
//        public Object pay(Object requestParam) {
//            // 请求微信完成支付
//            // 省略。。。。
//            return new Object();
//        }
//    }
//
//
//    class PayGateway {
//        public Object pay(Object requestParam) {
//
//            if (微信支付) {
//                // 请求微信完成支付
//                // 省略。。。。
//            }
//            esle if (支付宝) {
//                // 请求支付宝完成支付
//                // 省略。。。。
//            }
//            esle if (云闪付) {
//                // 请求云闪付完成支付
//                // 省略。。。。
//            }
//
//            // 其他，不同渠道的个性化参数的抽取，转换，适配
//            // 可能有些渠道一次支付需要多次接口请求，获取一些前置准备参数
//            // 省略。。。。
//            return new Object();
//        }
//    }
//
//    class PayGateway {
//
//        @Resource
//        List<AbstractPayChannel> payChannelList；
//
//        public Object pay(Object requestParam) {
//            for(AbstractPayChannel channel:payChannelList){
//                channel.pay(requestParam);
//            }
//        }
//    }
//
//    abstract class AbstractPayChannel {
//        public Object pay(Object requestParam) {
//            // 抽象方法
//        }
//    }
//
//    class AliayPayChannel extends  AbstractPayChannel{
//        public Object pay(Object requestParam) {
//            // 根据请求参数，如果选择支付宝支付，处理后续流程
//            // 支付宝处理
//        }
//    }
//    class WeixinPayChannel extends  AbstractPayChannel{
//        public Object pay(Object requestParam) {
//            // 根据请求参数，如果选择微信支付，处理后续流程
//            // 微信处理
//        }
//    }
//
//    public interface UserService{
//        // 注册接口
//        Object register(Object param);
//        // 登录接口
//        Object login(Object param);
//        // 查询用户信息
//        Object queryUserInfoById(Long uid);
//    }
//
//    public interface BopsUserService{
//        // 删除用户
//        Object deleteById(Long uid);
//    }
//
//    public interface ProjectService{
//        // 加入一个项目
//        void addProject (Object param);
//        // 统计一个用户参加过多少个项目
//        void countProject(Object param);
//    }
//}
