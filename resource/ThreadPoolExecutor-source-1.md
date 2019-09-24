## ThreadPoolExecutor 
---

### 一、开场白

Java是面向对象编程，万事万物皆对象，讲究`池化`技术，可以避免对象频繁的创建、销毁，浪费性能。线程池作为线程的复用利器，工作中都用过，可以说是非常非常重要。面试时很多面试官也会重点考察这块知识，用归用，但你是否真的了解线程池的内部原理？

* 核心线程、最大线程、阻塞队列、拒绝策略，这四者是什么关系？
* 拒绝策略有哪些？如何实现一个自定义的拒绝策略？
* 如何动态调整线程池中的参数配置？
* Runnable任务是作为构造器入参来实例化Thread对象的，如果一个Runnable任务执行完，下一个Runnable如何传入Thread对象中？
* 空闲线程是如何回收的？回收的力度有多大？
* ThreadPoolExecutor，预留了哪些扩展？如何做性能监控？

### 二、7个核心参数

```
ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler)

```

1、corePoolSize（核心线程数）：

   * 当向线程池提交一个任务时，若线程池已创建的线程数小于corePoolSize，即便此时存在空闲线程，也会创建一个新线程来执行该任务，直到已创建的线程数大于或等于corePoolSize。
  
   * 除了利用提交新任务来创建和启动线程（按需构造），也可以通过 prestartCoreThread() 或 prestartAllCoreThreads() 方法来提前启动线程。


2、maximumPoolSize（最大线程数）：

   * 线程池所允许的最大线程个数。当队列满了，且已创建的线程数小于maximumPoolSize，会创建新的线程来执行任务。
   * 另外，对于无界队列，可忽略该参数。

3、keepAliveTime（最大空闲时间）：

   * 默认情况下，当线程个数大于corePoolSize时，如果线程的空闲时间超过keepAliveTime则会销毁。
   * allowCoreThreadTimeOut(boolean) 方法可将此超时策略应用于核心线程。
   * 另外，也可以使用setKeepAliveTime()动态地更改参数。

4、unit（存活时间的单位）：

   * 时间单位，分为7类，从细到粗顺序：NANOSECONDS（纳秒），MICROSECONDS（微秒），MILLISECONDS（毫秒），SECONDS（秒），MINUTES（分），HOURS（小时），DAYS（天）；

5、workQueue（任务队列）：

  * 用于保存等待执行任务的阻塞队列，线程会不断从该队列拉取任务执行。
  * 如果运行的线程数少于 corePoolSize，优先创建新的线程，而不进行排队。
  * 如果运行的线程数大于等于 corePoolSize，则 Executor 始终首选将请求加入队列，而不是创建新的线程。
  * 如果无法将任务加入队列，则创建新的线程，除非线程数已经达到 maximumPoolSize，此时，任务将被拒绝。


6、threadFactory（线程工厂）：

   * 用于创建新线程。由同一个threadFactory创建的线程，属于同一个ThreadGroup，创建的线程优先级都为Thread.NORM_PRIORITY，以及是非守护进程状态。
   * threadFactory创建的线程也是采用new Thread()方式，threadFactory创建的线程名都具有统一的风格：pool-m-thread-n（m为线程池的编号，n为线程池内的线程编号）;

7、handler（拒绝策略）：

   * 当线程池和队列都满了，则表明该线程池已达饱和状态。
   * ThreadPoolExecutor.AbortPolicy：拒绝并抛出异常 RejectedExecutionException。(默认策略)
   * ThreadPoolExecutor.CallerRunsPolicy：调用者所在线程来运行该任务，**能够减缓新任务的提交速度**。
   * ThreadPoolExecutor.DiscardPolicy：直接扔掉。
   * ThreadPoolExecutor.DiscardOldestPolicy：如果线程池尚未关闭，将队列的头元素移除，然后提交当前任务
   * 也可以实现 RejectedExecutionHandler接口，自定义拒绝策略。


### 三、状态、计数字段

```
// 高3位用来表示线程池的状态，后面的29位则表示线程数
private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
private static final int COUNT_BITS = Integer.SIZE - 3;  // 29
private static final int CAPACITY   = (1 << COUNT_BITS) - 1;  //二进制表示，29个1

// 线程池当前状态
private static final int RUNNING    = -1 << COUNT_BITS;
private static final int SHUTDOWN   =  0 << COUNT_BITS;
private static final int STOP       =  1 << COUNT_BITS;
private static final int TIDYING    =  2 << COUNT_BITS;
private static final int TERMINATED =  3 << COUNT_BITS;
    	
```

