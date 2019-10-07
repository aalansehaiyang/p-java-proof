package proof.chapter11;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @author onlyone
 */
public class SynchronousQueue1 {

    public static void main(String[] args) throws Exception {
        BlockingQueue<Integer> queue = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("queue.offer(5)： " + queue.offer(5) + " ");

        }).start();

        // 每一个插入操作必须先等待一个take操作，否则不能添加元素
        System.out.println("queue.offer(1)： " + queue.offer(1) + " ");
        System.out.println("queue.offer(2)： " + queue.offer(2) + " ");
        System.out.println("queue.offer(3)： " + queue.offer(3) + " ");
        System.out.println("queue.take()： " + queue.take() + " ");

        System.out.println("queue.size(): " + queue.size());

        // 结果： false false false true 5 0
    }
}
