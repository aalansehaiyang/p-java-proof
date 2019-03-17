package proof.chapter5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author onlyone
 */
public class ReentrantLock3 {

    private Lock      lock      = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void method1() {
        try {
            lock.lock();
            System.out.println("当前线程：" + Thread.currentThread().getName() + "进入等待状态..");
            Thread.sleep(3000);
            System.out.println("当前线程：" + Thread.currentThread().getName() + "释放锁..");
            condition.await(); // Object wait
            System.out.println("当前线程：" + Thread.currentThread().getName() + "继续执行...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void method2() {
        try {
            lock.lock();
            System.out.println("当前线程：" + Thread.currentThread().getName() + "进入..");
            Thread.sleep(3000);
            System.out.println("当前线程：" + Thread.currentThread().getName() + "发出唤醒..");
            condition.signal(); // Object notify
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        ReentrantLock3 uc = new ReentrantLock3();

        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                uc.method1();
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                uc.method2();
            }
        }, "t2");

        t1.start();
        t2.start();
    }

}
