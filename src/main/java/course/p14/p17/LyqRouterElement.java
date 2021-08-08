package course.p14.p17;


import course.p14.p17.visitor.RouterVisitor;

/**
 * 路由器元素
 */
public class LyqRouterElement implements RouterElement {

    @Override
    public void sendData(RouterVisitor routerVisitor) {
        System.out.println("路由器开始工作。。。");
        routerVisitor.sendData(this);
    }
}
