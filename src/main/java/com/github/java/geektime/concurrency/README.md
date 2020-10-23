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