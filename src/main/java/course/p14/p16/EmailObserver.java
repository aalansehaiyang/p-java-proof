package course.p14.p16;


public class EmailObserver implements Observer {
    @Override
    public void handle() {
        System.out.println("发送邮件通知！");
    }
}
