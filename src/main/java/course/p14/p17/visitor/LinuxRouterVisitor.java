package course.p14.p17.visitor;

import course.p14.p17.JhjRouterElement;
import course.p14.p17.LyqRouterElement;

public class LinuxRouterVisitor implements RouterVisitor {
    @Override
    public void sendData(JhjRouterElement jhjRouterElement) {
        System.out.println("Linux 环境下，交换机发送数据");
    }

    @Override
    public void sendData(LyqRouterElement lyqRouterElement) {
        System.out.println("Linux 环境下，路由器发送数据");
    }
}
