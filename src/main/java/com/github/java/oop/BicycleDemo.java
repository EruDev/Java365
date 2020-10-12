package com.github.java.oop;

/**
 * @author pengfei.zhao
 * @date 2020/10/12 7:41
 */
public class BicycleDemo {
    public static void main(String[] args) {
        // 创建两个不同的自行车对象
        Bicycle bike1 = new Bicycle();
        Bicycle bike2 = new Bicycle();

        // 调用它们的方法
        bike1.changeCadence(50);
        bike1.speedUp(10);
        bike1.changeGear(2);
        bike1.printStates();

        bike2.changeCadence(50);
        bike2.speedUp(10);
        bike2.changeGear(2);
        bike2.changeCadence(40);
        bike2.speedUp(10);
        bike2.changeGear(3);
        bike2.printStates();
    }
}
