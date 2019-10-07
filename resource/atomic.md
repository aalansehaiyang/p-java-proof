## 原子类
---

### 一、基本信息

互斥锁方案为了保证互斥性，需要执行加锁、解锁操作，而加锁、解锁操作本身就消耗性能；同时拿不到锁的线程还会进入阻塞状态，进而触发线程切换，线程切换对性能的消耗也很大。 相比之下，无锁方案则完全没有加锁、解锁的性能消耗，同时还能保证互斥性。

**无锁方案的实现原理**：

* 硬件支持
* CPU 为了解决并发问题，提供了 CAS 指令（CAS，全称是 Compare And Swap，即“比较并交换”）。CAS 指令包含 3 个参数：共享变量的内存地址 A、用于比较的值 B 和共享变量的新值 C；并且只有当内存中地址 A 处的值等于 B 时，才能将内存中地址 A 处的值更新为新值 C。作为一条 CPU 指令，CAS 指令本身是能够保证原子性的。

* 代码示例：

```
public final long getAndAddLong(Object var1, long var2, long var4) {
    long var6;
    do {
        // 读取旧值
        var6 = this.getLongVolatile(var1, var2);
        // 引用的值与旧值比较，不相等时
    } while(!this.compareAndSwapLong(var1, var2, var6, var6 + var4));

    return var6;
}

```


**缺点**：

* 更新时可能因为不一致而快速失败，需要结合`自旋`，循环尝试

**其它**

* 原子类的方法都是针对一个共享变量的，如果你需要解决多个变量的原子性问题，建议还是使用互斥锁方案。



### 二、原子类概览

原子类分为五个类别：


* 基本数据类型

    * AtomicLong
    * AtomicInteger
    * AtomicBoolean

* 引用类型

    * AtomicReference
        * 可能会出现ABA问题
    * AtomicStampedReference
        * 增加一个版本号，解决ABA问题
        * `public boolean compareAndSet(V expectedReference, V newReference,int expectedStamp,int newStamp)`
        
    * AtomicMarkableReference
        * 同上，版本号简化成了一个 Boolean 值

* 数组

    * AtomicLongArray
        * 同基本数据类型，区别是：每个方法多了一个数组的索引参数
    * AtomicIntegerArray
    * AtomicReferenceArray
    
* 对象属性更新器

    * AtomicLongFieldUpdater
    * AtomicIntegerFieldUpdater
    * AtomicReferenceFieldUpdater

* 累加器

    * DoubleAccumulator
        * 执行累加操作，相比原子化的基本数据类型，速度更快
        * 但是不支持 compareAndSet() 方法
    * DoubleAdder
    * LongAccumulator
    * LongAdder
    