package proof.chapter5;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author onlyone
 */
public class ReentrantReadWriteLock2 {

    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private static Lock                   readLock               = reentrantReadWriteLock.readLock();
    private static Lock                   writeLock              = reentrantReadWriteLock.writeLock();
    private static int                    value;

    /**
     * 读操作
     */
    public static Object handleRead() throws InterruptedException {
        try {
            readLock.lock();
            // Thread.sleep(1000);// 模拟读操作
            System.out.println(Thread.currentThread().getName() + "读操作:" + value);
            return value;
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 写操作
     */
    public static void handleWrite(int index) throws InterruptedException {
        try {
            writeLock.lock();
            // Thread.sleep(1000);// 模拟写操作
            value = index;
            System.out.println(Thread.currentThread().getName() + "写操作:" + value);
        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) throws Exception {
        ReadRunnable readRunnable = new ReadRunnable();
        WriteRunnable writeRunnable = new WriteRunnable();
        for (int i = 1; i < 11; i++) {
            new Thread(readRunnable, "Read-1 t" + i).start();
        }
        for (int i = 1; i < 6; i++) {
            new Thread(writeRunnable, "Write-1 t" + i).start();
        }
        for (int i = 1; i < 6; i++) {
            new Thread(readRunnable, "Read-2 t" + i).start();
        }
        for (int i = 1; i < 6; i++) {
            new Thread(writeRunnable, "Write-2 t" + i).start();
        }

    }

    private static class ReadRunnable implements Runnable {

        @Override
        public void run() {
            try {
                handleRead();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class WriteRunnable implements Runnable {

        @Override
        public void run() {
            try {
                handleWrite(new Random().nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