ctl是线程池的核心状态控制字段，本身是一个AtomicInteger，用来保证对ctl的操作都是线程安全的。这里利用位运算巧妙地将一个int(一个int 4个字节 即32位)拆成了两部分，高3位用来表示线程的状态，剩下的29位则表示工作线程数。这里就可以得知工作线程的数量上限即CAPACITY，大约有5亿。

![](img/ctl.png)

这五种状态转换成二进制后如下所示：

* RUNNING: 
  * 能接受新提交的任务
  * 能处理阻塞队列中的任务

* SHUTDOWN: 
  * 不再接受新提交的任务
  * 可以继续处理阻塞队列中已保存的任务。
  * 在线程池处于 RUNNING 状态时，调用 shutdown()方法会使线程池进入到该状态
  * finalize() 方法，也会调用shutdown()方法进入该状态

* STOP: 
  * 不接受新任务，也不处理队列中的任务
  * 线程池处于 RUNNING 或 SHUTDOWN 状态时，调用 shutdownNow() 方法会使线程池进入到该状态          
      * interruptWorkers()，所有线程（Worker）会中断
      * drainQueue()，返回阻塞队列中未执行的任务List
      * 触发 tryTerminate() 方法

* TIDYING: 
  * 如果所有的任务都已终止了，workerCount (有效线程数) 为0，线程池进入该状态后会调用 terminated() 方法，并把状态修改成 TERMINATED

* TERMINATED:  
  * 在terminated() 方法执行完后进入该状态，terminated()方法默认空实现


#### 状态相关的方法：

* 线程池是否处于运行状态。


```
private static boolean isRunning(int c) {
    return c < SHUTDOWN;
}    
```

* 线程池的状态

```
private static int runStateOf(int c)     { return c & ~CAPACITY; }

```

* 线程数

```
private static int workerCountOf(int c)  { return c & CAPACITY; }
```

* 线程池的统计数据

```
long getTaskCount() // 已完成和未执行的任务总数；
long getCompletedTaskCount() // 已完成的任务数量，小于等于taskCount；
int getLargestPoolSize() //线程池曾经创建过的最大线程数量。通过这个数据可以知道线程池是否满过，是否达到过maximumPoolSize；
int getPoolSize() //线程池中的的线程数量
int getActiveCount() // 正在运行任务的线程数量
```

### 四、线程池提交任务

* execute()，提交不需要返回值的任务，所以无法判断任务是否被线程池执行成功。
* submit()，提交需要返回值的任务，线程池会返回一个Future类型的对象，通过这个对象可以判断任务是否执行成功。

```
Future<Object> future = executor.submit(task);
```


### 五、主流程

#### execute（）方法

ThreadPoolExecutor的顶级父类是Executor接口，它只有一个方法就是execute()，我们也就是通过它来向线程池提交任务去执行的。

```
// 提交一个任务
public void execute(Runnable command) {
    if (command == null)
        throw new NullPointerException();
    int c = ctl.get();
    // 如果小于核心线程数，创建Worker，并启动里面的Thread
    if (workerCountOf(c) < corePoolSize) {
        if (addWorker(command, true))
            return;
        c = ctl.get();
    }
    
    // 如果处于RUNNING态，将任务放入队列成功
    if (isRunning(c) && workQueue.offer(command)) {
        int recheck = ctl.get();
        // 二次状态检查，非RUNNING态，从队列移除
        if (! isRunning(recheck) && remove(command))
            // 执行拒绝策略
            reject(command);
            // 如果线程池为0了，重新创建一个新的线程
            // 为什么会这样？因为设置allowCoreThreadTimeOut，核心线程因空闲全部回收了
        else if (workerCountOf(recheck) == 0)
            // 创建Worker，并启动里面的Thread，为什么传null，线程启动后会自动从阻塞队列拉任务执行
            addWorker(null, false);
    }
    // 尝试往最大线程数创建线程
    else if (!addWorker(command, false))
        reject(command);
}
```

* 若当前线程数小于corePoolSize，则创建一个新的线程来执行任务
* 若当前线程数大于等于corePoolSize，且阻塞队列未满，则将任务添加到队列中
* 如果阻塞队列已满，但当前线程数小于maximumPoolSize，则创建一个“临时”线程来执行任务
* 若当前线程数大于等于maximumPoolSize，且阻塞队列已满，此时会执行拒绝策略


