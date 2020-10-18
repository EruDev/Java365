package com.github.java.oop;

/**
 * @author pengfei.zhao
 * @date 2020/10/12 7:39
 */
class Bicycle implements IBicycle{
    int cadence = 0;
    int speed = 0;
    int gear = 1;

    public void changeCadence(int newValue) {
        cadence = newValue;
    }

    public void changeGear(int newValue) {
        gear = newValue;
    }

    public void speedUp(int increment) {
        speed = speed + increment;
    }

    public void applyBrakes(int decrement) {
        speed = speed - decrement;
    }

    public void printStates() {
        System.out.println("cadence:" +
                cadence + " speed:" +
                speed + " gear:" + gear);
    }
}
