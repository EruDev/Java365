package com.github.java.interfaces;

/**
 * @author pengfei.zhao
 * @date 2020/10/13 20:53
 */
public class TestSimpleTimeClient {
    public static void main(String[] args) {
        TimeClient myTimeClient = new SimpleTimeClient();
        System.out.println("Current time: " + myTimeClient.toString());
        System.out.println("Time in California:" +
                myTimeClient.getZonedDateTime("Blah blah").toString());
    }
}
