package proof.chapter1;

public class TestWait {

    private static Object o1 = new Object();

    private static class Thread1 implements Runnable {

        @Override
        public void run() {
            // 由于 Thread1和下面Thread2内部run方法要用同一对象作为监视器，如果用this则Thread1和Threa2的this不是同一对象
            // TestWait.class这个字节码对象，当前虚拟机里引用这个变量时指向的都是同一个对象
            synchronized (o1) {
                System.out.println("enter thread1 ...");
                System.out.println("thread1 is waiting");

                try {
                    Thread.sleep(100);

                    // 释放锁有两种方式：
                    // 1、离开synchronized关键字管辖的代码范围
                    // 2、在synchronized关键字管辖的代码内部调用监视器对象的wait()方法。这里使用wait方法
                    o1.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("thread1 is being over!");
            }
        }

    }

    private static class Thread2 implements Runnable {

        @Override
        public void run() {
            // notify方法并不释放锁，即使thread2调用了下面的sleep方法休息10ms，但thread1仍然不会执行
            // 因为thread2没有释放锁，所以Thread1得不到锁而无法执行
            synchronized (o1) {
                System.out.println("enter thread2 ...");
                System.out.println("thread2 notify other thread can release wait status ...");
                o1.notify();
                System.out.println("thread2 is sleeping ten millisecond ...");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("thread2 is being over!");
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new Thread1()).start();

        new Thread(new Thread2()).start();
    }

}
