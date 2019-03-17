package proof.chapter5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author onlyone
 */
public class ReentrantLock1 {

    private ReentrantLock lock      = new ReentrantLock();
    private Condition     c1        = lock.newCondition();
    private Condition     c2        = lock.newCondition();

    private Long          startTime = System.currentTimeMillis();

    public void m1() {
        try {
            lock.lock();
            System.out.println((System.currentTimeMillis() - startTime) + "，当前线程：" + Thread.currentThread().getName()
                               + "进入方法m1等待..");

            Thread.sleep(1000);
            // 锁释放，并放入等待池中。待signal 后，重新去请求锁，获取锁后，才可以继续进行后面操作
            c1.await();

            Thread.sleep(5000);
            System.out.println((System.currentTimeMillis() - startTime) + "，当前线程：" + Thread.currentThread().getName()
                               + "方法m1继续..");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void m2() {
        try {
            lock.lock();
            System.out.println((System.currentTimeMillis() - startTime) + "，当前线程：" + Thread.currentThread().getName()
                               + "进入方法m2等待..");

            Thread.sleep(1000);
            c1.await();

            Thread.sleep(6000);
            System.out.println((System.currentTimeMillis() - startTime) + "，当前线程：" + Thread.currentThread().getName()
                               + "方法m2继续..");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void m3() {
        try {
            lock.lock();
            System.out.println((System.currentTimeMillis() - startTime) + "，当前线程：" + Thread.currentThread().getName()
                               + "进入方法m3等待..");
            c2.await();
            System.out.println((System.currentTimeMillis() - startTime) + "，当前线程：" + Thread.currentThread().getName()
                               + "方法m3继续..");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void m4() {
        try {
            lock.lock();
            System.out.println((System.currentTimeMillis() - startTime) + "，当前线程：" + Thread.currentThread().getName()
                               + "唤醒..");

            // 唤醒c1.await的线程
            c1.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void m5() {
        try {
            lock.lock();
            System.out.println((System.currentTimeMillis() - startTime) + "，当前线程：" + Thread.currentThread().getName()
                               + "唤醒..");
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            lock.unlock();
        }
    }

    public static void main(String[] args) {

        final ReentrantLock1 umc = new ReentrantLock1();

        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                umc.m1();
            }
        }, "t1");
        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                umc.m2();
            }
        }, "t2");
        Thread t3 = new Thread(new Runnable() {

            @Override
            public void run() {
                umc.m3();
            }
        }, "t3");
        Thread t4 = new Thread(new Runnable() {

            @Override
            public void run() {
                umc.m4();
            }
        }, "t4");
        Thread t5 = new Thread(new Runnable() {

            @Override
            public void run() {
                umc.m5();
            }
        }, "t5");

        t1.start(); // c1
        t2.start(); // c1
        t3.start(); // c2

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t4.start(); // c1
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t5.start(); // c2

    }

}
