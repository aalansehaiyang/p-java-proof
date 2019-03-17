package proof.chapter1;

public class TestWaitTimeout {

    private static class Thread1 implements Runnable {

        private Object lock;

        public Thread1(Object lock){
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println("thread1 start...");

                try {
                    // lock.wait();

                    // 等待2秒，继续抢占资源锁
                    lock.wait(2000);
                } catch (InterruptedException e) {
                    // 打印中断日志
                    System.out.println("thread1 InterruptedException...");
                    e.printStackTrace();
                }

                System.out.println("thread1 is  over!");
            }
        }

    }

    private static class Thread2 implements Runnable {

        private Object lock;

        public Thread2(Object lock){
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println("thread2 start...");
                while (true) {
                    // 一直不释放锁

                }

            }
        }
    }

    public static void main(String[] args) {
        Object lock = new Object();
        Thread t1 = new Thread(new TestWaitTimeout.Thread1(lock));
        t1.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(new TestWaitTimeout.Thread2(lock)).start();

        // t1.interrupt();
    }
}
