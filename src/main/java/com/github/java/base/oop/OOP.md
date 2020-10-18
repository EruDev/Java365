# Object-Oriented Programming

## What Is An Object?
An object is a software bundle of related state and behavior.(对象是具有相关状态和行为的软件包)
- 模块化
- 信息隐藏
- 代码复用
- 可插拔性和调试方便

## What Is a Class?
A class is a blueprint or prototype from which objects are created.(类是从对象中创建的蓝图或原型)

## What Is Inheritance（继承）?
不同的对象通常彼此具有一定数量的共同点, 例如山地自行车、公路自行车和双人自行车, 共同的特点(当前速度、当前档位)抽象到自行车类, 子类定义额外的属性(字段).
面向对象允许类从其他类继承行为和状态(单继承)
![A hierarchy of bicycle classes.](https://docs.oracle.com/javase/tutorial/figures/java/concepts-bikeHierarchy.gif)

## What Is an Interface?
接口是类与外界之间的契约。
- 接口不能被实例化
- 接口没有构造器
- 接口中所有的方法都是抽象的
- 接口中没有实例属性(field), 属性只能是 static 和 final
- 接口不能继承类, 可以继承接口

## What Is a Package?
包是一个命名空间，它组织一组相关的类和接口.

## Reference
[The Java™ Tutorials Lesson: Object-Oriented Programming Concepts](https://docs.oracle.com/javase/tutorial/java/concepts/index.html)