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

## 4. 线程生命周期

- NEW(初始化状态)
- RUNNABLE(可运行/运行状态)
- BLOCKED(阻塞状态)
- WAITING(无限等待状态)
- TIMED_WAITING(有时限等待)
- TERMINATED(终止状态)
![线程状态](https://raw.githubusercontent.com/EruDev/md-picture/master/img/1603525472.png)

### 线程转换条件
1. RUNNABLE -> BLOCKED
    - 线程等待 synchronized 隐式锁（线程调用阻塞式API依然是RUNNABLE状态）
2. RUNNABLE -> WAITING
    - 获得synchronized隐式锁的线程, 调用Object.wait()
    - Thread.join()
    - LockSupport.part()
3. RUNNABLE -> TIMED_WAITING
    - Thread.sleep(long millis)
    - 获得 synchronized 隐式锁的线程调用 Object.wait(long timeout)
    - Thread.join(long millis)
    - LockSupport.parkNanos(Object blocker, long deadline)
    - LockSupport.parkUntil(long deadline)
4. NEW -> RUNNABLE
    - Thread.start()
5. RUNNABLE -> TERMINATED
    - run()执行完后自动转为 TERMINATED
    - stop() @Deprecated 直接结束线程, 如果持有 ReentrantLock 锁不会被释放
    - interrupt()
        - 异常通知
        - 主动监测
        
## 5. 多线程以及线程数确定

### 多线程目的
- 降低**延迟**(发出请求到收到响应的这个过程的时间;延迟越短, 意味着程序执行越快, 性能越好)
- 提高**吞吐量**（指的是在单位时间内处理请求的数量; 吞吐量越大, 意味着程序能处理的请求越多, 性能也越好）。

### 多线程应用场景
- 优化算法
- 将硬件性能发挥到极致

### 线程数
- CPU密集型：一般 CPU 核数 +1
- I/密集型
    - 单核：最佳线程数 =1 +（I/O 耗时 / CPU 耗时）
    - 多核：最佳线程数 =CPU 核数 * [ 1 +（I/O 耗时 / CPU 耗时）]
    
## 6. Lock和Condition

### 再造管程的理由：

- **能够响应中断**
>synchronized的问题是, 持有锁A后, 如果尝试获取锁B失败, 那么线程就进入阻塞状态, 一旦发生死锁, 就没有任务机会来唤醒线程。但如果阻塞的线程能够响应中断, 这样也能破坏不可抢占条件了。
- **支持超时**
>如果线程在一段时间之内没有获取到锁, 不是进入阻塞状态, 而是返回一个错误, 那这个线程也有机会释放曾经持有的锁。
- **非阻塞地获取锁**
>如果尝试获取锁失败，并不进入阻塞状态，而是直接返回，那这个线程也有机会释放曾经持有的锁

### 体现在代码层面上的
```java

// 支持中断的API
void lockInterruptibly() 
  throws InterruptedException;
// 支持超时的API
boolean tryLock(long time, TimeUnit unit) 
  throws InterruptedException;
// 支持非阻塞获取锁的API
boolean tryLock();
```

### 可重入锁
>指的是线程可以重复获取一把锁

例如下面代码中，当线程 T1 执行到 ① 处时，已经获取到了锁 rtl ，当在 ① 处调用 get() 方法时，会在 ② 再次对锁 rtl 执行加锁操作。
此时，如果锁 rtl 是可重入的，那么线程 T1 可以再次加锁成功；如果锁 rtl 是不可重入的，那么线程 T1 此时会被阻塞。
```java

class X {
  private final Lock rtl =
  new ReentrantLock();
  int value;
  public int get() {
    // 获取锁
    rtl.lock();         ②
    try {
      return value;
    } finally {
      // 保证锁能释放
      rtl.unlock();
    }
  }
  public void addOne() {
    // 获取锁
    rtl.lock();  
    try {
      value = 1 + get(); ①
    } finally {
      // 保证锁能释放
      rtl.unlock();
    }
  }
}
```

### 用锁的最佳实践
1. 永远只在更新对象的成员变量时加锁
2. 永远只在访问可变的成员变量时加锁
3. 永远不在调用其他对象的方法时加锁

## 6. 信号量

### 信号量模型
![信号量模型](https://raw.githubusercontent.com/EruDev/md-picture/master/img/1603590302.png)

- init(): 设置计数器初始值.
- down(): 计时器的值减 1;如果此时计数器的值小于 0，则当前线程将被阻塞，否则当前线程可以继续执行。
- up():计数器的值加 1；如果此时计数器的值小于或者等于 0，则唤醒等待队列中的一个线程，并将其从等待队列中移除。

```java
class Semaphore{
  // 计数器
  int count;
  // 等待队列
  Queue queue;
  // 初始化操作
  Semaphore(int c){
    this.count=c;
  }
  // 
  void down(){
    this.count--;
    if(this.count<0){
      //将当前线程插入等待队列
      //阻塞当前线程
    }
  }
  void up(){
    this.count++;
    if(this.count<=0) {
      //移除等待队列中的某个线程T
      //唤醒线程T
    }
  }
}
```

### 实现限流器（Semaphore 可以允许多个线程访问一个临界区）
- [代码示例](com/github/java/geektime/concurrency/features/semaphore/SemaphoreEx.java)

## 7. ReadWriteLock、StampedLock、CountDownLatch、CyclicBarrier

### ReadWriteLock
>读多写少场景
- 允许多个线程同时读共享变量
- 只允许一个线程写共享变量
- 如果一个写线程正在执行写操作, 此时禁止读线程读共享变量
- [代码示例](com/github/java/geektime/concurrency/features/readwritelock/CachedByReadWriteLock.java)

### StampedLock
- [代码示例](com/github/java/geektime/concurrency/features/readwritelock/StampedLockEx.java)

### CountDownLatch
- 主要用来解决一个线程等待多个线程的场景
- [代码示例](com/github/java/geektime/concurrency/features/countdownlatch/CountDownLatchEx.java)

### CyclicBarrier
- 一组线程之间互相等待, CyclicBarrier的计数器是可以循环利用的, 而且有自动重置的功能
- [代码示例](com/github/java/geektime/concurrency/features/cyclicbarrier/CyclicBarrierEx.java)

## 10. 并发容器

### 同步容器（jdk1.5之前）

#### 包装安全类
- List list = Collections.synchronizedList(new ArrayList)
- Set set = Collections.synchronizedSet(new HashSet)
- Map map = Collections.synchronizedMap(new HashMap)

Vetor、Stack 和 HashTable(基于synchronized实现)

#### 对同步容器做遍历操作时需要加锁保证互斥
- 如下组合操作非原子操作, 故通过synchronized 保证原子操作
```java
List list = Collections.synchronizedList(new ArrayList());
synchronized (list) {  
  Iterator i = list.iterator(); 
  while (i.hasNext())
    foo(i.next());
}    
```

### 并发容器（jdk1.5 之后）

### List
CopyOnWriteArrayList 写的时候共享变量新复制一份出来，这样的好处是读操作完全无锁

- 内部维护了一个数组 array，所有的操作都是基于 array 进行的，如下图所示，迭代器 Iterator 遍历的就是 array 数组
- 若遍历 array 数组的同时，新增元素，CopyOnWriteArrayList 会将 Array 复制一份，然后在新复制处理的数组上执行增加元素的操作，执行完后再将 array 指向这个新的数组。读写是可以并行的，遍历操作一直都是基于原 array 执行，而写操作则是基于新 array 进行。
- 总结：
    - 仅适用于写操作非常少的场景，而且能够容忍读写的短暂不一致。例如上面的例子中，写入的新元素并不能被立刻遍历到。
    - 迭代器是只读的，不支持增删改。因为迭代器遍历的仅仅是一个快照而对快照进行增删改查是没有意义的。
    
### Map
ConcurrentHashMap、ConcurrentSkipListMap（SkipList跳表） 区别在于Key是否有序

### Set 
CopyOnWriteArraySet、ConcurrentSkipListSet

### Queue
- 单端阻塞队列
    - 可有界
        - ArrayBlockingQueue（数组）
        - LinkedBlockingQueue（链表）默认大小为Integer最大值
    - 无界
        - SynchronousQueue（无队列，Producer的入队必须等待Consumer的出队）
        - LinkedTransferQueue（融合LinkedBlockingQueue和SynchronousQueue，性能优于LinkedBlockingQueue）
        - PriorityBlockingQueue（支持按照优先级出队）
        - DelayQueue（延时队列）

    ![BlockingQueue1](media/15607632412286/BlockingQueue1.png)

- 双端阻塞队列
    - LinkedBlockingDeque
    ![blockingDeque](media/15607632412286/blockingDeque.png)

- 单端非阻塞队列
    - ConcurrentLinkedQueue
- 双端非阻塞队列
    - ConcurrentLinkedDeque

## 11. 原子类
- 无锁方案实现原理（Compare And Swap）
    -[代码示例](com\github\java\geektime\concurrency\features\atomic\SimulatedCompareAndSwap.java)

### 原子化的基本数据类型
- AtomicBoolean
- AtomicInteger
- AtomicLong

### 原子化的对象引用类型
- AtomicReference
- AtomicStampedReference（版本号解决ABA问题）
- AtomicMarkableReference（版本号解决ABA问题）

## 原子化对象属性更新器（基于反射原子化更新对象属性，对象属性必须是volitale保证可见性）
- AtomicIntegerFieldUpdater
- AtomicLongFieldUpdater
- AtomicReferenceFieldUpdater

## 原子化累加器（空间换时间,只支持累加操作性能比原子化基本数据类型更好，不支持compareAndSet()）
- DoubleAccumulator
- DoubleAdder
- LongAccumulator
- LongAdder

## 12. 线程池

### 为什么要使用线程池
>创建对象，仅仅是在 JVM 的堆里分配一块内存而已. 而创建一个线程, 却需要调用操作系统内核 API, 然后操作系统分配一系列的资源, 这个成本就很高了

### 线程池是一种生产者-消费者模式
- [代码示例]()
- ThreadPoolExecutor

```markdown
ThreadPoolExecutor(
  int corePoolSize,
  int maximumPoolSize,
  long keepAliveTime,
  TimeUnit unit,
  BlockingQueue<Runnable> workQueue,
  ThreadFactory threadFactory,
  RejectedExecutionHandler handler) 
```
- 参数意义：
    - **corePoolSize**: 最小或核心线程数。有些项目很闲，但是也不能把人都撤了，至少要留 corePoolSize 个人坚守阵地。
    - **maximumPoolSize**: 最大线程数。当项目很忙时，就需要加人，但是也不能无限制地加，最多就加到 maximumPoolSize 个人。当项目闲下来时，就要撤人了，最多能撤到 corePoolSize 个人。
    - **keepAliveTime & unit**: 线程空闲回收时间，大小和单位，也就是说，如果一个线程空闲了keepAliveTime & unit这么久，而且线程池的线程数大于 corePoolSize ，就回收。
    - **workQueue**: 工作队列
    - **threadFactory**: 通过这个参数你可以自定义如何创建线程，例如你可以给线程指定一个有意义的名字。
    - **handler**:  拒绝策略。若线程池内所有线程都是忙碌，并且工作队列（有界队列）也满，线程池就会触发拒绝策略，以下为ThreadPoolExecutor提供的四种策略
        - CallerRunsPolicy: 提交任务的线程自己去执行该任务
        - AbortPolicy: 默认的拒绝策略，会 throws RejectedExecutionException
        - DiscardPolicy: 直接丢弃任务，没有任何异常抛出。
        - DiscardOldestPolicy: 丢弃最老的任务，其实就是把最早进入工作队列的任务丢弃，然后把新任务加入到工作队列。
- jdk 1.6之后加入allowCoreThreadTimeOut(boolean value) 核心线程也可释放。

### 注意事项
- 不建议使用Executors创建线程池（很多都是无界队列）
- 慎用默认拒绝策略RejectedExecutionException不强制处理容易忽略，建议自定义拒绝策略配合策略降级使用
- 异常处理不会通知所有需要按需捕获处理异常