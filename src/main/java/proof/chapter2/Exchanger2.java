package proof.chapter2;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author onlyone
 */
public class Exchanger2 {

    private static final Exchanger<String> exchanger  = new Exchanger<>();

    private static ExecutorService         threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args)

    {

        threadPool.execute(new Runnable() {

            @Override

            public void run()

            {

                String A = "A";

                try

                {

                    String B = exchanger.exchange(A);

                    System.out.println("Thread A ，" + Thread.currentThread().getName() + ": " + B);

                }

                catch (InterruptedException e)

                {

                    e.printStackTrace();

                }

            }

        });

        threadPool.execute(new Runnable() {

            @Override

            public void run()

            {

                try

                {

                    String B = "B";

                    String A = exchanger.exchange(B);

                    System.out.println("Thread B ，" + Thread.currentThread().getName() + ": " + A);

                }

                catch (InterruptedException e)

                {

                    e.printStackTrace();

                }

            }

        });

        threadPool.shutdown();

    }

}
