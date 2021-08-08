package course.p14.p17;

import course.p14.p17.visitor.RouterVisitor;

/**
 * @author 微信公众号：微观技术
 * <p>
 * 路由元素
 */

public interface RouterElement {

    // 发送数据
    void sendData(RouterVisitor routerVisitor);

}
