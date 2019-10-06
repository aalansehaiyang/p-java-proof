## StampedLock
---

### 一、基本信息

**三种模式**：

* 写锁
* 悲观读锁
* 乐观读

其中，写锁、悲观读锁的语义和 ReadWriteLock 的写锁、读锁的语义非常类似，允许多个线程同时获取悲观读锁，但是只允许一个线程获取写锁，写锁和悲观读锁是互斥的。

不同的是：StampedLock 里的写锁和悲观读锁加锁成功之后，都会返回一个 stamp；然后解锁的时候，需要传入这个 stamp。

**主要方法**：

* long writeLock() ，阻塞式获取写锁
* long tryWriteLock()，非阻塞式获取写锁，如果是0，表示没有拿到写锁
* long tryWriteLock(long time, TimeUnit unit)，同上，支持超时时间
* long writeLockInterruptibly()，阻塞式获取写锁，支持中断
* long readLock()，阻塞式获取悲观读锁
* long tryReadLock()，非阻塞式获取悲观读锁，如果是0，表示没有拿到锁
* long tryReadLock(long time, TimeUnit unit)，同上
* long readLockInterruptibly()，阻塞式获取悲观读锁，支持中断
* long tryOptimisticRead()，获取乐观读的版本号
* boolean validate(long stamp)，验证上面的版本号是否通过，期间是否有修改
* void unlockWrite(long stamp) ，释放写锁
* void unlockRead(long stamp)，释放悲观读锁
* void unlock(long stamp)，释放锁，笼统
* long tryConvertToWriteLock(long stamp)，升级读锁为写锁，非阻塞式，不满足返回0
* 其它的一些方法，主要用于监控，数据采集

**特性**：

* 不支持重入
* 写锁、悲观读锁都不支持条件变量
* 一定不要调用中断操作，如果需要支持中断功能，一定使用可中断的悲观读锁 readLockInterruptibly() 和写锁 writeLockInterruptibly()

**与ReadWriteLock比较**：

* ReadWriteLock 支持多个线程同时读，但是当多个线程同时读的时候，所有的写操作会被阻塞，可能导致写的**饥饿问题**（读操作一直都能抢占到CPU时间片，而写操作一直抢不了）
* StampedLock 的性能比 ReadWriteLock 好。主要是 StampedLock 支持乐观读的方式。当在乐观读期间，允许另一个线程获取写锁。那么怎样保证读和写的一致性呢？通过`validate（）方法`，如果校验不通过，说明期间有修改，可以再重试一次。




### 二、代码示例


* proof.chapter14.StampedLock2

写锁，具有独占性，一次只允许一个线程占用

* proof.chapter14.StampedLock1

悲观读锁，支持多个线程共享

* 乐观读锁demo

```
class Point {
  private double x, y;
  private final StampedLock sl = new StampedLock();


  double distanceFromOrigin() { 
    // 获取乐观读锁
    long stamp = sl.tryOptimisticRead();
    // 读取业务数据
    double currentX = x, currentY = y;
    // 验证乐观读锁，此期间是否有变化
    if (!sl.validate(stamp)) {
       // 如果有变化，升级为悲观读锁
       stamp = sl.readLock();
       try {
         currentX = x;
         currentY = y;
       } finally {
          sl.unlockRead(stamp);
       }
    }
    return Math.sqrt(currentX * currentX + currentY * currentY);
  }
}
```

* 乐观读，使用模板：

```
final StampedLock sl =  new StampedLock();
 
// 乐观读
long stamp = sl.tryOptimisticRead();

// 读入方法局部变量
......
// 校验 stamp
if (!sl.validate(stamp)){
  // 升级为悲观读锁
  stamp = sl.readLock();
  try {
    // 读入方法局部变量
    .....
  } finally {
    // 释放悲观读锁
    sl.unlockRead(stamp);
  }
}
// 使用方法局部变量执行业务操作
......
```

* StampedLock 写模板：

```
long stamp = sl.writeLock();
try {
  // 写共享变量
  ......
} finally {
  sl.unlockWrite(stamp);
}
```

### 三、参考

* http://blog.sina.com.cn/s/blog_6f5e71b30102xfsb.html
* 


