package proof.chapter5;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author onlyone
 */
public class ReentrantLock2 {

    private Lock lock      = new ReentrantLock();
    private Long startTime = System.currentTimeMillis();

    public void method1() {
        try {
            lock.lock();
            System.out.println((System.currentTimeMillis() - startTime) + "，当前线程:" + Thread.currentThread().getName()
                               + "进入method1..");

            // sleep方法并不会释放锁
            Thread.sleep(3000);
            System.out.println((System.currentTimeMillis() - startTime) + "，当前线程:" + Thread.currentThread().getName()
                               + "退出method1..");
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            lock.unlock();
        }
    }

    public void method2() {
        try {
            lock.lock();
            System.out.println((System.currentTimeMillis() - startTime) + "，当前线程:" + Thread.currentThread().getName()
                               + "进入method2..");
            Thread.sleep(3000);
            System.out.println((System.currentTimeMillis() - startTime) + "，当前线程:" + Thread.currentThread().getName()
                               + "退出method2..");
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantLock2 ur = new ReentrantLock2();

        Thread t1 = new Thread(new Runnable() {

            public void run() {
                ur.method1();
                ur.method2();
            }
        }, "t1");

        t1.start();

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // System.out.println(ur.lock.getQueueLength());

    }

}