注意点：

* 在往队列中添加任务后会对线程池状态 double check，这是因为在并发情况下，从上次判断线程池状态到现在线程池可能会被关闭，由于线程池关闭后不能再继续添加任务了，此时就需要回滚刚才的添加任务到队列中的操作，并执行拒绝策略
* addWorker(null, false)，只是创建一个新的Thread，但是没有传入任务，这是因为前面已经将任务添加到队列中了


#### addWorker（）方法

addWorker 方法主要是创建一个`Thread`并封装到`Worker`中。Worker实现了Runnable接口，本身也是一个线程任务。

```
 private final class Worker
        extends AbstractQueuedSynchronizer
        implements Runnable
    {
    ....
        Worker(Runnable firstTask) {
            setState(-1); 
            this.firstTask = firstTask;
            // 用Worker自身任务做为入参，构造Thread
            this.thread = getThreadFactory().newThread(this);    
        }

        public void run() {
            runWorker(this);
        }
    .....
}
```

该方法接收两个参数firstTask和core，firstTask参数用于指定新增的线程执行的第一个任务，如果firstTask为空的话只创建线程。

core参数：

* true，表示新增线程时，判断当前线程数是否少于corePoolSize
* false，表示新增线程时，判断当前线程数是否少于maximumPoolSize

```
private boolean addWorker(Runnable firstTask, boolean core) {
    retry:
    for (;;) {
        int c = ctl.get();
        int rs = runStateOf(c);

        // 如果队列为空，跳出
        if (rs >= SHUTDOWN &&
            ! (rs == SHUTDOWN &&
               firstTask == null &&
               ! workQueue.isEmpty()))
            return false;

        for (;;) {
            // 线程数
            int wc = workerCountOf(c);
            // 如果线程数超过限制，跳出
            if (wc >= CAPACITY ||
                wc >= (core ? corePoolSize : maximumPoolSize))
                return false;
            // ctl 线程计数+1 。成功，跳出最外层的for循环 
            if (compareAndIncrementWorkerCount(c))
                break retry;
            // 计数失败，判断状态是否改变，如果改变，重新执行最外层的for循环 
            c = ctl.get();  
            if (runStateOf(c) != rs)
                continue retry;
            
        }
    }

    boolean workerStarted = false;
    boolean workerAdded = false;
    Worker w = null;
    try {
        // 构建 Worker 对象
        w = new Worker(firstTask);
        final Thread t = w.thread;
        if (t != null) {
            final ReentrantLock mainLock = this.mainLock;
            mainLock.lock();
            try {
                
                int rs = runStateOf(ctl.get());

                if (rs < SHUTDOWN ||
                    (rs == SHUTDOWN && firstTask == null)) {
                    if (t.isAlive()) // precheck that t is startable
                        throw new IllegalThreadStateException();
                    // 把Worker放入HashSet集合，后面关闭时，线程中断会用到
                    workers.add(w);
                    int s = workers.size();
                    // 记录，历史上曾经创建过的最大线程数
                    if (s > largestPoolSize)
                        largestPoolSize = s;
                    workerAdded = true;
                }
            } finally {
                mainLock.unlock();
            }
            if (workerAdded) {
                // 将Thread 启动起来
                t.start();
                workerStarted = true;
            }
        }
    } finally {
        // 如果添加Worker失败，把Worker从HashSet集合移除，并对线程计数减1
        if (! workerStarted)
            addWorkerFailed(w);
    }
    return workerStarted;
} 
```

重点：

Worker本身实现了Runnable接口，`t.start()`这个语句启动时，会调用Worker类中的run方法。内部调用runWorker（）方法，开限`循环模式`从阻塞队列中拉取任务来执行。

#### runWorker（）方法

