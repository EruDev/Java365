# Collections Framework
- Reduces programming effort（减少编程工作）
- Increases program speed and quality（提升编程速度和质量）
- Allows interoperability among unrelated APIs（允许无关API之间的互操作性）
- Reduces effort to learn and to use new APIs （减少学习和使用新APIs的精力）
- Reduces effort to design new APIs（减少设计新API的工作量）
- Fosters software reuse（促进软件重用）

PS：因为个人觉得翻译过来，难免会有点主观色彩，所以把原文也放出来了。

# Interfaces
![Interfaces](https://docs.oracle.com/javase/tutorial/figures/collections/colls-coreInterfaces.gif)
- `Collection` — root interface
- `Set` — a collection that cannot contain duplicate elements.
- `List` — an **ordered** collection(sometimes called a sequence). Lists can contain duplicate elements.
- `Queue` — a collection used to hold multiple elements prior to processing.
- `Deque` — a collection used to hold multiple elements prior to processing.
- `Map` — an object that maps keys to values.
- `SortedSet` — a Set that maintains its mappings in ascending key order.
- `SortedMap` — a Map that maintains its mappings in ascending key order.

## Collection Interface
三种遍历方式:
- 聚合操作
- for-each
- 迭代器

List 在遍历过程中删除元素, 会导致 `ConcurrentModificationException`, 可以用迭代器的方法避免。[Avoiding the ConcurrentModificationException in Java](https://www.baeldung.com/java-concurrentmodificationexception)

## Set Interface
>Set 有三种实现 `HashSet`、`TreeSet`、`LinkedHashSet`, HashSet 基于 hash table 实现, 无法保证元素的顺序; TreeSet 基于红黑树实现

Set 集合操作比较方便, 例如交集、并集、差集

## List Interface
>有序集合, 允许重复, 提供两种实现：`ArrayList` 和 `LinkedList`
- Positional access (位置访问)
- Search (搜索)
- Iteration (迭代)
- Range-view (范围访问)

## Queue Interface

## Deque Interface
>发音 deck, 双端队列

## Map Interface
>Map 是将键映射到值的对象. 不能包含重复的键, 每个键至少有一个值.

## Object Ordering
实现 `Comparable` 接口, 例如 Date、八大基本类型都可以比较(compareTo)等.