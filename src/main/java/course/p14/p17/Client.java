package course.p14.p17;


import course.p14.p17.visitor.LinuxRouterVisitor;
import course.p14.p17.visitor.WindowRouterVisitor;

public class Client {
    public static void main(String[] args) {
        WindowRouterVisitor windowRouterVisitor = new WindowRouterVisitor();
        LinuxRouterVisitor linuxRouterVisitor = new LinuxRouterVisitor();

        // 路由器
        LyqRouterElement lyq = new LyqRouterElement();
        lyq.sendData(windowRouterVisitor);
        lyq.sendData(linuxRouterVisitor);

        System.out.println("====================");

        // 交换机
        JhjRouterElement jhjRouterElement = new JhjRouterElement();
        jhjRouterElement.sendData(windowRouterVisitor);
        jhjRouterElement.sendData(linuxRouterVisitor);
    }

}