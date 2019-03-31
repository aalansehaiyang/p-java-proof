package proof.chapter4;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author onlyone
 */
public class CyclicBarrier1 {

    static CyclicBarrier c = new CyclicBarrier(2, new RunnableTask());

    public static void main(String[] args) {

        new Thread(new Runnable() {

            @Override

            public void run() {
                try {
                    System.out.println("CyclicBarrier -1");
                    // 阻塞，直到所有的线程到达屏障时，线程才会继续执行
                    c.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }

                System.out.println(2);

            }
        }).start();

        try {
            System.out.println("CyclicBarrier -2");
            // 阻塞，直到所有的线程到达屏障时，线程才会继续执行
            c.await();
        }

        catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(4);

    }

    static class RunnableTask implements Runnable {

        @Override
        public void run() {
            System.out.println("CyclicBarrier  task   execute !!!");
        }

    }

}
