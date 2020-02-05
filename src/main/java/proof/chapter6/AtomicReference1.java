package proof.chapter6;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author onlyone
 */
public class AtomicReference1 {

    public static void main(String[] args) throws InterruptedException {
        // 初始大小 1000
        AtomicReference<Integer> ref = new AtomicReference<>(new Integer(1000));

        // 启动1000个线程，每个线程计数+1
        for (int i = 0; i < 1000; i++) {
            Thread t = new Thread(new Task2(ref), "Thread-" + i);
            t.start();
        }
        System.out.println("输出结果：");
        System.out.println(ref.get()); // 打印2000
    }

}

class Task1 implements Runnable {

    private AtomicReference<Integer> ref;

    Task1(AtomicReference<Integer> ref){
        this.ref = ref;
    }

    @Override
    public void run() {
        for (;;) { // 自旋操作
            Integer oldV = ref.get();
            if (ref.compareAndSet(oldV, oldV + 1)) // CAS操作
                break;
        }
    }
}

class Task2 implements Runnable {

    private AtomicReference<Integer> ref;

    Task2(AtomicReference<Integer> ref){
        this.ref = ref;
    }

    @Override
    public void run() {
//        try {
//            Thread.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        ref.updateAndGet(t -> t + 1);
    }
}
