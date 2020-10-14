# 接口和继承

## 接口
- 接口声明可以包含方法签名，默认方法，静态方法和常量定义。具有实现的唯一方法是默认方法和静态方法。
- 实现接口的类必须实现接口中声明的所有方法, JDK 8 接口可以有默认的实现.
- 接口名称可以在任何可以使用类型的地方使用。

## 继承

### 子类能做什么
子类继承父类 `public`, `protected` 成员, 无论子类在哪个包。如果子类和父类位于同一包, 它还能继承 `private` 成员
- 可以直接使用继承的 fields;
- 可以声明不再超类中有的 field, 方法;
- 继承的方法可以直接使用;
- 可以 `overring` 实例方法(instance method);
- 可以在子类中写和超类一样的静态方法, 该方法与超类的签名一样;
- 可以在构造函数调用超类的构造函数 `super`

1. 除了 Object 类, 一个类只有一个超类（单继承）。子类可以从超类继承覆盖方法和字段
2. 抽象类只能被子类化。它不能被实例化。抽象类可以包含抽象方法，即已声明但未实现的方法。然后，子类提供抽象方法的实现。
3. final 修饰的类或方法不可修改, 例如 String

## 参考
[The Java™ Tutorials Lesson: Interfaces and Inheritance](https://docs.oracle.com/javase/tutorial/java/IandI/index.html)