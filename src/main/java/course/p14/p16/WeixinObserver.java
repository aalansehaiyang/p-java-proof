package course.p14.p16;


public class WeixinObserver implements Observer {
    @Override
    public void handle() {
        System.out.println("发送微信通知！");
    }
}
