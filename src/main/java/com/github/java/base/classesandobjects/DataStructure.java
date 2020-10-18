package com.github.java.classesandobjects;

import java.util.Iterator;

/**
 * Inner Class Example
 *
 * @author pengfei.zhao
 * @date 2020/10/13 16:18
 */
public class DataStructure {
    // Create an array
    private final static int SIZE = 15;
    private int[] arrayOfInts = new int[SIZE];

    public DataStructure() {
        for (int i = 0; i < SIZE; i++) {
            arrayOfInts[i] = i;
        }
    }

    public void printEven() {
        EvenIterator iterator = this.new EvenIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next() + " ");
        }
        System.out.println();
    }

    interface DataStructureIterator extends Iterator<Integer> {
    }

    public class EvenIterator implements DataStructureIterator {

        private int nextIndex = 0;

        @Override
        public boolean hasNext() {
            return (nextIndex <= arrayOfInts.length - 1);
        }

        @Override
        public Integer next() {
            int retValue = arrayOfInts[nextIndex];
            nextIndex += 2;
            return retValue;
        }
    }

    public static void main(String[] args) {
        DataStructure ds = new DataStructure();
        ds.printEven();
    }

}
