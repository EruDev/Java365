# Java 并发编程实战

## 1. 序言及全览

### 学习并发的原因
- 硬件驱动
- 人才稀缺

### 并发编程解决的核心问题
- 分工（如何高效地拆解任务并分配给线程） Fork/Join 线程
- 同步（指的是线程之间如何协作）CountDownLatch
- 互斥（保证统一时刻只允许一个线程访问共享资源）可重入锁

### 如何学习
- 跳出来, 看全景, 站在模型角度看问题（避免盲人摸象）。例如: `synchronized`、`wait()`、`notify()` 不过是操作系统领域里管程模型的一种实现而已.
- 钻进去, 看本质.
- 探究 Doug Lea 大师在 J.U.C 包创造的章法
- 知识体系全景图

![知识体系全景图](https://raw.githubusercontent.com/EruDev/md-picture/master/img/1603367859.png)

## 2. 抽象问题总结

### 并发程序的背后

1. CPU 增加了缓存, 以均衡与内存的速度差异;
2. 操作系统增加了进程、线程, 以分时复用CPU, 进而均衡 CPU 与 I/O 设备的速度差异;
3. 编译程序优化指令执行次序, 使得缓存能够得到合理地利用。

### 缓存导致的可见性问题
- 一个线程对共享变量的修改, 另外一个线程能够立即看到, 我们称为**可见性**。
- [代码示例](com/github/java/geektime/concurrency/features/visibility/Visibility.java)

![缓存导致的可见性问题1](https://raw.githubusercontent.com/EruDev/md-picture/master/img/1603370998.png)
![缓存导致的可见性问题1](https://raw.githubusercontent.com/EruDev/md-picture/master/img/1603371011.png)

### 线程切换带来的原子性问题
- 一个或多个操作在 CPU 执行的过程中不被中断的特性称为**原子性**
- 时间片概念
- 线程切换 ——> 提升 CPU 利用率。
- 线程切换
- 原子问题 [代码示例](com/github/java/geektime/concurrency/features/atomic/AtomicCounter.java)
- count+=1 操作分析
    - 指令1: 需要把变量count从内存加载到CPU的寄存器;
    - 指令2: 在寄存器中执行+1操作
    - 指令3: 将结果写入内存（缓存机制导致可能写入的是CPU缓存而不是内存缓存）
    
![非原子操作的执行路径示意图](https://raw.githubusercontent.com/EruDev/md-picture/master/img/1603371557.png)

### 编译优化带来的有序性问题

双重检查锁：
```java
public class Singleton {
  static Singleton instance;
  static Singleton getInstance(){
    if (instance == null) {
      synchronized(Singleton.class) {
        if (instance == null)
          instance = new Singleton();
        }
    }
    return instance;
  }
}
```
如果线程A与线程B同时进入第一个分支,那么这个程序就没有问题。

如果线程A先获取锁并出现指令重排序, 线程B未进入第一个分支, 那么就可能出现空指针问题, 这里说可能出现问题是因为当把内存地址赋值给共享变量后, CPU将数据写回缓存的时机是随机的。
 `new` 操作上, 我们以为的 new 操作是这样的：
1. 分配一块内存 M;
2. 在内存上初始化 Singleton 对象;
3. 然后 M 的地址指向 instance 变量。

实际上优化后可能是 1->3->2.

![双重检查创建单例的异常执行路径](https://raw.githubusercontent.com/EruDev/md-picture/master/img/1603371930.png)

线程在 **synchronized** 块中, 发生线程切换, 锁是不会释放的。

## 3. Java内存模型(JMM)

### Happens-Before六大规则

- 程序的顺序性规则
    - 程序前面对某个变量的修改一定是对后续操作可见的。
- volatile 变量规则
    - 对一个 volatile 变量的写操作, Happens-Before 于后续对这个 volatile 变量的读操作
- 传递性规则
    - A Happens-Before B, B Happens-Before C, 那么 A Happens-Before C.
- 管程(synchronized)中锁的规则：对一个锁的解锁 Happens-Before 于后续的这个锁加锁
- 线程 start() 规则: 主线程 A 启动子线程 B 后, 子线程 B 能够看到主线程在启动子线程 B 之前的操作。
```java
Thread B = new Thread(()->{
  // 主线程调用 B.start() 之前
  // 所有对共享变量的修改，此处皆可见
  // 此例中，var==77
});
// 此处主线程A对共享变量 var 修改
var = 77;
// 主线程启动子线程
B.start();
```
- 线程 join() 规则: 主线程 A 等待子线程 B完成（主线程A通过调用子线程B的join()方法实现）, 当子线程B完成后（主线程A中join()方法返回），主线程能够看到子线程操作
```java
Thread B = new Thread(()->{
  // 此处对共享变量 var 修改
  var = 66;
});
// 例如此处对共享变量修改，
// 则这个修改结果对线程 B 可见
// 主线程启动子线程
B.start();
B.join()
// 子线程所有对共享变量的修改
// 在主线程调用 B.join() 之后皆可见
// 此例中，var==66
```

### synchronized
- JVM 中的同步是基于进入和退出管程（Monitor）对象实现的。每个对象实例都会有一个 Monitor, Monitor 可以和对象一起创建、销毁。Monitor 是由 ObjectMonitor 实现的, 而 ObjectMonitor 实现是由
C++ 的 ObjectMonitor.cpp 文件实现的。
>当多个线程同时访问时, 多个线程会被先放在EntryList集合, 处于block状态的线程, 都会被加入到该列表。接下来当线程获取到对象的Monitor时, Monitor依靠底层操作系统 Mutex Lock 实现互斥, 线程申请Mutex成功
>持有, 其它线程无法获取到Mutex

```cpp
ObjectMonitor() {
   _header = NULL;
   _count = 0; // 记录个数
   _waiters = 0,
   _recursions = 0;
   _object = NULL;
   _owner = NULL;
   _WaitSet = NULL; // 处于 wait 状态的线程，会被加入到 _WaitSet
   _WaitSetLock = 0 ;
   _Responsible = NULL ;
   _succ = NULL ;
   _cxq = NULL ;
   FreeNext = NULL ;
   _EntryList = NULL ; // 处于等待锁 block 状态的线程，会被加入到该列表
   _SpinFreq = 0 ;
   _SpinClock = 0 ;
   OwnerIsThread = 0 ;
}
```
- wait(): 如果线程调用 wait() 方法, 就会释放当前持有的 Mutex, 并且该线程会进入 WaitSet 集合中, 等待下一次被唤醒。如果当前线程顺利执行完方法, 也将释放 Mutex.
![waitSet](https://raw.githubusercontent.com/EruDev/md-picture/master/img/1603512408.png)
- 锁升级优化
    - jdk1.6引入 Java 对象头（MarkWord、指向类的指针以及数组长度三部分组成）
    >对象实例分为对象头、实例数据和对齐填充
    - 64位 JVM 中 MarkWord 的存储结构
    ![64位JVM中MarkWord的存储结构](https://raw.githubusercontent.com/EruDev/md-picture/master/img/1603512459.jpg)
    - 偏向锁
    - 轻量级锁
    - 重量级锁
- [代码示例](com/github/java/geektime/concurrency/features/synchronizedcase/SynchronizedConnection.java)
```java
class X {
  // 修饰非静态方法 锁对象为当前类的实例对象 this
  synchronized void get() {
  }
  // 修饰静态方法 锁对象为当前类的Class对象 Class X
  synchronized static void set() {
  }
  // 修饰代码块
  Object obj = new Object();
  void put() {
    synchronized(obj) {
    }
  }
}  
```

### 死锁
>一组互相竞争资源的线程因互相等待, 导致"永久"阻塞的现象

四个条件：
- 互斥, 共享资源 X 和 Y 只能被一个线程占用;
- 占有且等待, 线程T1已经取得共享资源 X, 在等待共享资源Y的时候, 不释放共享资源X
- 不可抢占, 其他线程不能强行抢占线程T1占有的资源
- 循环等待, 线程T1等待线程T2占有的资源, 线程T2等待线程T1占有的资源等待。

如何破坏：

- 占有且等待
    - 拿银行转账问题来说, 我们只要增加一个账本管理员, 执行转账操作前, 需要向账本管理员同时拿到转出和转入的账本才能执行成功, 拿到其一都不行。
- 不可抢占
    - synchronized 在这一点是做不到, 因为 synchronized 如果申请不到, 线程直接进入阻塞状态, 也释放不了已经占有的资源。
    - JUC包下的 Lock 是可以解决的
- 循环等待
    - 还是以转账问题来说, 我们可以给每个账户增加一个id, 加锁按照 id 的大小从小到大加锁即可。
    
[代码示例](com/github/java/geektime/concurrency/features/synchronizedcase/SynchronizedResolveDeadLock.java)

### final
- [代码示例](com/github/java/geektime/concurrency/features/finalcase/FinalExample.java)
- 修饰变量时, 初衷是告诉编译器：这个变量生而不变, 非immutable, 即只能表示对象引用不能被赋值（例如list）
- 修饰方法时, 方法不能被重写
- 修饰类时不能被继承

```java
final int x;
    // 错误的构造函数
    public FinalFieldExample() { 
          x = 3;
          y = 4;
          // 此处就是讲 this 逸出，
          global.obj = this;
    }
```