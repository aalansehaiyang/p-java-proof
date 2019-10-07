## 并发容器：List、Map、Set
---

### 一、同步容器

Java 在 1.5 版本之前所谓的线程安全的容器，主要是指`同步容器`。不过同步容器性能差，所有方法都用 synchronized 来保证互斥，串行度太高。因此 Java 在 1.5 及之后版本提供了性能更高的容器，我们一般称为`并发容器`。

同步容器实现思路：只要把非线程安全的容器封装在对象内部，然后控制好访问路径就可以了。

```
List list = Collections.synchronizedList(new ArrayList());
Set set = Collections.synchronizedSet(new HashSet());
Map map = Collections.synchronizedMap(new HashMap());
```

同步容器实现分为两种：

* 包装类：对普通的集合类包装成线程安全容器，内部基于 synchronized 关键字实现
* 原生类：JDK封装好的原生容器，Vector、Stack 和 Hashtable，内部同样是基于 synchronized 实现的

### 二、并发容器

并发容器虽然数量非常多，但主要分为四大类：List、Map、Set 和 Queue


### 三、List

#### 1、CopyOnWriteArrayList

**原理**：

内部维护了一个数组，成员变量 array 就指向这个内部数组，所有的读操作都是基于 array 进行的。

当有写操作进来时，CopyOnWriteArrayList 会将 array 复制一份，然后在新复制处理的数组上执行增加元素的操作，执行完之后再将 array 指向这个新的数组

**特性**：

* 读写可以并行。读操作基于原array进行，而写操作则是基于新 array 进行。
* 适用读多写少场景



### 四、Map

#### 1、ConcurrentHashMap

**特性**：

*  key 是无序的
*  key 和 value 都不能为空




#### 2、ConcurrentSkipListMap

**特性**：

*  key 是有序的
*  key 和 value 都不能为空
*  复杂度是 O(log n)，理论上和并发线程数没有关系，所以在并发度非常高的情况下，可以尝试一下 ConcurrentSkipListMap。

### 五、Set

#### 1、CopyOnWriteArraySet

* 基本等同CopyOnWriteArrayList

#### 2、ConcurrentSkipListSet