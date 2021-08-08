package course.p14.p17;

import course.p14.p17.visitor.RouterVisitor;

/**
 * 交换机元素
 */
public class JhjRouterElement implements RouterElement {

    @Override
    public void sendData(RouterVisitor routerVisitor) {
        System.out.println("交换机开始工作。。。");
        routerVisitor.sendData(this);
    }
}
