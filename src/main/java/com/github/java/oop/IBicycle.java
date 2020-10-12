package com.github.java.oop;

/**
 * @author pengfei.zhao
 * @date 2020/10/12 8:18
 */
public interface IBicycle {
    void changeCadence(int newValue);

    void changeGear(int newValue);

    void speedUp(int increment);

    void applyBrakes(int decrement);

    void printStates();
}