```
final void runWorker(Worker w) {
    Thread wt = Thread.currentThread();
    Runnable task = w.firstTask;
    w.firstTask = null;
    w.unlock(); // allow interrupts
    boolean completedAbruptly = true;
    try {
        // 如果有firstTask，先执行。否则从阻塞队列拉取任务
        while (task != null || (task = getTask()) != null) {
            w.lock();
            // 二次检查，如果状态停止，确保线程是中断的
            if ((runStateAtLeast(ctl.get(), STOP) ||
                 (Thread.interrupted() &&
                  runStateAtLeast(ctl.get(), STOP))) &&
                !wt.isInterrupted())
                wt.interrupt();
            try {
                // 前置扩展
                beforeExecute(wt, task);
                Throwable thrown = null;
                try {
                    // 最最最核心，Runnable任务执行
                    task.run();
                } catch (RuntimeException x) {
                    thrown = x; throw x;
                } catch (Error x) {
                    thrown = x; throw x;
                } catch (Throwable x) {
                    thrown = x; throw new Error(x);
                } finally {
                    // 后置扩展
                    afterExecute(task, thrown);
                }
            } finally {
                // task复位
                task = null;
                // Worker中已完成的任务计数+1
                w.completedTasks++;
                w.unlock();
            }
        }
        completedAbruptly = false;
    } finally {
        // 清理 Worker
        processWorkerExit(w, completedAbruptly);
    }
}
```

分析源码可以得知runWorker方法的执行过程：

* while循环不断地通过getTask()方法，从阻塞队列拉取任务；
* 如果线程池正在停止，那么要保证当前线程是中断状态，否则要保证当前线程不是中断状态；
* 前置扩展
* 调用`task.run()`执行任务；
* 后置扩展
* 如果task为null，则跳出循环，执行processWorkerExit()方法；
* runWorker方法执行完毕，代表着Worker中的run方法执行完毕，Runnable任务执行完毕，**然后线程销毁**。

#### processWorkerExit（）方法

```
private void processWorkerExit(Worker w, boolean completedAbruptly) {
    // 突然结束，completedAbruptly为true，会对线程计数减1。
    // 对于因没有任务而结束，completedAbruptly为false，getTask（）方法中会执行减1操作
    if (completedAbruptly) 
        decrementWorkerCount();

    final ReentrantLock mainLock = this.mainLock;
    mainLock.lock();
    try {
        // 将Worker中记录的已完成任务数，合并到ThreadPoolExecutor的全局字段中
        completedTaskCount += w.completedTasks;
        // 把Worker从HashSet集合移除
        workers.remove(w);
    } finally {
        mainLock.unlock();
    }

    tryTerminate();

    // 二次检查，如果RUNNING或SHUTDOWN，非正常结束，需要重新创建线程，容错
    int c = ctl.get();
    if (runStateLessThan(c, STOP)) {
        if (!completedAbruptly) {
            int min = allowCoreThreadTimeOut ? 0 : corePoolSize;
            if (min == 0 && ! workQueue.isEmpty())
                min = 1;
            if (workerCountOf(c) >= min)
                return; 
        }
        addWorker(null, false);
    }
}
```

#### getTask（）方法

getTask方法用于从阻塞队列中获取任务，源码如下

```
private Runnable getTask() {
    boolean timedOut = false; 

    for (;;) {
        int c = ctl.get();
        int rs = runStateOf(c);

        // 如果rs >=STOP或者（rs 为 SHUTDOWN且队列为空）
        if (rs >= SHUTDOWN && (rs >= STOP || workQueue.isEmpty())) {
            // 线程计数减1
            decrementWorkerCount();
            return null;
        }

        int wc = workerCountOf(c);

        // 标记：大于核心线程数 或 允许核心线程被回收
        boolean timed = allowCoreThreadTimeOut || wc > corePoolSize;

        if ((wc > maximumPoolSize || (timed && timedOut))
            && (wc > 1 || workQueue.isEmpty())) {
            // 线程计数减1
            if (compareAndDecrementWorkerCount(c))
                return null;
            continue;
        }

        try {
            // 采用超时方式来获取任务
            Runnable r = timed ?
                workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS) :
                // 采用阻塞方式来获取任务
                workQueue.take();
            if (r != null)
                return r;
            timedOut = true;
        } catch (InterruptedException retry) {
            timedOut = false;
        }
    }
}
```

* getTask方法首先对线程池状态进行判断，如果线程池为非RUNNING状态且满足以下条件，则将workerCount减1并返回null

    * 1、rs >= STOP
    * 2、rs 为 SHUTDOWN且队列为空。
    * 说明：当线程池状态为SHUTDOWN或以上时，不允许再往队列中添加任务。

* timed变量用来判断是否进行超时控制
    * allowCoreThreadTimeOut默认是false，当线程数量降到corePoolSize时，会采用阻塞方式从队列拉取任务
    * 其它情况，采用超时方式来获取任务
        * 如果达到keepAliveTime最大空闲时间，仍拿不到任务，线程计数减1，返回null
    * 如果设置allowCoreThreadTimeOut为true，空闲时，线程池数最小可能会为0

