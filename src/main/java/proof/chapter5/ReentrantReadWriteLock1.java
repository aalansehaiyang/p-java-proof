package proof.chapter5;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author onlyone
 */
public class ReentrantReadWriteLock1 {

    private ReentrantReadWriteLock           rwLock    = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock  readLock  = rwLock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();
    private Long                             startTime = System.currentTimeMillis();

    public void read() {
        try {
            readLock.lock();
            System.out.println((System.currentTimeMillis() - startTime) + "，当前线程:" + Thread.currentThread().getName()
                               + "进入...");
            Thread.sleep(3000);
            System.out.println((System.currentTimeMillis() - startTime) + "，当前线程:" + Thread.currentThread().getName()
                               + "退出...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
        }
    }

    public void write() {
        try {
            writeLock.lock();
            System.out.println((System.currentTimeMillis() - startTime) + "，当前线程:" + Thread.currentThread().getName()
                               + "进入...");
            Thread.sleep(3000);
            System.out.println((System.currentTimeMillis() - startTime) + "，当前线程:" + Thread.currentThread().getName()
                               + "退出...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {

        ReentrantReadWriteLock1 urrw = new ReentrantReadWriteLock1();

        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                urrw.read();
            }
        }, "t1");
        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                urrw.read();
            }
        }, "t2");
        Thread t3 = new Thread(new Runnable() {

            @Override
            public void run() {
                urrw.write();
            }
        }, "t3");
        Thread t4 = new Thread(new Runnable() {

            @Override
            public void run() {
                urrw.write();
            }
        }, "t4");

        t1.start();
        t2.start();

        // t1.start(); // R 
        // t3.start(); // W

        t3.start();
        t4.start();
    }
}
