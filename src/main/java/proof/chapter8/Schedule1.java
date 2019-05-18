package proof.chapter8;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author onlyone
 */
public class Schedule1 {

    public static void main(String[] args) {

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {
                System.out.println("-------设定要指定任务--------");
            }
        }, 1000, 1000);

    }
}
