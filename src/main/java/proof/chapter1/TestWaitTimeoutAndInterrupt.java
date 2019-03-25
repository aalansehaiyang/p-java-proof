package proof.chapter1;

public class TestWaitTimeoutAndInterrupt {

    private static Long startTime = System.currentTimeMillis();

    private static class Task1 implements Runnable {

        private Object lock;

        public Task1(Object lock){
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println((System.currentTimeMillis() - startTime) + "，thread1 start...");

                try {
                    // lock.wait();

                    // 等待2秒，继续抢占资源锁
                    lock.wait(2000);
                } catch (InterruptedException e) {
                    // 打印中断日志
                    System.out.println("thread1 InterruptedException...");
                    e.printStackTrace();
                }

                System.out.println((System.currentTimeMillis() - startTime) + "，thread1 is  over!");
            }
        }

    }

    private static class Task2 implements Runnable {

        private Object lock;

        public Task2(Object lock){
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println((System.currentTimeMillis() - startTime) + "，thread2 start...");
                // while (true) {
                // // 一直不释放锁
                //
                // }

                try {
                    Thread.sleep(6000);
                    System.out.println((System.currentTimeMillis() - startTime) + "，thread2 sleep 6000...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static void main(String[] args) {
        Object lock = new Object();
        Thread t1 = new Thread(new Task1(lock));
        t1.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t2 = new Thread(new Task2(lock));
        t2.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

         t2.interrupt();
    }
}
