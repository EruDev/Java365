# 语言基础

## 变量
- 实例变量 (非 static 字段), 对于每个类来说都是唯一的。
- 类变量 (静态变量), static 修饰, 在类中只会存在一份, 无论被实例化多少次。
- 局部变量, 声明在方法内, 基本数据类型不会分配默认值, 需要手动指定, 类中的其他地方是无法访问的。
- 参数, 方法的入参

### 命名规则
- 变量区分大小写;
- 后续字符可以是字母，数字，美元符号或下划线字符;
- 推荐驼峰命名法。

### 原始数据类型
- byte: 8bit, 默认值0
- short: 16bit, 默认值0
- int: 32bit, 默认值0
- long: 64bit, 默认值0L
- float: 32bit, 默认值0.0f
- double: 64bit, 默认值0.0d
- boolean: 默认值false
- char: 16bit, 默认值'\u0000'

### 数组
>数组是一个容器对象，包含固定数量的单个类型的值. 容器长度在被创建的时候就指定了
![数组](https://docs.oracle.com/javase/tutorial/figures/java/objects-tenElementArray.gif)

## 运算符

## 表达式, 语句, and 块

## 控制流语句