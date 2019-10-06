package proof.chapter14;

import java.util.concurrent.locks.StampedLock;

/**
 * @author onlyone
 */
public class StampedLock1 {

    public static void main(String[] args) throws InterruptedException {

        StampedLock stampedLock = new StampedLock();

        for (int i = 0; i < 5; i++) {
            new Thread(new Task(i, stampedLock)).start();
        }

        // 防止jvm进程提早退出
        Thread.sleep(15_000);
    }

    static class Task implements Runnable {

        int         number;
        StampedLock stampedLock;

        public Task(int number, StampedLock stampedLock){
            this.number = number;
            this.stampedLock = stampedLock;
        }

        @Override
        public void run() {

            // 获取 / 释放悲观读锁
            long stamp = stampedLock.readLock();
            System.out.println("Task number：" + number + " begin!");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Task number：" + number + " end!");

            stampedLock.unlockRead(stamp);
        }
    }
}
