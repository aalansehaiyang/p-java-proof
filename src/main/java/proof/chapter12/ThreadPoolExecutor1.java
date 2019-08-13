package proof.chapter12;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author onlyone
 */
public class ThreadPoolExecutor1 {

    static ThreadPoolExecutor executorService = new ThreadPoolExecutor(0, 10, 60, TimeUnit.SECONDS,
                                                                       new LinkedBlockingDeque<>());

    public static void main(String[] args) throws InterruptedException {

        for (int i = 1; i <= 10; i++) {
            executorService.submit(new RunableTask(i));
        }

//        Thread.sleep(3000);

        for (int j = 11; j <=20; j++) {
            executorService.submit(new RunableTask(j));
        }

        
        System.out.println("========："  + ",队列长度：" + executorService.getQueue().size() + ",活跃线程："
                + executorService.getActiveCount() + ",核心线程：" + executorService.getCorePoolSize());

        Thread.sleep(5000);

        System.out.println("=======："  + ",队列长度：" + executorService.getQueue().size() + ",活跃线程："
                + executorService.getActiveCount() + ",核心线程：" + executorService.getCorePoolSize());

        for (int j = 21; j <=30; j++) {
            executorService.submit(new RunableTask(j));
        }

    }

    static class RunableTask implements Runnable {

        int i;

        public RunableTask(int i){
            this.i = i;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("执行任务：" + i + ",队列长度：" + executorService.getQueue().size() + ",活跃线程："
                               + executorService.getActiveCount() + ",核心线程：" + executorService.getCorePoolSize());
        }
    }
}
