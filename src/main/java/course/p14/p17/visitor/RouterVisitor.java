package course.p14.p17.visitor;

import course.p14.p17.JhjRouterElement;
import course.p14.p17.LyqRouterElement;

/**
 * @author 微信公众号：微观技术
 * 定义行为动作
 */
public interface RouterVisitor {

    // 交换机，发送数据
    void sendData(JhjRouterElement jhjRouterElement);

    // 路由器，发送数据
    void sendData(LyqRouterElement lyqRouterElement);

}
