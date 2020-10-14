package com.github.java.interfaces;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author pengfei.zhao
 * @date 2020/10/13 20:38
 */
public class SimpleTimeClient implements TimeClient {

    private LocalDateTime dateAndTime;

    public SimpleTimeClient() {
        dateAndTime = LocalDateTime.now();
    }

    @Override
    public void setTime(int hour, int minute, int second) {
        // JDK 8
        LocalDate currentDate = LocalDate.from(dateAndTime);
        LocalTime time = LocalTime.of(hour, minute, second);
        dateAndTime = LocalDateTime.of(currentDate, time);
    }

    @Override
    public void setDate(int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month, day);
        LocalTime currentTime = LocalTime.from(dateAndTime);
        dateAndTime = LocalDateTime.of(date, currentTime);
    }

    @Override
    public void setDateAndTime(int day, int month, int year, int hour, int minute, int second) {
        LocalDate date = LocalDate.of(year, month, day);
        LocalTime time = LocalTime.of(hour, minute, second);
        dateAndTime = LocalDateTime.of(date, time);
    }

    @Override
    public LocalDateTime getLocalDateTime() {
        return dateAndTime;
    }

    public String toString() {
        return dateAndTime.toString();
    }

    public static void main(String[] args) {
        TimeClient timeClient = new SimpleTimeClient();
        System.out.println(timeClient.toString());
    }
}
