package com.github.java.classesandobjects;

/**
 * @author pengfei.zhao
 * @date 2020/10/13 7:25
 */
public class MountainBike extends Bicycle{

    public int seatHeight;

    public MountainBike(int startHeight, int startCadence,
                        int startSpeed, int startGear) {
        super(startCadence, startSpeed, startGear);
        seatHeight = startHeight;
    }

    public void setSeatHeight(int seatHeight) {
        this.seatHeight = seatHeight;
    }
}
