package proof.chapter13;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author onlyone
 */
public class Unsafe1 {

    private static final sun.misc.Unsafe UNSAFE = getUnsafe();

    private static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);

        } catch (Exception e) {
        }
        return null;
    }

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("线程T1，任务编号：" + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 挂起
                UNSAFE.park(false, 0);
            }
        });
        t1.start();


        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("线程T2，任务编号：" + i);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();

        //唤醒线线程1
        UNSAFE.unpark(t1);

        // 防止提前退出
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
