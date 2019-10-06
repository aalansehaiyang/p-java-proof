package proof.chapter14;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * @author onlyone
 */
public class StampedLock3 {

    public static void main(String[] args) throws InterruptedException {
        StampedLock stampedLock = new StampedLock();

        new Thread(() -> {
            stampedLock.writeLock();
            System.out.println("线程1：获取了写锁");
        }).start();

        Thread.sleep(100);

        new Thread(() -> {
            long stamp = -1;
            // 非阻塞式获取写锁，如果是0，表示没有拿到写锁
            // stamp = stampedLock.tryWriteLock();
            try {
                stamp = stampedLock.tryWriteLock(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程2：tryWriteLock stamp=" + stamp);
        }).start();

        // 防止jvm进程提早退出
        Thread.sleep(15_000);
    }

}
