## 集合

### ArrayList 和 LinkedList 区别？

<details>
<summary>点击查看答案</summary>


- ArrayList 底层基于动态数组实现，LinkedList 底层基于链表实现; 
- 对于随机访问(get/set), ArrayList 通过 index 直接定位到数组对应位置的节点, 而 LinkedList 需要从头节点或尾节点开始遍历, 直到寻找到目标节点, 因此效率上 ArrayList 优于 LinkedList;
- 对于插入和删除(add/remove), ArrayList 需要移动目标节点后面的节点(System.arraycopy方法移动节点), 而 LinkedList 只需要修改目标前后节点的 next 或 prev 属性即可, 因此效率上 LinkedList 优于 ArrayList。

PS: 当 ArrayList 是顺序插入的时候, 由于不需要移动节点, 因此效率上并不会比 LinkedList 差; LinkedList 因为有 prev 和 next, 所以空间上比 ArrayList 占用大。
</details>

### HashMap 数据结构是什么样的？不同JDK版本之间实现有什么区别？

### ConcurrentHashMap 是如何保证线程安全的？

### ConcurrentHashMap 在JDK1.8 前后的锁有什么区别？

### HashMap 扩容的过程？



## JVM

#### Java 内存区域分配？

#### 类加载机制？

#### GC回收机制？

#### 常见的垃圾回收算法？

#### 线上出现 Full GC，是如何排查的？

<details>
    <summary>点击查看答案</summary>
    产生问题的原因对象到 GC Roots 的引用链没有被释放。
    1. 打印/导出 GC 日志, dump 出快照
    2. MAT 等工具分析堆内存情况。
</details>

<img src="https://static.xkcoding.com/blog/2020-06-23-82407.jpg" alt="JVM性能优化" style="zoom: 50%;" />

### G1 与 CMS 区别？

<details>
  <summary>点击查看答案</summary>
    CMS 适合老年代的回收, G1 适合新生代和老年代的回收。
    G1 使用 `Region` 的方式对堆内存进行了划分, 基于 `标记-整理` 算法, 减少内存碎片的产生。
</details>

## 多线程、锁

### volatile 关键字解决了什么问题，它的实现原理是什么？

### synchronized 原理，描述下锁膨胀的过程？

### synchronized 和 lock 有什么区别？

### 线程池的工作流程？参数都有哪些？具体什么含义？

### 介绍下 CAS，CAS 会存在哪些问题?

### 什么情况下会用 ThreadLocal？会存在哪些问题？



## Spring

### Spring 中用到了哪些设计模式？

### Spring MVC 流程

### Spring Boot 自动装配是如何实现的？

### Spring IOC AOP 

### Bean 的生命周期

### Bean 的作用域



## MySQL

### MySQL  为什么使用 B+ 树来作索引，对比 B 树它的优点和缺点是什么？

### MySQL  联合索引底层原理？

### 索引使用、创建原则?

### MySQL 事务隔离级别，各有哪些优缺点？

### InnoDB 和 MyISAM 的区别，为什么会使用 InnoDB 作为默认引擎？



## Redis

### Redis 支持的数据结构，以及它们应用的场景？

### 两种持久化方式 RDB 和 AOF 的优缺点？

### Redis 中如何防止缓存雪崩和缓存击穿

### Redis 的集群模式，setinel(哨兵) 和 cluster(主从)区别以及应用场景？

### Redis 如何实现分布式锁?

### 使用策略？