#### advanceRunState（）方法

更改线程池状态

```
private void advanceRunState(int targetState) {
    for (;;) {
        int c = ctl.get();
        // 如果当前状态在目标状态之后
        if (runStateAtLeast(c, targetState) ||
            // 将目标状态+线程数，合成一个字段
            ctl.compareAndSet(c, ctlOf(targetState, workerCountOf(c))))
            break;
    }
}
```

* 如果当前状态在目标状态之后，跳出循环，不做任何处理
* 否则，将目标状态+线程数，合成一个字段，更新到ctl

### 六、线程池关闭

关闭线程池。他们的原理是遍历线程池的工作线程，然后逐个调用线程的interrupt方法来中断线程。

#### shutdown（）方法

```
public void shutdown() {
    final ReentrantLock mainLock = this.mainLock;
    mainLock.lock();
    try {
        checkShutdownAccess();
        // 标记线程池状态为 SHUTDOWN
        advanceRunState(SHUTDOWN);
        // 把所有空闲线程中断
        interruptIdleWorkers();
        // 空实现，为ScheduledThreadPoolExecutor预留的扩展
        onShutdown(); 
    } finally {
        mainLock.unlock();
    }
    tryTerminate();
}
```

#### shutdownNow（）方法

```
public List<Runnable> shutdownNow() {
    List<Runnable> tasks;
    final ReentrantLock mainLock = this.mainLock;
    mainLock.lock();
    try {
        checkShutdownAccess();
        // 标记线程池状态为 STOP
        advanceRunState(STOP);
        // 把所有线程中断
        interruptWorkers();
        // 把阻塞队列中的所有任务提取到List集合中，并返回
        tasks = drainQueue();
    } finally {
        mainLock.unlock();
    }
    tryTerminate();
    return tasks;
}
```


区别：

* shutdown方法，不再接收新的任务，已提交的任务会执行完
* shutdownNow方法，比较粗暴，它将尝试中断所有运行中的任务，并且不再启动队列中尚未开始执行的任务。

只要调用了这两个关闭方法中的任意一个，isShutdown（）方法就会返回true。当所有的任务都已关闭后，才表示线程池关闭成功，这时调用isTerminated（）方法会返回true。

采用哪一种方法来关闭线程池，由业务特性决定，大部分是采用shutdown（）方法来关闭线程池。如果任务不强求一定要执行完，可以调用shutdownNow（）方法。


### 七、扩展

ThreadPoolExecutor提供扩展方法：通过继承`ThreadPoolExecutor`，**重写beforeExecute、afterExecute、terminated方法**。在执行任务的线程中将调用beforeExecute和afterExecute等方法，**在这些方法中还可以添加日志、计时、监视或者统计信息收集的功能。**

```
public class MonitorableThreadPoolExecutor extends ThreadPoolExecutor {

private ThreadLocal<Context> taskExecutionTimer = new ThreadLocal<>();

。。。

  @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        Context context = new Context();
        context.setStartTime(System.currentTimeMillis());
        taskExecutionTimer.set(context);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        Context context = taskExecutionTimer.get();
        long start;
        if (context != null) {
            start = context.getStartTime();
        } else {
            start = System.currentTimeMillis();
        }
        long cost = System.currentTimeMillis() - start;
        RedAlertLogUtils.threadPoolMonitorLog(name, getCorePoolSize(), getActiveCount(), getMaximumPoolSize(), getQueue().size(), cost);
    }
。。。。

}
```

### 八、工具类方法

* newFixedThreadPool， 有固定长度（nThreads）的线程数组，忙不过来时会把任务放到无限长的队列里，这是因为LinkedBlockingQueue 默认是一个无界队列。


```
public static ExecutorService newFixedThreadPool(int nThreads) {
    return new ThreadPoolExecutor(nThreads, nThreads,
                                  0L, TimeUnit.MILLISECONDS,
                                 new LinkedBlockingQueue<Runnable>());
}

```

* newCachedThreadPool 的 maximumPoolSize 参数值是Integer.MAX_VALUE
，因此它对线程个数不做限制，忙不过来时无限创建临时线程，闲下来时再回收。它的任务队列是SynchronousQueue，表明队列长度为 0。

``` 
public static ExecutorService newCachedThreadPool() {
    return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                  60L, TimeUnit.SECONDS,
                                  new SynchronousQueue<Runnable>());
}
```




