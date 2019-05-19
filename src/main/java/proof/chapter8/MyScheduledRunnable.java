package proof.chapter8;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author onlyone
 */
@Data
@Accessors(chain = true)
public class MyScheduledRunnable implements Runnable {

    private Long bizCostTime;
    private Long startTime = System.currentTimeMillis();

    @Override
    public void run() {

        try {
            Thread.sleep(bizCostTime);
            System.out.println("当前线程：" + Thread.currentThread().getName() + "，当前时间："
                               + (System.currentTimeMillis() - startTime));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
