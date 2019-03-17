package proof.chapter1;

public class TestNotifyAll {

    private static Object o1 = new Object();

    private static class Thread1 implements Runnable {

        @Override
        public void run() {
            synchronized (o1) {
                System.out.println("thread1 start...");
                System.out.println("thread1  wait");

                try {
                    o1.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("thread1 is  over!");
            }
        }

    }

    private static class Thread2 implements Runnable {

        @Override
        public void run() {
            synchronized (o1) {
                System.out.println("thread2 start...");
                System.out.println("Thread2  wait");

                try {
                    o1.wait();

                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Thread2 is  over!");
            }
        }

    }

    private static class Thread3 implements Runnable {

        @Override
        public void run() {
            synchronized (o1) {
                System.out.println("thread3 start...");
                System.out.println("Thread3 notify other thread can awaken wait status ...");

                // 1、Thread1和Thread2都处于wait，只随机唤醒其中一个
                // o1.notify();

                // 2、Thread1和Thread2都处于wait，全部唤醒
                o1.notifyAll();

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Thread3 is  over!");
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new TestNotifyAll.Thread1()).start();
        new Thread(new TestNotifyAll.Thread2()).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(new TestNotifyAll.Thread3()).start();
    }

}
