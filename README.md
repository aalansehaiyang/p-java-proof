## java
---

### :point_right: sleep、wait、notify、notifyAll

wait()，当前线程进行wait等待状态，同时释放资源锁，其他正在等待该锁的线程开始抢占锁进而运行，当调用了notify() 方法，之前调用wait()的线程才会解除wait状态，有权重新去参与竞争同步资源锁，如果抢到锁可以继续执行。


sleep()方法正在执行的线程主动让出CPU（然后CPU就可以去执行其他任务），在sleep指定时间后CPU再回到该线程继续往下执行(注意：sleep方法只让出了CPU，而并不会释放同步资源锁)。

sleep()方法可以在任何地方使用；wait()方法则只能在同步方法或同步块中使用；

sleep()是线程线程类（Thread）的方法，调用会暂停此线程指定的时间，但监控依然保持，不会释放对象锁，到时间自动恢复；wait()是Object的方法，调用会放弃对象锁，进入等待队列，待调用notify()/notifyAll()唤醒指定的线程或者所有线程

示例：

* proof.a.TestWait
* proof.a.TestNotifyAll


wait(long timeout)，让当前线程处于“等待状态”，“直到其他线程调用此对象的notify()方法，且当前线程 T，正好是被选中唤醒的，或者 notifyAll() 方法，或者其它线程中断 T，或者超过指定的时间值，当前线程被唤醒，重新进入锁池抢占同步资源锁
                 
示例：

* proof.a.TestWaitTimeout

https://hacpai.com/article/1488015279637

### :point_right: Collections.shuffle()

对集合元素的顺序随机打乱

```
1）static void shuffle(List<?> list)  使用默认随机源对列表进行置换，所有置换发生的可能性都是大致相等的。

2）static void shuffle(List<?> list, Random rand) 使用指定的随机源对指定列表进行置换，所有置换发生的可能性都是大致相等的，假定随机源是公平的。

```

https://blog.csdn.net/u011514810/article/details/51218784

### :point_right: Exchanger

```
String exchange(V x)  用于交换，启动交换并等待另一个线程调用exchange。

String exchange(V x,long timeout,TimeUnit unit)  用于交换，启动交换并等待另一个线程调用exchange，并且设置最大等待时间，当等待时间超过timeout便停止等待。
```

https://blog.csdn.net/carson0408/article/details/79477280

### :point_right: Sytem.exit

```
public static void exit(int status) {
    Runtime.getRuntime().exit(status);
}
```
* 0：正常退出
* 非0：非正常退出

无论status为何值程序都会退出，和return 相比有不同的是：return是回到上一层，而System.exit(status)是回到最上层。
